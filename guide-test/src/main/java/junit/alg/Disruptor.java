package junit.alg;

import lombok.extern.slf4j.Slf4j;
import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;


@Slf4j
public class Disruptor {
    AtomicInteger size=new AtomicInteger(0);
    int capacity;
    Object [] elements;



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
                    (Disruptor.class.getDeclaredField("head"));
            tailOffset = unsafe.objectFieldOffset
                    (Disruptor.class.getDeclaredField("tail"));
        } catch (Exception ex) { throw new Error(ex); }
    }

    private volatile int head=0;
    private volatile int tail=0;


    private final boolean compareAndSetHead(int expect, int update) {
        return unsafe.compareAndSwapInt(this, headOffset, expect, update);
    }

    private final boolean compareAndSetTail(int expect, int update) {
        return unsafe.compareAndSwapInt(this, tailOffset, expect, update);
    }
    /*---------*/

    public Disruptor(int capacity){
        this.capacity = capacity;
        this.head = 0;
        this.tail = 0;
        elements=new Object [capacity];
    }

    public Disruptor(){
        this(4);
    }

    public boolean offer(Object o){
        if(tail==head && size.intValue()==capacity){
            log.error("the queue is full, size:{}, tail:{},head:{}",size,tail,head);
            return false;
        }
        elements [tail]=o;
        tail=(tail+1)%capacity;
        size.incrementAndGet();

        return true;
    }

    public boolean safeOffer(Object o) {
        int exp=0;
        int update=0;
        do{
            int expHead=head;
            exp=tail; //获取到tail，到工作内存的exp
            //TODO 判断放在循环 里面。
            if((exp+1)%capacity==expHead ){
                //打印不用使用内存变量，而是局部变量
                log.error("the queue is full, size:{}, tail:{},head:{},object:{}",size,exp,expHead,o);
                return false;
            }
            update=(exp+1) % capacity;
        }while ( !compareAndSetTail(exp,update));

        elements [exp] = o; //更新 工作内存的 exp
        size.incrementAndGet();


        return true;
    }



    public Object take(){
        if(tail==head&& size.intValue()!=capacity){
            log.error("the queue is empty, tail:{},head:{}",tail,head);
            return null;
        }
        Object o =elements [head] ;

        head=(head+1)%capacity;
        size.decrementAndGet();

        return o;
    }


    public Object safeTake(){
        int exp=0;
        int update=0;
        do {

            exp=head;
            update=(exp+1) % capacity;
            int exptail=tail;
            if(exptail==exp){
                log.error("the queue is empty, size:{},tail:{},head:{}",size.get(),exptail,exp);
                return null;
            }
        }while (!compareAndSetHead(exp,update));
        Object o =elements [exp] ;
        size.decrementAndGet();
        return o;
    }


    public void inspect(){
        log.info("the queue status, capacity:{}, size:{}, tail:{},head:{}",capacity, size.get(),tail,head);
    }


    public static void main(String[] args) throws InterruptedException {


//        singleTheadTest();
        multiTheadTest();
    }

    public  static  void singleTheadTest(){
        AtomicLong atomicLong=new AtomicLong(0);
        Disruptor disruptor=new Disruptor(3);
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

        int loop=10000*10;
        Disruptor disruptor=new Disruptor(loop-1000);
        ExecutorService executorService= Executors.newFixedThreadPool(80);

        AtomicLong atomicLong=new AtomicLong(0);
        for (int i = 0; i < 1000; i++) {
            executorService.submit(
                    new Runnable() {
                        @Override
                        public void run() {
                            for (int i=0;atomicLong.get()<loop;atomicLong.incrementAndGet()){
                                disruptor.safeOffer("o"+i);
                            }
                        }
                    });
        }

        Thread.sleep(1000);
        atomicLong.set(0);
        for (int i = 0; i < 1000; i++) {
            executorService.submit(
                    new Runnable() {
                        @Override
                        public void run() {
                            for (int i=0;atomicLong.get()<loop;atomicLong.incrementAndGet()){
                                disruptor.safeTake();
                            }
                        }
                    });
        }

        for (int i = 0; i < 50; i++) {
            Thread.sleep(1000);
            disruptor.inspect();
        }





    }

}
