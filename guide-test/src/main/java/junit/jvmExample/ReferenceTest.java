package junit.jvmExample;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.nio.ByteBuffer;
import java.util.concurrent.TimeUnit;

@Slf4j
public class ReferenceTest {



    @Test
    public void test2() throws InterruptedException {
        String obj=new String("ob");
        ThreadLocal threadLocal=new ThreadLocal();
        threadLocal.set(obj);
        threadLocal.get();

    }
        @Test
    public void test() throws InterruptedException {
        ByteBuffer b=ByteBuffer.allocateDirect(1024*10);
        byte[] bytes=new byte[10000];
        String str="sssssfasdfasdfsdfds";
        int len=str.getBytes().length;
        b.putInt(len);
        b.get(bytes,0,len);

        log.info("out:{}",new String(bytes));
        b=null;
        System.gc();
        /**
         * gc后 ，b不再引用对外内存，
         * 回收b时，reference静态方法的一个线程，判单pending是否空
         * 去除后，是否cleaner，则调用clean方法， 回收堆外内存
         */

        TimeUnit.SECONDS.sleep(2);

    }
}
