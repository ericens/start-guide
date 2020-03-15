package log4j2;

import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.LockSupport;

/**
 * sleep 阻塞可以，自旋阻塞可以。
 */
@Log4j2
public class Disruptor_v1 {
    AtomicInteger size=new AtomicInteger(0);
    int capacity;
    Object [] elements;
    AtomicInteger callCount = new AtomicInteger(0);


    /*---------*/
    private static final long headOffset;
    private static final long tailOffset;
    private static final Unsafe unsafe;


    private static Unsafe getUnsafeInstance() throws SecurityException,
            NoSuchFieldException, IllegalArgumentException,
            IllegalAccessException {
        Field theUnsafeInstance = Unsafe.class.getDeclaredField("theUnsafe");
        theUnsafeInstance.setAccessible(true);
        return (Unsafe) theUnsafeInstance.get(Unsafe.class);
    }

    static {
        try {
            unsafe= getUnsafeInstance();

            headOffset = unsafe.objectFieldOffset
                    (Disruptor_v1.class.getDeclaredField("head"));
            tailOffset = unsafe.objectFieldOffset
                    (Disruptor_v1.class.getDeclaredField("tail"));
        } catch (Exception ex) { throw new Error(ex); }
    }

    private volatile long head=0;
    private volatile long tail=0;


    private final boolean compareAndSetHead(long expect, long  update) {
        return unsafe.compareAndSwapLong(this, headOffset, expect, update);
    }

    private final boolean compareAndSetTail(long expect, long update) {
        return unsafe.compareAndSwapLong(this, tailOffset, expect, update);
    }
    /*---------*/

    public Disruptor_v1(int capacity){
        this.capacity = capacity;
        this.head = 0;
        this.tail = 0;
        elements=new Object [capacity];
    }

    public Disruptor_v1(){
        this(4);
    }

    public boolean offer(Object o){
        if(tail==head && size.intValue()==capacity){
            log.error("the queue is full, size:{}, tail:{},head:{}",size,tail,head);
            return false;
        }
        elements [(int) tail]=o;
        tail=(tail+1)%capacity;
        size.incrementAndGet();

        return true;
    }

    public boolean safeOffer(Object o) throws InterruptedException {
        long exptail=0;
        long update=0;
        long expHead;
        do{
            expHead=head;
            exptail=tail; //获取到tail，到工作内存的exp
            //TODO 判断放在循环 里面。


//            if(exptail==expHead) {
//                //之前已经空了
//                sigalNotEmpty();
//            }

            if ((exptail+1)%capacity==expHead ){

                //打印不用使用内存变量，而是局部变量
//                log.error("is full,capacity:{}, size:{}, tail:{},head:{},object:{}",capacity,size.intValue(),exptail,expHead,o);
                Thread.sleep(100);
//                parkOnFull();
//                return false;
            }
            update=(exptail+1) % capacity;
        }while ( ((exptail+1)%capacity==expHead)  ||  !compareAndSetTail(exptail,update));

        elements [(int) exptail] = o; //更新 工作内存的 exp
        size.incrementAndGet();




        return true;
    }

    List<Thread> fullThreadList=new Vector<>();
    List<Thread> emptyTreadList=new Vector<>();

    public synchronized void  parkOnFull(){
        fullThreadList.add(Thread.currentThread());
        LockSupport.park();

    }

    public  void sigalNotFull(){
        signa(fullThreadList);
    }
    public void sigalNotEmpty(){
        signa(emptyTreadList);
    }

    public synchronized void parkOnEmpty(){
        emptyTreadList.add(Thread.currentThread());
        LockSupport.park();
    }



    public synchronized void signa(List list){
        Iterator iterator=list.iterator();
        while (iterator.hasNext())
        {
            Thread t=(Thread)iterator.next();
            LockSupport.unpark(t);
        }
        list.clear();
    }


