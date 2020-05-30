package junit.thread;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

@Slf4j
public class SharedLockXX {


    @Test
    public void test() throws InterruptedException {
        int i = 0;
        CountDownLatch countDownLatch=new CountDownLatch(2);
        List<Thread> list=new ArrayList();


        for (; i < 2; i++) {
            T t=new T("xxx "+i,countDownLatch);
            list.add(t);
            t.start();
        }

        for (; i < 5; i++) {
            T2 t=new T2("xxx "+i,countDownLatch);
            list.add(t);
            t.start();
        }

        for (int j = 0; j < list.size() ; j++) {
            list.get(j).join();
        }




    }
    public class T extends Thread {
        CountDownLatch countDownLatch;
        public T (String name,CountDownLatch countDownLatch){
            this.setName(name);
            this.countDownLatch=countDownLatch;
        }

        @Override
        public void run(){
            try {
                Thread.sleep(30*1000);
                log.info("xx{} begin countdown", this.toString() );
                countDownLatch.countDown();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public class T2 extends Thread {
        CountDownLatch countDownLatch;
        public T2 (String name,CountDownLatch countDownLatch){
            this.setName(name);
            this.countDownLatch=countDownLatch;
        }

        @Override
        public void run(){
            try {
                log.info("xx{} begin await", this.toString() );
                countDownLatch.await();
                log.info("xx{} done", this.toString() );
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
