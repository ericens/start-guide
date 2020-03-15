package org.zlx;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

@Slf4j
public class SocketChannelTest {

    @Test
    public void test() throws IOException {
        String host="127.0.0.1";
        int port=8088;

        SocketChannel socketChannel = SocketChannel.open();
        InetSocketAddress sad = new InetSocketAddress(host, port);
        socketChannel.connect(sad);
        socketChannel.configureBlocking(false);
        Selector selector=Selector.open();
        SelectionKey selectKey=socketChannel.register(selector, SelectionKey.OP_READ);
        log.info("key:{}", JSON.toJSONString(selectKey.interestOps()));

        selectKey=socketChannel.register(selector, SelectionKey.OP_WRITE);
        selectKey.interestOps(selectKey.interestOps() & (~SelectionKey.OP_READ));
        log.info("key:{}", JSON.toJSONString(selectKey.interestOps()));


        Set<SelectionKey> keys = selector.selectedKeys();
        Iterator<SelectionKey> iterator = keys.iterator();
        while (iterator.hasNext()) {
            log.info("key:{}", JSON.toJSONString(iterator.next()));
        }


    }
}
