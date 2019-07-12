package org.zlx.rpc.rpcFrame.entity;

import com.alibaba.fastjson.JSON;
import lombok.Data;

@Data
public class Response extends Message{
    Object resp;
    Long rspTime=System.currentTimeMillis();


    public Response(){
        this.msgType=MsgType.response;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
