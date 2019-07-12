package org.zlx.rpc.rpcFrame.io;

import com.alibaba.fastjson.JSON;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.zlx.rpc.rpcFrame.entity.*;
import org.zlx.rpc.rpcFrame.io.client.ResponseFuture;
import org.zlx.rpc.rpcFrame.io.server.Invocation;

import java.io.IOException;
import java.nio.channels.SocketChannel;
import java.util.Map;
import java.util.concurrent.*;

@Slf4j
@Data

public class Dispatcher {
    public  Role role;
    /**
     * 用于client端， 识别返回的Future
     */
    public static ConcurrentHashMap<Long,ResponseFuture> reqIdFutureMap =new ConcurrentHashMap();

    /**
     * 用于识别消息，来源于哪个channel, 返回response时候，返回到原来的channel
     */
    Map<Long,SocketChannel> receivedMsgIdChannelMap =new ConcurrentHashMap<>();

    /**
     *  执行业务逻辑的线程
     */
    ExecutorService bizThreadPools = Executors.newFixedThreadPool(4);


    ISender iSender;

    public Dispatcher(ISender iSender){
        this.iSender=iSender;
    }

    /**
     * TODO 加入 req时间搓比较，不处理超时的信息。而是直接丢失，记录日志。
     * @param str
     * @param socketChannel
     * @throws TimeoutException
     * @throws IOException
     */
    public  void doReceive(String str, SocketChannel socketChannel) throws TimeoutException, IOException {
        Message msg=JSON.parseObject(str,Message.class);

        receivedMsgIdChannelMap.put(msg.getReqId(),socketChannel);
        if(msg.getMsgType().equals(MsgType.request)){
            /**
             * TODO done  为什么无效   收到请求消息一定是server
             *  断言的使用需要指定启动一下，在命令行中输入java就可以看到它的启动参数为-ea和-enableassertions，
             *  idea: enableassertions
             *  命令行: -ea
             */
            assert role.equals(Role.server) : "收到的request消息，不是server";
            this.dealRequest(str);
        }
        else if(msg.getMsgType().equals(MsgType.response)){
            //收到请求消息，一定是client
            assert role.equals(Role.client) :"收到的response消息，不是client";

            this.dealResponse(str);
        }
        else {
            log.error("error msg found :{}",msg);
        }

    }

    private   void dealRequest(String  str) throws TimeoutException, IOException {
        Request request=JSON.parseObject(str,Request.class);
        try {
            Invocation invocation=new Invocation(this,request);
            bizThreadPools.execute(invocation);
        }catch (Exception e){
            //TODO  done 执行失败时，通知client，远程方法执行失败的原因
            Response response=new Response();
            response.setReqId(request.getReqId());
            response.setResp("服务方执行失败"+e.getMessage());
            sendResponse(response);
        }
    }

    private   void dealResponse(String  str) {
        Response response=JSON.parseObject(str,Response.class);
        //通知业务线程，已经调用成功了
        ResponseFuture responseFuture=reqIdFutureMap.get(response.getReqId());
        responseFuture.setResponse(response);
    }


    private void oneWayCall(Request req){

    }

    private Response syncCall(Request req) throws IOException {
        ResponseFuture future=new ResponseFuture(req);
        reqIdFutureMap.put(req.getReqId(),future);
        //tcp的client端发送消息,默认cient只有一个channel.
        iSender.sendMsg(null,req);
        //TODO done 阻塞业务线程
        try {
            return future.get(3000,TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            log.error("get message error",e);
        } catch (TimeoutException e) {
            //TODO done 超时进行封装，抛出异常：包含有意义信息：req，
            //TODO done 而不是返回null,业务继续进行，而是终止本次调用
            log.error("rpc调用超时：{}",req,e);
            new TimeoutException(e.getMessage());
        }
        return  null;
    }


    public   Response sendRequest(Request req) throws IOException {
        Response response=null;
        if (req.getReqId() == null) {
            //TODO done 发送request消息, 确保req_id, future
            req.setReqId(Request.reqIdIndex.incrementAndGet());
        }
        req.setRequestCallType(req.getRequestCallType()==null? RequestCallType.sync: req.getRequestCallType());
        switch (req.getRequestCallType()) {
            case sync:
               response=syncCall(req);
            case async:
            case callback:
                //TODO 增加异步回调接口
                break;

            case oneway:
                oneWayCall(req);
                break;
            default:
                break;
        }
        return response;
    }

    public void sendResponse(Message message) throws IOException{

        //返回数据直接调用io线程， 用户线程立马返回
        //TODO done 根据channel 发送信息
        //tcp的server 端发送消息, 发送到消息的源channel,
        SocketChannel socketChannel = receivedMsgIdChannelMap.get(message.getReqId());
        iSender.sendMsg(socketChannel,message);
        //清楚
        receivedMsgIdChannelMap.remove(message.getReqId());
    }


}
