package org.zlx.rpc.rpcFrame.io.server;

import lombok.extern.slf4j.Slf4j;
import org.zlx.rpc.rpcFrame.entity.Message;
import org.zlx.rpc.rpcFrame.io.Dispatcher;
import org.zlx.rpc.rpcFrame.io.IOListener;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

/**
 * Created by @author linxin on 03/10/2017.  <br>
 *
 */
@Slf4j
public  class IOServerProcessor {
    private Selector selector;
    private Dispatcher dispatcher;
    private IOListener listener;

    public IOServerProcessor( Dispatcher dispatcher) throws IOException{
        this.dispatcher=dispatcher;
        this.selector =Selector.open();
    }

    public void sendMsg(SocketChannel socketChannel,Message message) {
        try {
            listener.sendMsg(socketChannel,message);
        } catch (IOException e) {
            log.error("IOServerProcessor sendRequest msg error",e);
        }
    }

    /**
     * 为socketChannel 穿件读写链接
     */
    public void addChannel(SocketChannel socketChannel) throws IOException{
        socketChannel.register(this.selector, SelectionKey.OP_READ);
        listener=new IOListener(selector,this.dispatcher);
        listener.start();
    }
    public void wakeup() {
        this.selector.wakeup();
    }



}
