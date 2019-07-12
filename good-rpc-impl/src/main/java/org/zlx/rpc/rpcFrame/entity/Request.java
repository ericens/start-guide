package org.zlx.rpc.rpcFrame.entity;

import com.alibaba.fastjson.JSON;
import lombok.Data;

import java.util.concurrent.atomic.AtomicLong;

@Data
public class Request extends Message{

    public static AtomicLong reqIdIndex=new AtomicLong(0L);

    public Request(){
        this.msgType=MsgType.request;
    }

    Long reqTime=System.currentTimeMillis();

    String service;
    String method;
    Class paramType;
    String param;

    RequestCallType requestCallType;


    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
