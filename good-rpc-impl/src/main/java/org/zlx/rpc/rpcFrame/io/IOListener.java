package org.zlx.rpc.rpcFrame.io;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.zlx.rpc.rpcFrame.entity.Message;
import org.zlx.rpc.rpcFrame.utils.Coder;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

@Slf4j
@Data
public class IOListener {
    Selector selector;
    MsgQueue msgQueue=new MsgQueue();
    private Dispatcher dispatcher;

    public IOListener(){

    }


    public IOListener(Selector selector,Dispatcher dispatcher){
        this.dispatcher=dispatcher;
        this.selector=selector;
    }

    public void sendMsg(SocketChannel socketChannel,Message message) throws IOException {
        msgQueue.putMsg(socketChannel,message);
        socketChannel.register(this.selector,SelectionKey.OP_WRITE | SelectionKey.OP_READ);
        //TODO  done 暂时直接写socketChannel, 不等isWritable.
        //writeMsg(socketChannel);
    }

    public void start() {
        Runnable runnable=new Runnable() {
            @Override
            public void run() {
                doStart();
            }
        };
        new Thread(runnable,"ioThread").start();
    }


    public void doStart() {

        while (true) {
            try {
                /**
                 * TODO done 需要引入超时机制，如果没有,则即使设置了SelectionKey.OP_WRITE，也不能接受。
                 * 因此此时select 没有监听 OP_WRITE 事件，需要下一轮才监听。
                 */
                if (selector.select(1000) <= 0) {
                    continue;
                }
                Set<SelectionKey> keys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = keys.iterator();
                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    //处理读事件
                    if (key.isReadable()) {
                        ByteBuffer buffer = ByteBuffer.allocate(4089);
                        SocketChannel socketChannel = (SocketChannel) key.channel();
                        int count = socketChannel.read(buffer);
                        if (count < 0) {
                            socketChannel.close();
                            key.cancel();
                            log.info("{}\t Read ended", socketChannel);
                        } else if (count <= 4) {
                            log.info("{}\t Message size is :{} ", socketChannel,count);
                        }else {
                            //处理消息
                            String str=Coder.decodeMesg(buffer);
                            log.info("{}\t Read message {}", socketChannel,str );
                            dispatcher.doReceive(str,socketChannel);
                        }
                    }

                    //处理写入消息
                    else if (key.isWritable()) {
                        SocketChannel socketChannel = (SocketChannel) key.channel();
                        log.info("socketChannel :{} is isWritable",socketChannel);
                        /**
                         * TODO done 暂时直接写，不是通过isWritable 来判断。但是如果注册 写事件，则socketBuffer不为空，则一直可写。
                         * 参考：https://marlonyao.iteye.com/blog/1005690
                         * 把队列的消息写完后。重置写事件，不再监听写。 等待队列有消息后，才进行注册写事件。
                         * 1. 读写分线程，写线程没有消息写是，一直阻塞。只有queue有消息，注册写事件，写事件，queue消息为空，则取消
                         * 2. 读写公用一个线程，则 写的时候可能阻塞。
                         */
                        while(msgQueue.hasMessage(socketChannel)){
                            writeMsg(socketChannel);
                        }
                        socketChannel.register(this.selector,SelectionKey.OP_READ);
                    }
                    iterator.remove();

                }// while iterator
            } catch(Exception e) {
                log.info("error when io",e);
            }
        }//while true
    }


    private void writeMsg(SocketChannel socketChannel) throws IOException {
        Message message=msgQueue.getMSg(socketChannel);
        if(message!=null){
            ByteBuffer buffer=Coder.codeMsg(message);
            socketChannel.write(buffer);
        }
    }




}
