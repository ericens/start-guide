package io.openmessaging;


import static io.openmessaging.xjf.Queue.FILL_BYTE;
import static io.openmessaging.xjf.Queue.SINGLE_MESSAGE_SIZE;

public class MsgUtils {

    /**
     * 把磁盘数据，过滤成有效数据。主要是删除填充的数据。
     * @param bytes
     * @return
     */
    public static byte[] filter(byte[] bytes) {
        int size=0;
        for (int i = 0; i < bytes.length; i++) {
            if(bytes[i]>=0){
                size++;
            }
        }
        byte[] newByte =new byte[size];
        System.arraycopy(bytes,0,newByte,0,size);
        return newByte;
    }


    /**
     * 按照最大消息长度填充消息SINGLE_MESSAGE_SIZEÓ
     *
     *
      */
    public static byte[] fillToFixLenMsg(byte[] message){
        if (message.length < SINGLE_MESSAGE_SIZE) {
            byte[] newMessage = new byte[SINGLE_MESSAGE_SIZE];
            for (int i = 0; i < SINGLE_MESSAGE_SIZE; i++) {
                if (i < message.length) {
                    newMessage[i] = message[i];
                } else {
                    newMessage[i] = FILL_BYTE;
                }
            }
            message = newMessage;
        }
        return message;
    }



}
