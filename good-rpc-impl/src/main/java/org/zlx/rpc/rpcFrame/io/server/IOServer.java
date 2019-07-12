package org.zlx.rpc.rpcFrame.io.server;

import lombok.extern.slf4j.Slf4j;
import org.zlx.rpc.rpcFrame.entity.Message;
import org.zlx.rpc.rpcFrame.entity.Role;
import org.zlx.rpc.rpcFrame.io.Dispatcher;
import org.zlx.rpc.rpcFrame.io.ISender;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;

/**
 * Created by @author linxin on 03/10/2017.  <br>
 *
 */

@Slf4j
public class IOServer implements ISender {

    static Boolean started=false;
    static Integer port=8088;
    private Dispatcher dispatcher;

    Map<SocketChannel,IOServerProcessor> socketChannelIOServerProcessorMap=new ConcurrentHashMap<>();

    public  void start() throws IOException, NoSuchMethodException, InterruptedException, ExecutionException, InstantiationException, IllegalAccessException, InvocationTargetException, ClassNotFoundException {
        if(started==false){
            //设定为服务端角色
            dispatcher =new Dispatcher(this);
            dispatcher.setRole(Role.server);

            Selector selector = Selector.open();
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.bind(new InetSocketAddress(port));
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);


            //创建多个线程池处理器，每个处理器是多一个线程池
            int coreNum = Runtime.getRuntime().availableProcessors();
            IOServerProcessor[] processors = new IOServerProcessor[coreNum];
            for (int i = 0; i < processors.length; i++) {
                processors[i] = new IOServerProcessor(dispatcher);
            }
            log.info("the server started... listen on:{}",port);
            int index = 0;
            while (selector.select() > 0) {
                Set<SelectionKey> keys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = keys.iterator();
                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    iterator.remove();
                    //处理链接事件
                    if (key.isAcceptable()) {
                        ServerSocketChannel acceptServerSocketChannel = (ServerSocketChannel) key.channel();
                        SocketChannel socketChannel = acceptServerSocketChannel.accept();
                        socketChannel.configureBlocking(false);
                        log.info("Accept request from {}", socketChannel.getRemoteAddress());

                        //每个链接放入，响应的处理器中
                        IOServerProcessor processor = processors[(int) ((index++) % coreNum)];
                        processor.addChannel(socketChannel);
                        socketChannelIOServerProcessorMap.put(socketChannel,processor);
                        processor.wakeup();
                    }
                }
            }
            started=true;
        }
    }


    @Override
    public void sendMsg(SocketChannel socketChannel, Message message)  {
        IOServerProcessor processor=socketChannelIOServerProcessorMap.get(socketChannel);
        processor.sendMsg(socketChannel,message);

    }
}
