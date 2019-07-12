package org.zlx.rpc.rpcFrame.io.client;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.zlx.rpc.rpcFrame.entity.Message;
import org.zlx.rpc.rpcFrame.entity.Role;
import org.zlx.rpc.rpcFrame.io.Dispatcher;
import org.zlx.rpc.rpcFrame.io.IOListener;
import org.zlx.rpc.rpcFrame.io.ISender;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

@Slf4j
@Data

public class IOClient implements ISender {
    boolean started=false;
    String host="127.0.0.1";
    int port=8088;
    SocketAddress sad;
    SocketChannel socketChannel;
    Selector selector;
    IOListener listener;
    private Dispatcher dispatcher;

    public IOClient(){
        this.listener=new IOListener();

        //设定为服务端角色
        this.dispatcher =new Dispatcher(this);
        this.dispatcher.setRole(Role.client);


    }
    @Override
    public void sendMsg(SocketChannel socketChannel,Message message) throws IOException {
        checkConnection(this.socketChannel);
        listener.sendMsg(this.socketChannel,message);

    }

    /**
     *  TODO done 加入重连逻辑,
     *  保证原来的对象，资源不会泄露。要么重用要么销毁。
     *  1. dispachter的线程池
     *  2. ioListener的 selector,是新的open的对象，不然监听不到事件。
     *
     * @param socketChannel
     * @throws IOException
     */
    private void checkConnection(SocketChannel socketChannel) throws IOException {
        if(!socketChannel.isConnected()|| !socketChannel.isOpen()){
            this.connect();
        }

    }

    /**
     *
     */
    public synchronized  void  connect() throws IOException {

        log.info("client begin to connect to host:{}, port:{} " ,host,port);
        //连接 服务端
        this.socketChannel = SocketChannel.open();
        this.sad = new InetSocketAddress(host, port);
        this.socketChannel.connect(sad);
        this.socketChannel.configureBlocking(false);
        this.selector=Selector.open();
        this.socketChannel.register(this.selector, SelectionKey.OP_READ);
        log.info("client connect to host:{}, port:{} success" ,host,port);

        this.listener.setDispatcher(this.dispatcher);
        this.listener.setSelector(this.selector);
        this.listener.start();

    }



}
