package junit.jvmExample;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 java -Djdk.internal.lambda.dumpProxyClasses junit.jvmExample.LambdaTest
 */
@Slf4j
public class LambdaTest {
    interface Encode {
        void encode(Derive person);
    }
    class Base {
        public void encrypt() {
            System.out.println("Base::speak");
        }
    }
    class Derive extends Base {
        @Override
        public void encrypt() {
            System.out.println("Derive::speak");
        }
    }
    public static class MethodReference {
        public static void main(String[] args) {
            Encode encode = Base::encrypt;
            System.out.println(",, "+encode);
        }
    }

    public class HH{
        String h="";
        public void  p(){
            System.out.println("as");
        }
    }

    public static void printString(String s, Print<String> print) {
        print.print(s);
    }
    public static void main(String[] args) {
        HH d= new LambdaTest().new HH();
        d.p();

        System.setProperty("jdk.internal.lambda.dumpProxyClasses","/Users/ericens/tmp");
        for (int i = 0; i<100; i++){
            Consumer<String> c = s -> System.out.println(s);
            System.out.println(c.hashCode());
        }

        printString("test", (x) -> System.out.println(x));

        foreachTest();
    }

    @Test
    public void test(){

        ConcurrentLinkedQueue concurrentLinkedQueue=new ConcurrentLinkedQueue();
        concurrentLinkedQueue.offer(1);
        concurrentLinkedQueue.offer(2);
        concurrentLinkedQueue.offer(3);

        log.info("1, {}",concurrentLinkedQueue.peek());
        log.info("1, {}",concurrentLinkedQueue.peek());
        log.info("1, {}",concurrentLinkedQueue.peek());

        log.info("1, {}",concurrentLinkedQueue.poll());
        log.info("1, {}",concurrentLinkedQueue.poll());
        log.info("1, {}",concurrentLinkedQueue.poll());
    }


    public static void foreachTest(){
        Stream.of(1,2,2).forEach(
                i->log.info(", {}",i)
        );

        List<Integer> list=new ArrayList();
        for (Integer item: list) {
            System.out.println(item);
        }

        System.out.println("xxxxxxxxxxxxx");

        list.stream().forEach(
                item->  System.out.println(item)
        );
    }
}

interface Print<T> {
    public void print(T x);
}