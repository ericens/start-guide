package geym.zbase.my;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class ReadByHeapByteBufferTest {
    public static void main(String[] args) throws IOException, InterruptedException {
        File data = new File("/tmp/data.txt");
        FileChannel fileChannel = new RandomAccessFile(data, "rw").getChannel();
        ByteBuffer buffer = ByteBuffer.allocate(4 * 1024 * 1024);
        for (int i = 0; i < 1000; i++) {
            Thread.sleep(1000);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        //从文件读可以，使用堆外内存,  调用了堆外内存分配，
                        fileChannel.read(buffer);
                        //仅仅putInt，无法使用堆外内存
                        buffer.putInt(22);

                        buffer.clear();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }
}

