package util;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.zlx.rpc.rpcFrame.entity.MsgType;
import org.zlx.rpc.rpcFrame.entity.Request;
import org.zlx.rpc.rpcFrame.utils.Coder;

import java.nio.ByteBuffer;

@Slf4j
public class CoderTest {

    @Test
    public void codeTest(){
        Request message=new Request();
        message.setMsgType(MsgType.request);
        message.setReqId(234L);
        message.setParam("param");
        message.setMethod("method");


        ByteBuffer byteBuffer =Coder.codeMsg(message);


        String str=Coder.decodeMesg(byteBuffer);

        log.info("str:{}",str);
        log.info("msg:{}",message.toString());
        assert  str.equals(message.toString());

    }
}
