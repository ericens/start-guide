package zlx;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.ArrayDeque;

/**
 * Created by ericens on 2017/5/18.
 */
@Slf4j
public class MyRateLimitorTest {

    /**
     * 200w qps 跑5分钟。
     */


    int defaultQPS=2;
    long modelTestRunCount=defaultQPS*100;
    long queueTestRunCount=defaultQPS*30;
    @Test
    public void defaultBlockTest(){
        log.info("blocking test start.......................");
        MyRateLimitor myRateLimitor=new MyRateLimitor(defaultQPS, MyRateLimitor.LimitModel.BlocK,new ArrayDeque<>(defaultQPS));
        doTest(myRateLimitor);
        log.info("blocking test end.......................");

    }

    @Test
    public void myQueueBlockTest(){
        log.info("myQueueBlockTest test start.......................");
        MyRateLimitor myRateLimitor=new MyRateLimitor(defaultQPS, MyRateLimitor.LimitModel.BlocK,new MyLimitorQueue(defaultQPS));
        doTest(myRateLimitor);
        log.info("myQueueBlockTest test end.......................");

    }



    @Test
    public void noBlockTest(){
        log.info("noBlocking test start.......................");
        MyRateLimitor myRateLimitor=new MyRateLimitor(defaultQPS, MyRateLimitor.LimitModel.NoNBlock);
        doTest(myRateLimitor);
        log.info("noBlocking test end.......................");

    }





    @Test
    public void myQueueTest(){
        log.info("myQueueTest test start.......................");
        MyRateLimitor myRateLimitor=new MyRateLimitor(defaultQPS, MyRateLimitor.LimitModel.BlocK,new MyLimitorQueue(defaultQPS));
        doTest(myRateLimitor,queueTestRunCount);
        log.info("myQueueTest test end.......................");

    }

    @Test
    public void defaultArrayQueueTest(){
        log.info("defaultArrayQueueTest test start.......................");
        MyRateLimitor myRateLimitor=new MyRateLimitor(defaultQPS, MyRateLimitor.LimitModel.BlocK,new ArrayDeque<>(defaultQPS));
        doTest(myRateLimitor,queueTestRunCount);
        log.info("defaultArrayQueueTest test end.......................");

    }


    public void doTest(MyRateLimitor myRateLimitor, long runCount){
        Long start=System.currentTimeMillis();
        for(long i=0;i<runCount;i++){
            log.debug("result:{}", myRateLimitor.acquire(i));
        }
        long end=System.currentTimeMillis();

        log.info("elapse:{}", (end-start));

    }

    public void doTest(MyRateLimitor myRateLimitor){
        doTest(myRateLimitor,modelTestRunCount);

    }


}