    public Object take(){
        if(tail==head&& size.intValue()!=capacity){
            log.error("the queue is empty, tail:{},head:{}",tail,head);
            return null;
        }
        Object o =elements [(int) head] ;

        head=(head+1)%capacity;
        size.decrementAndGet();

        return o;
    }


    public Object safeTake() throws InterruptedException {
        long exphead=0;
        long update=0;
        long exptail=0;
        do {
            exphead=head;
            update=(exphead+1) % capacity;
            exptail=tail;

//            if((exptail+1)%capacity==exphead ) {
//                //之前已经满了
//                sigalNotFull();
//            }


            if (exptail==exphead){
//                log.error("is empty,capacity:{}, size:{},tail:{},head:{}",capacity,size.get(),exptail,exphead);
//                parkOnEmpty();
                Thread.sleep(10);//                return null;
            }
        }while (  (exptail==exphead) || !compareAndSetHead(exphead,update));

        Object o =elements [(int) exphead] ;
        size.decrementAndGet();



        return o;
    }

    public void add(){
        callCount.incrementAndGet();

        long exphead=0,update=0;
        do{
            exphead=head;
            update=head+1;
        }
        while (!compareAndSetHead(exphead,update));

    }

    public void inspect(){
        log.info("callcount:{},capacity:{},size:{},tail:{},head:{},fullWaitingSize:{},emptyWaitingSize:{}",callCount.get(),capacity, size.get(),tail,head, fullThreadList.size(), emptyTreadList.size());
    }


    public static void addTestcc() throws InterruptedException {
        Disruptor_v1 disruptor=new Disruptor_v1(0);

        AtomicInteger atomic = new AtomicInteger(0);
        ExecutorService executorService= Executors.newFixedThreadPool(20);
        for (int i = 0; i < 10; i++) {
            executorService.submit(new Runnable() {
                                       @Override
                                       public void run() {
                                           for (int j = 0; j < 10000; j++) {
                                               disruptor.add();
                                           }
                                       }
                                   }
            );
        }


        for (int i = 0; i < 20; i++) {
            disruptor.inspect();
            Thread.sleep(1000);
            log.info("call .. :{}",atomic.get());
        }



    }

    public static void main(String[] args) throws InterruptedException {
//        addTestcc();

//        singleTheadTest();
        multiTheadTest();
    }

    public  static  void singleTheadTest() throws InterruptedException {
        AtomicLong atomicLong=new AtomicLong(0);
        Disruptor_v1 disruptor=new Disruptor_v1(3);
        for (int j = 0; j < 4; j++) {
            for (int i=0;i<10;i++){
                if(true== disruptor.safeOffer(("o"+atomicLong.get()))){
                    atomicLong.incrementAndGet();
                }

            }

            for (int i=0;i<5;i++){
                log.info("take {}",disruptor.safeTake());

            }
        }

    }

    public static void multiTheadTest() throws InterruptedException {
        log.info("the max  21 4748 3647 :{}",Integer.MAX_VALUE);

        int loop=100*10;
        Disruptor_v1 disruptor=new Disruptor_v1(10);
        ExecutorService executorService= Executors.newFixedThreadPool(20);
        ExecutorService executorService2= Executors.newFixedThreadPool(20);

        for (int index = 0; index < 20; index++) {
            executorService.execute(
                    new Runnable() {
                        @Override
                        public void run() {
                            for (int i = 0; i <100000 ; i++) {
                                try {
                                    disruptor.safeOffer("o" + i);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    });
        }

        Thread.sleep(1000);
        AtomicLong atomicLong2=new AtomicLong(0);

        for (int index = 0; index < 20; index++) {
            executorService2.execute(
                    new Runnable() {
                        @Override
                        public void run() {
                            while (true){
                                try {
                                    disruptor.safeTake();
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                atomicLong2.incrementAndGet();
                            }
                        }
                    });
        }

        for (int i = 0; i < 200; i++) {
            Thread.sleep(1000);
            disruptor.inspect();
            log.info("the receive count:{}",atomicLong2.get());
        }





    }

}
