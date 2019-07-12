package org.zlx.rpc.rpcFrame.io;

import org.zlx.rpc.rpcFrame.entity.Message;

import java.nio.channels.ClosedChannelException;
import java.nio.channels.SocketChannel;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingDeque;

public class MsgQueue {

    public static Map<SocketChannel,BlockingQueue<Message>> channelQueueMap=new ConcurrentHashMap();

    public void putMsg(SocketChannel socketChannel, Message message) throws ClosedChannelException {
        BlockingQueue queue=channelQueueMap.get(socketChannel);
        if(queue==null){
            queue=new LinkedBlockingDeque();
            channelQueueMap.put(socketChannel,queue);
        }
        //message 放到  发送队列中暂存
        queue.offer(message);
    }

    public Boolean hasMessage(SocketChannel socketChannel){
        BlockingQueue<Message> queue=channelQueueMap.get(socketChannel);
        if(queue==null){
            return false;
        }
        return queue.size()==0?false: true;
    }


    public Message getMSg(SocketChannel socketChannel) {
        BlockingQueue<Message> queue=channelQueueMap.get(socketChannel);
        Message message= null;
        try {
            message = queue.poll();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return message;
    }


}
