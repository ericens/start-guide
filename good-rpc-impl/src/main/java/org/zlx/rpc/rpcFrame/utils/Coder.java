package org.zlx.rpc.rpcFrame.utils;

import org.zlx.rpc.rpcFrame.entity.Message;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;

public class Coder {


    static public ByteBuffer codeMsg(Message message){
        byte [] bytes=message.toString().getBytes(Charset.forName("UTF-8"));
        ByteBuffer buffer = ByteBuffer.allocate(bytes.length+4);
        //先写长度
        buffer.put( toByteArray(bytes.length));
        //再写消息体
        buffer.put(bytes);
        buffer.flip();
        return buffer;
    }

    static byte[] toByteArray(int value) {
        return new byte[] {
                (byte)(value >> 24),
                (byte)(value >> 16),
                (byte)(value >> 8),
                (byte)value };
    }




    public static String decodeMesg(ByteBuffer buffer){
        byte [] bytes=buffer.array();
        byte []header=new byte[4];
        System.arraycopy(bytes,0,header,0,4);
        int len=fromByteArray(header);

        byte []body=new byte[len];
        System.arraycopy(bytes,4,body,0,len);
        String str=new String(body);
        return str;
    }


    static int  fromByteArray(byte[] bytes) {
        return ByteBuffer.wrap(bytes).getInt();
    }


}
