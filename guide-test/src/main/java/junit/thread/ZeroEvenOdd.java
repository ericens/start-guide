package junit.thread;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import sun.reflect.Reflection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.function.Function;
import java.util.function.IntConsumer;

@Slf4j
@NoArgsConstructor
public class ZeroEvenOdd {


    @Test
    public void tw() {
        AtomicInteger hashCode = new AtomicInteger();
        int hash_increment = 0x61c88647;
        int size = 32;
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            list.add(hashCode.getAndAdd(hash_increment) & (size - 1));
        }
        System.out.println("original:" + list);
        Collections.sort(list);
        System.out.println("sort:    " + list);
    }

    @Test
    public void ss(){
        int n=4096;
        for (int i = 0; i < 20; i++) {

            log.info(" i:{} ,origin:{},o:{}, n:{}",i,  1<<i, (1<<i) >>> 1,n>>>=1 );
        }
    }
    private int n;

    // printNumber.accept(x) outputs "x", where x is an integer.
    public void zero(IntConsumer printNumber) throws InterruptedException {

    }

    public void even(IntConsumer printNumber) throws InterruptedException {

    }

    public void odd(IntConsumer printNumber) throws InterruptedException {

    }

    Thread z,e,o;
    Lock zL,eL,oL;



}