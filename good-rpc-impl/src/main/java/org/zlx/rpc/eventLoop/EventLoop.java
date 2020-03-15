package org.zlx.rpc.eventLoop;
import	java.util.concurrent.Executors;
import java.util.concurrent.*;
import	java.util.Arrays;
import java.util.*;
import java.util.concurrent.CompletableFuture;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;

import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Function;

/**
 *
 思考：


 多线程编程,
 异步编码  ,每个线程仍然在io总阻塞，依靠叠加线程数，提高并发数。

 事件驱动编码， 1个线程就可以支持很高的qps, 像事件一样思考
 */

@Slf4j
public class EventLoop {
//    ExecutorService executorService=Executors.newCachedThreadPool(); todo 一直增加到100多个线程

    ExecutorService executorService=Executors.newFixedThreadPool(10);

    static AtomicLong eventId =new AtomicLong(0);
    static final AtomicLong REQ_Id =new AtomicLong(0);
    @Data
    public static class EventContext{
        static public long getNextEventId(){
            return eventId.incrementAndGet();
        }
        String eventContextName;

    }
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    static class Event{
        long id;
        EventType eventType;
        Object originData;
        Object responseData;
    }
    enum EventType{
        clientReq,
        redisReponse,
        mysqlResponse,
        rpcRespons
    }

    @Data
    static  class SocketChannel{
        LinkedList sendChannel=new LinkedList();
        LinkedList receChannel=new LinkedList();
    }

    SocketChannel socketChannel = new SocketChannel();

    public void startLoop(){
        new Thread(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                log.debug("start server eventloop");
                while(true){
                    Event data=(Event)socketChannel.getSendChannel().poll();
                    if(data==null){
                        //todo 如果 TimeUnit.MILLISECONDS.sleep(1);。 后面的流程会 一直不走，statistic.serverReqCount 一直未0;
                        //这事什么原因呢？
                        TimeUnit.MILLISECONDS.sleep(1);
                        continue;
                    }
                    //** 这就是各个事件，需要根据 上层定义的流程代码。 确定事件A, 应该触发什么handler的顺序。以及代码的调用流程。
                    switch (data.eventType){
                        case clientReq:
                            break;
                        case redisReponse:
                            break;
                        case mysqlResponse:
                            break;
                        case rpcRespons:
                            break;
                    }

                    statistic.serverReqCount++;


                    RedisHandler redisHandler=new RedisHandler();

                    CompletableFuture redisFuture=redisHandler.apply(data);

                    redisFuture.thenAccept( e->{
                        //写入返回socket
                        socketChannel.getReceChannel().offer(e);
                        statistic.serverResponseCount++;
                    });

                }
            }
        }).start();
    }

    Map reqMap=new ConcurrentHashMap();

    @Data
    class Statistic{
        public long clientReqCount;
        public long serverReqCount;
        public long serverResponseCount;

        public long start,end,elapse;

        public double qpsClientReq;
        public double qpsServerReq;
        public double qpsServerResp;
    }
    Statistic statistic=new Statistic();
    public void startMockClient(){
        new Thread(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                log.debug("start client request:{}");
                while(true){

                    TimeUnit.MILLISECONDS.sleep(RandomUtils.nextInt(2,2));
                    //请求server
                    String reqData=UUID.randomUUID().toString();
                    Long reqIdx= REQ_Id.incrementAndGet();
                    reqMap.put(reqIdx,reqData);

                    Event event=Event.builder().id( reqIdx).originData(reqData).build();
//                    log.info("reqid:{},req:{}", event.getId(),reqMap.get(event.getId()));
                    socketChannel.getSendChannel().offer(event);
                    statistic.clientReqCount++;

                    Event response=(Event)socketChannel.getReceChannel().poll();
                    if(response==null){
//                        TimeUnit.MILLISECONDS.sleep(1);
                        continue;
                    }
//                    log.error("reqid:{},req:{} response:{}", response.getId(),reqMap.get(response.getId()),response.getResponseData());
                }
            }
        }).start();
    }




    //模拟访问redis. 耗时10ms
    class RedisHandler implements Function<Event, CompletableFuture<Event>> {
        public SocketChannel redisChannel=new SocketChannel();
        @SneakyThrows
        @Override
        public CompletableFuture<Event> apply(Event event) {


            CompletableFuture<Event> future = new CompletableFuture<> ();
            executorService.submit(new Runnable() {
                @SneakyThrows
                @Override
                public void run() {
//                    log.info("redis processData reqid:{}, data:{}",event.getId(),event.getOriginData());
                    TimeUnit.MILLISECONDS.sleep(RandomUtils.nextInt(200,200));
                    event.setResponseData( "redis+"+event.getOriginData());
                    future.complete(event);
                }
            });
            return future;
        }
    }




    public static void main(String[] args) {
        EventLoop eventLoop=new EventLoop();
        eventLoop.startMockClient();
        eventLoop.startLoop();
        eventLoop.statistic.start=System.currentTimeMillis();
        Runtime.getRuntime().addShutdownHook(new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        eventLoop.statistic.end=System.currentTimeMillis();
                        eventLoop.statistic.elapse= eventLoop.statistic.end- eventLoop.statistic.start;

                        eventLoop.statistic.qpsClientReq = Double.valueOf(eventLoop.statistic.clientReqCount*1000)/eventLoop.statistic.elapse;
                        eventLoop.statistic.qpsServerReq = Double.valueOf(eventLoop.statistic.serverReqCount*1000)/eventLoop.statistic.elapse;
                        eventLoop.statistic.qpsServerResp= Double.valueOf(eventLoop.statistic.serverResponseCount*1000)/eventLoop.statistic.elapse;
                        log.info("statistic:{}", eventLoop.statistic);

                    }
                }
        ));

    }

}
