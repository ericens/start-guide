package io.openmessaging.my;

import io.openmessaging.QueueStore;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.util.Collection;
import java.util.Collections;

/**
 * @author ericens
 */
public class MyxQueueStore extends QueueStore {
    int QUEUE_COUNT=100*10000;
    Queue[] queues=new Queue[QUEUE_COUNT];

    FileChannel fileChannel;

    public MyxQueueStore() throws FileNotFoundException {
        fileChannel=new RandomAccessFile("/Users/ericens/tmp/alitest/001.data","rw").getChannel();
    }

    @Override
    public void put(String queueName, byte[] message) {
        int index= Math.abs(queueName.hashCode())%QUEUE_COUNT;
        try {
            if(queues[index]==null){
                queues[index]=new Queue(fileChannel);
            }
            queues[index].put(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    @Override
    public Collection<byte[]> get(String queueName, long offset, long num) {
        int index= Math.abs(queueName.hashCode())%QUEUE_COUNT;
        try {
            if(queues[index]==null){
                System.out.println("the queue is empty :"+ queueName);
                return Collections.EMPTY_LIST;
            }
           return queues[index].get(offset,(int)num);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Collections.EMPTY_LIST;
    }
}
