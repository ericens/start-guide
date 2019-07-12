package org.zlx.rpc.rpcFrame.entity;

import com.alibaba.fastjson.JSON;
import lombok.Data;

@Data
public class Message {
    Long ReqId;
    MsgType msgType;

    //父类message没有 定义toString方法，response也没有，所以使用Object自己的

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
