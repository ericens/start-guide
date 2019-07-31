package zlx;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by ericens on 2017/5/18.
 */
@Slf4j
public class MyRateLimitorTest {

    /**
     *
     */


    public void blockTest(){
        MyRateLimitor myRateLimitor=new MyRateLimitor(2000, MyRateLimitor.LimitModel.BlocK);
        Long start=System.currentTimeMillis();
        for(int i=0;i<2000*10000;i++){
            log.debug("result:{}", myRateLimitor.acquire(i));
//            log.debug("result:{}","just run");
        }
        long end=System.currentTimeMillis();

        log.info("elapse:{}", (end-start)/1000);

    }


    public void nonBlockTest(){
        MyRateLimitor myRateLimitor=new MyRateLimitor(2000, MyRateLimitor.LimitModel.NoNBlock);
        for(int i=0;i<100;i++){
            log.info("result:{}", myRateLimitor.acquire(i));
        }

    }
    public static void main(String[] args) {
        MyRateLimitorTest test=new MyRateLimitorTest();
        test.blockTest();
//        test.nonBlockTest();
    }




}
