package junit.thread;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Slf4j
public class ThreadPoolx {

    int COUNT_BITS = Integer.SIZE - 3;
    int CAPACITY   = (1 << COUNT_BITS) - 1;

    int RUNNING    = -1 << COUNT_BITS;
    int SHUTDOWN   =  0 << COUNT_BITS;
    int STOP       =  1 << COUNT_BITS;
    int TIDYING    =  2 << COUNT_BITS;
    int TERMINATED =  3 << COUNT_BITS;

    @Override
    public String toString() {
        String str= "ThreadPoolx{" +
                "COUNT_BITS=" + Integer.toString(COUNT_BITS) + "\n"+
                ", CAPACITY=" + Integer.toString(CAPACITY) +"\n"+
                ", RUNNING=" + Integer.toString(RUNNING) +"\n"+
                ", SHUTDOWN=" + Integer.toString(SHUTDOWN) +"\n"+
                ", STOP=" + Integer.toString(STOP) +"\n"+
                ", TIDYING=" + Integer.toString(TIDYING) +"\n"+
                ", TERMINATED=" + Integer.toString(TERMINATED )+"\n"+
                '}';

        String str2= "ThreadPoolx{" +
                "COUNT_BITS=" + Integer.toBinaryString(COUNT_BITS) + "\n"+
                ", CAPACITY=" + Integer.toBinaryString(CAPACITY) +"\n"+
                ", RUNNING=" + Integer.toBinaryString(RUNNING) +"\n"+
                ", SHUTDOWN=" + Integer.toBinaryString(SHUTDOWN) +"\n"+
                ", STOP=" + Integer.toBinaryString(STOP) +"\n"+
                ", TIDYING=" + Integer.toBinaryString(TIDYING) +"\n"+
                ", TERMINATED=" + Integer.toBinaryString(TERMINATED )+"\n"+
                '}';

        return str + str2;
    }

    volatile int state=3;

    static Lock l=new ReentrantLock();
    static final Condition c=l.newCondition();

    public static void main2(String[] args) throws InterruptedException {

        ThreadPoolExecutor f=new ThreadPoolExecutor(1,1,4,TimeUnit.HOURS,new ArrayBlockingQueue<>(4));
        List<Future> list=new ArrayList();
        for (int i = 0; i < 4; i++) {
            Future<String> future=f.submit( ()->
            {
                try{
                    log.info("start...");
                    c.await();
                    log.info("end...");
                }catch (Exception e){
                    e.printStackTrace();
                }
                return "xx";
            });
            list.add(future);
        }


        for (int i = 0; i < list.size() ; i++) {
            log.info("ss{}",f.getQueue().size());
            list.get(i).cancel(false);
        }

        for (int i = 0; i < 10 ; i++) {
            l.lock();
            c.signal();
            log.info(i+"i{}",f.getQueue().size());
            l.unlock();
        }



        Thread.sleep(30000);
    }




    public static void main(String[] args) throws InterruptedException {
        main2(args);

        int HASH_BITS = 0x7fffffff; // usable bits of normal node hash
//        int HASH_BITS = 0x7f ff ff ff; // usable bits of normal node hash


        int h="2222222".hashCode();
        int  xx=(h ^ (h >>> 16)) & HASH_BITS;

        log.info("sss:{},{},{}",h,Integer.toBinaryString(h),Integer.toHexString(h));
        log.info("sss:{},{},{}",h,Integer.toBinaryString(h >>> 16),Integer.toHexString(h >>> 16));
        log.info("sss:{},{},{}",h,Integer.toBinaryString(h ^ (h >>> 16)),Integer.toHexString(h ^ (h >>> 16)));

        ThreadPoolx x=new ThreadPoolx();
        ThreadPoolExecutor th;

        CountDownLatch c=new CountDownLatch(4);

        ReentrantLock reentrantLock=new ReentrantLock(false);
        reentrantLock.lockInterruptibly();

        ReentrantReadWriteLock rwl=new ReentrantReadWriteLock(false);
        rwl.writeLock().lock();
        rwl.readLock().lock();  //锁降级

        rwl.writeLock().unlock();
        rwl.readLock().unlock();


        ExecutorService service = Executors.newFixedThreadPool(3);

        ArrayBlockingQueue arrayBlockingQueue=new ArrayBlockingQueue(3);
        log.info("x:{}",x);
    }
}
