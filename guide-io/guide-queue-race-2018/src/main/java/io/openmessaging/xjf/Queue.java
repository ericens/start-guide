package io.openmessaging.xjf;

import io.openmessaging.MsgUtils;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author 徐靖峰
 * Date 2018-07-05
 *
 * 1. 在测试时候，固定offset=消息。大小进行填充。
 * 2. 所有的queue放在一个 文件中。
 * 3. 完全没有考虑，flush. 所谓的flush 其实叫commit. 没有可靠性。
 * 4. 假设消息定长，58字节。进行字节的填充。
 * 5. 在消息返回的时候，需要把填充字节去掉，以还原原始消息。
 * 6. 写阶段没有读，读阶段没有写。读的时候可能多thread 读。
 *
 * good
 * 1. buffer.remiaing()
 * 2. 找到startOffset,  endOffset=min(start+num, endOffset)
 * 3.
 *
 * todo
 * 1. 如何进行index
 *  1. 一次写block, 也就是多条消息，所有queue的所有block都写一个文件。（假设没有大小限制）
 *  内存中记录=====当然可以index到磁盘。
 *  2. 记录此block在文件的物理位置
 *  3. 记录此block的第一条消息offset
 *  4. 记录每个queue的，所有block的 2、3条信息。
 *
 * 2. get的时候如何 删除填充
 * 3.
 */
public class Queue {

    public final static int SINGLE_MESSAGE_SIZE = 58;
    public final static int BLOCK_SIZE = 40;
    public static final byte FILL_BYTE = (byte) -1;


    private FileChannel channel;
    private AtomicLong wrotePosition;

    private volatile boolean firstGet = true;
    private volatile boolean firstPut = true;

    public Queue(FileChannel channel, AtomicLong wrotePosition) {
        this.channel = channel;
        this.wrotePosition = wrotePosition;
    }

    // 缓冲区大小
    public final static int bufferSize = SINGLE_MESSAGE_SIZE * BLOCK_SIZE;

    // 读写缓冲区
    private ByteBuffer buffer = ByteBuffer.allocateDirect(bufferSize);
    //
    private int lastReadOffset = -1;

    private static final int size = 2000 / BLOCK_SIZE + 1;
//    private static final int size = 2000;


    //一连串的block，                 1：物理文件偏移量:queueOffset，2，3
    // 记录该块在物理文件中的起始偏移量， 300，800，900
    private long pyOffsets[] = new long[size];
    // 记录该块中第一个消息的起始消息编号, 30,80,90
    private int firstOffsetInBlock[] = new int[size];
    //get消息时，找到queue,根据offset。到第几个的block, 找到block的 offset。
    //put时，   block为整体，      更新


    private long offset;
    private int firsOffset;

    /**
     * 队列的总块数
     */
    private int blockSize = 0;
    /**
     * 队列的总消息数
     */
    private int queueLength = 0;

    private static ExecutorService flushThread = Executors.newSingleThreadExecutor();

    private static ByteBuffer flushBuffer = ByteBuffer.allocateDirect(64 * 1024);

    private Future<Long> flushFuture;

    /**
     * put 由评测程序保证了 queue 级别的同步
     *
     * @param message
     */
    public void put(byte[] message) {
        if (firstPut) {
            this.firsOffset = 0;
            firstPut = false;
        }
        // 缓冲区满，先落盘:假定最长消息长度为SINGLE_MESSAGE_SIZE。
        if (SINGLE_MESSAGE_SIZE > buffer.remaining()) {
            // 落盘
            flush();
        }
        buffer.put(MsgUtils.fillToFixLenMsg(message));
        this.queueLength++;
    }


