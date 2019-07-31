package zlx;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * Created by ericens on 2017/5/18.
 */
@Slf4j
public class MyRateLimitor {

    /**
     *
     */
    public enum LimitModel {
        BlocK,
        NoNBlock
    }

    int qps;
    Double singleWaitNano;
    LimitModel limitModel;
    Queue<Long> queue;

    long NanoConstantToMS=1000*1000;

    public MyRateLimitor(int qps){
        this(qps, LimitModel.BlocK);
    }
    public MyRateLimitor(int qps, LimitModel limitModel){
        this.limitModel = limitModel;
        this.qps=qps;
        this.singleWaitNano =(1000d/qps)*NanoConstantToMS;
        this.queue=new ArrayDeque(qps+100);
    }

    public boolean acquire(){
        return doAcquire(0L);
    }

    public boolean acquire(long reqId){
        return doAcquire(reqId);

    }
    public boolean doAcquire(long reqId){
        boolean needWait=true;
        Double waitTime=0D;

        Long now=System.nanoTime();
        Long oldest=queue.peek();
        if(oldest==null){
            //第一次
            needWait=false;
        }else {
            if(now-oldest>= queue.size()* singleWaitNano){
                //间隔 够久了
                needWait=false;
            }
            else{
                // 价格不够久，要等待
               waitTime=  queue.size()* singleWaitNano - (now-oldest);
               needWait=true;
            }
        }

        log.debug("reqId:{}, now-oldest:{},size:{},needWait:{},waitTime:{}",reqId,now-(oldest==null?now:oldest),queue.size(),needWait,waitTime/NanoConstantToMS);
        if(needWait){
            switch (limitModel){
                case NoNBlock:
                    return false;
                case BlocK:
                default:
                    try {
                        Thread.sleep(waitTime.longValue()/NanoConstantToMS);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
        queue.add(now);
        return true;
    }



    public static void main(String[] args) {
        log.debug("this is sl4j debug");
        log.info("this is sl4j info");
        log.warn("this is sl4j warn");
        log.error("this is sl4j error");
    }




}
