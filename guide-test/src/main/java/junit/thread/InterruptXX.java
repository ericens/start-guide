package junit.thread;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

@Slf4j
public class InterruptXX {

    /**
     * 阻塞方法调用前执行interrupt方法
     *
     */
    @Test
    public void teinterrupt_before(){
        log.info("main start");
        Thread.currentThread().interrupt();
        try{
            log.info("main exe sleep:{}",Thread.currentThread().isInterrupted());
            Thread.sleep(6000); //主线程执行2S睡眠，保证t2线程能够正常执行一段时间
            log.info("after main exe sleep");
        }catch (InterruptedException e){
            log.info("oh! shit Exception:{}",Thread.currentThread().isInterrupted());
        }
        log.info("main end:{}",Thread.currentThread().isInterrupted());
    }



    @Test
    public void test1(){
        T2 t2 = new T2();
        t2.start();

        try{
            Thread.sleep(200); //主线程执行2S睡眠，保证t2线程能够正常执行一段时间
        }catch (Exception e){
            System.out.println("main exception");
        }

        t2.interrupt(); //通知t2,你中断了

    }

    class T2 extends Thread {

        @Override
        public void run()
        {
            while (true){
                if(Thread.currentThread().isInterrupted()){
                    System.out.println("111111111");
                }else {
                    System.out.println("22222222");
                }
                System.out.println("33333333333333");
            }
        }
    }

}
