package io.openmessaging.my;

import com.alibaba.fastjson.JSON;
import io.openmessaging.MsgUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Slf4j
public class Queue {
    // 一个block的大小,固定大小4k
    int blockSize=4200;
    ByteBuffer byteBuffer= ByteBuffer.allocateDirect(blockSize);

    FileChannel fileChannel;
    QueueIndex queueIndex=new QueueIndex();
    boolean firstRead=true;

    public Queue( FileChannel fileChannel){
        this.fileChannel=fileChannel;
    }

    boolean newBlock=true;
    int nextWriteOffset =0;
    int nextBlockOffset;
    long nextBlockPyOffset;

    QueueIndex.BlockInfo lastBlockInfo;
    long lastReadOffset= Long.MIN_VALUE;
    public  void put(byte[] message) throws IOException {

        if(message.length+2 > byteBuffer.remaining()){
            //flush，没有空间放入消息，所以写入文件
            synchronized (fileChannel){
                commit();
            }

        }
        if(newBlock){
            nextBlockOffset= nextWriteOffset;
            newBlock=false;
        }
        //放入内存缓存
        byteBuffer.putShort((short)message.length);
        byteBuffer.put(message);
        nextWriteOffset++;
    }

    private void commit() throws IOException {
        //构造index缓存
        nextBlockPyOffset=fileChannel.position();
        queueIndex.blockInfos.add(QueueIndex.BlockInfo.builder()
                .offset(nextBlockOffset)
                .pyOffset(nextBlockPyOffset)
                .msgCount(nextWriteOffset -nextBlockOffset)
                .build()
        );


        //填充-1
        while(byteBuffer.position()<byteBuffer.limit()){
            byteBuffer.put((byte)-1);
        }
        byteBuffer.flip();

        fileChannel.write(byteBuffer);
        byteBuffer.rewind();
        newBlock=true;
    }

    public  synchronized Collection<byte[]> get(long readOffset, int count) throws IOException {
        log.debug( "queue index:{}", JSON.toJSONString(queueIndex.blockInfos));

        if(readOffset>this.nextWriteOffset){
            return Collections.EMPTY_LIST;
        }

        //写缓冲，写入文件
        if(firstRead){
            firstRead=false;
            commit();
            fileChannel.force(true);
        }


        long endOffset= Math.min(readOffset+count-1,this.nextWriteOffset);
        //顺序读，上次和这次链接上了
        if(readOffset == this.lastReadOffset+1){
            //看看buffer里有没有。
            synchronized (byteBuffer){
                return byteBufferToMsg(lastBlockInfo,readOffset,count,byteBuffer);
            }
        }


        byteBuffer.clear();
        //读文件
        for ( int index=0; index<queueIndex.getBlockInfos().size();index++){
            //find the  block
            QueueIndex.BlockInfo blockInfo=queueIndex.getBlockInfos().get(index);
            if( blockInfo.getOffset() <= readOffset && readOffset< blockInfo.getOffset()+ blockInfo.getMsgCount()){
                //read the block.
                lastBlockInfo=blockInfo;
                fileChannel.read(byteBuffer,blockInfo.getPyOffset());

                byteBuffer.flip();
                return byteBufferToMsg(lastBlockInfo,readOffset,count,byteBuffer);
            }
        }

        return Collections.EMPTY_LIST;

    }

    public Collection<byte[]> byteBufferToMsg(QueueIndex.BlockInfo blockInfo, long offset, int readNum, ByteBuffer blockByteBuffer){
        List result= new ArrayList();
        int count=0;
        int offsetIndex=blockInfo.getOffset();
        while(blockByteBuffer.hasRemaining()){
            //读到block 未了
            if(count>=blockInfo.getMsgCount()){
                return  result;
            }

            Short msgLen=blockByteBuffer.getShort();
            if(offsetIndex < offset){
                //跳过不感兴趣的消息
                blockByteBuffer.position( blockByteBuffer.position()+ msgLen);
            }else{
                byte [] msg=new byte[msgLen];
                blockByteBuffer.get(msg,0,msgLen);
                msg= MsgUtils.filter(msg);
                result.add(msg);
                if(readNum==result.size()){
                    return result;
                }
                this.lastReadOffset=offsetIndex;
            }
            count++;
            offsetIndex++;

        }
        return result;

    }
}
