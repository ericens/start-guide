package disruptor;

import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.util.DaemonThreadFactory;
import java.nio.ByteBuffer;

public class LongEventMain {

    public void sort(int array[],int size){
        if(size<512*1024){
            使用归并排序_小数据量时使用();
            //稳定的排序，空间复杂度O(N),最坏时间的为O(N*log(N))
        }else{
            使用快速排序();
            //3点中间值作为 轴povit
            //使用堆，模拟函数调用栈，防止调用栈溢出
            //使用哨兵，减少一次比较
            if(array.length <=4){
                使用插入排序();
                //小数据量是O(N^2) 的实际复杂度不一定是大于 O(N*log(N))

            }
        }
    }

    private void 使用插入排序() {
    }

    private void 使用归并排序_小数据量时使用() {
    }

    private void 使用快速排序() {
    }


    //main 方法，启动入口
    public static void main(String[] args) throws Exception {

        //在该框架中，所有的 task 的包装类被称为 Event，EventFactory 则是 Event 的生产者
        LongEventFactory factory = new LongEventFactory();

        //RingBuffer 的大小，数字为字节数
        //RingBuffer 是框架启动器内部的缓存区，用来存储 event 内的 task 数据
        int bufferSize = 1024;

        //创建一个 Disruptor 启动器，其中 DaemonThreadFactory 是一个线程工厂的实现类
        Disruptor<LongEvent> disruptor = new Disruptor<>(factory, bufferSize, DaemonThreadFactory.INSTANCE);


        //该框架本质上是 生产-消费 设计模式的应用。所有的消费者被冠名为 handler
        //handleEventsWith(...) 方法会在启动器中注册 handler
        //此处的参数是不定数量的，可以有多个消费者，每个消费者都可以获取 Event
        disruptor.handleEventsWith(new LongEventHandler("handler1"),new LongEventHandler("handler2"));

        //启动器开始执行，并获取其内部的缓存区
        RingBuffer<LongEvent> ringBuffer = disruptor.start();

        //创建一个生产者，负责往缓存区内写入数据
        LongEventProducer producer = new LongEventProducer(ringBuffer);

        //官方 demo 中使用了 ByteBuffer 来方便操作，其实非必须
        ByteBuffer bb = ByteBuffer.allocate(8);

        for (long l = 0; true; l++) {
            //将变量 l 作为一个 long 类型的数存入 ByteBuffer 中
            bb.putLong(0, l);
            //将 ByteBuffer 传入生产者的相关方法中，该方法会负责将 ByteBuffer 中的数据写入 RingBuffer
            producer.onData(bb);
            //线程休眠
            Thread.sleep(1000);
        }
    }
}

//Event 类，本质上是数据的封装，是生产者和消费者之间进行数据传递的介质
class LongEvent {
    private long value;

    public void set(long value) {
        this.value = value;
    }

    public long get() {
        return value;
    }
}

//Event 的生产工厂类，必须实现 Disruptor 自带的 EventFactory 接口
class LongEventFactory implements EventFactory<LongEvent> {

    @Override
    public LongEvent newInstance() {
        return new LongEvent();
    }
}

//消费者，必须实现 Disruptor 自带的 EventHandler 接口
class LongEventHandler implements EventHandler<LongEvent> {

    private String handlerName;

    public LongEventHandler(String handlerName){
        this.handlerName = handlerName;
    }

    //此方法为最终的消费 Event 的方法
    @Override
    public void onEvent(LongEvent event, long sequence, boolean endOfBatch) {
        System.out.println("Event " + handlerName + " : " + event.get());
    }
}

//生产者，主要负责往 RingBuffer 中写入数据
//生产者类在框架中并非必须，但是一般情况下都会做一定程度的封装
class LongEventProducer {
    private final RingBuffer<LongEvent> ringBuffer;

    //生产者的构造器负责获取并存储启动器中的 RingBuffer
    public LongEventProducer(RingBuffer<LongEvent> ringBuffer) {
        this.ringBuffer = ringBuffer;
    }

    public void onData(ByteBuffer bb) {
        //sequence 是 RingBuffer 中的一个数据块，类似于一个数据地址
        long sequence = ringBuffer.next();
        try {
            //用数据地址去获取到一个 Event 事件类实例
            LongEvent event = ringBuffer.get(sequence);
            //在实例中存入 ByteBuffer 中的数据
            event.set(bb.getLong(0));
        } finally {
            //发布该数据块，此时消费者们都可以看到该数据块了，可以进行消费
            ringBuffer.publish(sequence);
        }
    }
}