    private void flush() {
        if (flushFuture != null) {
            try {
                //更新此block的 物理偏移量
                pyOffsets[blockSize - 1] = flushFuture.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
            flushFuture = null;
        }

        buffer.flip();
        int remaining = buffer.remaining();
        final byte[] message = new byte[remaining];
        buffer.get(message);
        flushFuture = flushThread.submit(new Callable<Long>() {
            @Override
            public Long call() throws Exception {
                long writePosition = wrotePosition.getAndAdd(message.length);
                try {
                    if (flushBuffer.remaining() < message.length) {
                        flushBuffer.flip();
                        channel.write(flushBuffer);
                        flushBuffer.clear();
                    }
                    flushBuffer.put(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return writePosition;
            }
        });
        buffer.clear();
        //更新此block的第一条消息的offset
        firstOffsetInBlock[blockSize] = this.firsOffset;
        blockSize++;
        if (blockSize > pyOffsets.length * 0.7) {
            pyOffsets = copyOf(pyOffsets, pyOffsets.length * 2);
            firstOffsetInBlock = copyOf(firstOffsetInBlock, firstOffsetInBlock.length * 2);
        }

        this.firsOffset += BLOCK_SIZE;
    }

    private void flushForGet() {
        if (flushFuture != null) {
            try {
                pyOffsets[blockSize - 1] = flushFuture.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
            flushFuture = null;
        }

        buffer.flip();
        flushFuture = flushThread.submit(new Callable<Long>() {
            @Override
            public Long call() throws Exception {
                long writePosition = wrotePosition.getAndAdd(buffer.remaining());
                try {
                    if (flushBuffer.position() > 0) {
                        flushBuffer.flip();
                        channel.write(flushBuffer);
                        flushBuffer.clear();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                channel.write(buffer);
                buffer.clear();
                return writePosition;
            }
        });

        try {
            pyOffsets[blockSize] = flushFuture.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        firstOffsetInBlock[blockSize] = this.firsOffset;
        blockSize++;
    }

    /**
     * 读可能存在并发读，注意 race condition
     *
     * @param offset
     * @param num
     * @return
     */
    public synchronized Collection<byte[]> get(long offset, long num) {
        if (firstGet) {
            flushForGet();
            firstGet = false;
        }
        if (offset > queueLength - 1) {
            return DefaultQueueStoreImpl.EMPTY;
        }
        int startIndex = (int) offset;
        int endIndex = Math.min(startIndex + (int) num - 1, queueLength - 1);

        List<byte[]> result = new ArrayList<>();
        //zlx 上一个 IO读过，放在buffer里面，这次直接从buffer里面获取。
        if (lastReadOffset == startIndex) {
            while (startIndex <= endIndex && buffer.hasRemaining()) {
                startIndex++;
                byte[] cacheMessage = new byte[SINGLE_MESSAGE_SIZE];
                buffer.get(cacheMessage);
                result.add(MsgUtils.filter(cacheMessage));
                lastReadOffset++;
            }
        }
        // 从 cache 中获取到了所有的消息
        if (startIndex > endIndex) {
            return result;
        }
        //zlx 由于是 固定消息长度，所以直接定位具体的block。
        int startBlock = startIndex / BLOCK_SIZE;
        int endBlock = endIndex / BLOCK_SIZE;

        for (int j = startBlock; j <= endBlock; j++) {
            int blockStartIndex = j * BLOCK_SIZE;
            buffer.clear();
            try {
                channel.read(buffer, this.pyOffsets[j]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            buffer.flip();
            for (int i = 0; i < BLOCK_SIZE; i++) {
                if (startIndex <= blockStartIndex + i && blockStartIndex + i <= endIndex) {
                    byte[] bytes = new byte[SINGLE_MESSAGE_SIZE];
                    buffer.get(bytes);
                    // TODO
                    result.add(MsgUtils.filter(bytes));
                    this.lastReadOffset = blockStartIndex + i + 1;
                } else if (blockStartIndex + i > endIndex) {
                    break;
                } else {
                    // skip
                    byte[] bytes = new byte[SINGLE_MESSAGE_SIZE];
                    buffer.get(bytes);
                }

            }
        }
        return result;
    }

    public static int[] copyOf(int[] original, int newLength) {
        int[] copy = new int[newLength];
        System.arraycopy(original, 0, copy, 0,
                Math.min(original.length, newLength));
        return copy;
    }

    public static long[] copyOf(long[] original, int newLength) {
        long[] copy = new long[newLength];
        System.arraycopy(original, 0, copy, 0,
                Math.min(original.length, newLength));
        return copy;
    }

}
