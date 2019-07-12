package org.zlx.rpc.rpcFrame.io;

import org.zlx.rpc.rpcFrame.entity.Message;

import java.io.IOException;
import java.nio.channels.SocketChannel;

public interface ISender {
    void sendMsg(SocketChannel socketChannel, Message message) throws IOException;
}
