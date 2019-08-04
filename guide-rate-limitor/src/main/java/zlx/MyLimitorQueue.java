package zlx;

import java.util.AbstractQueue;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 实现了 数组存储。避免了扩容等问题。
 * 1. todo ,这个实现仍然有问题。没有调用 poll方法，没有调用remove方法。
 * 2. 主要在 head, tail的实现上，有有问题，导致size 的问题，不会调用remove方法
 * 2. 可以head和tail 可以循环利用
 *
 * 但是看了 ArrayDeque的源码，基本是一样的原理，所以在内存使用方面，表现基本一致。
 *  1. 也是使用了上下限标记，重复利用空间。
 *  2. 不是线程安全
 *  3. 在空间不够时，可以扩容。
 *
 *
 *
 */
public class MyLimitorQueue extends AbstractQueue {
    int size;
    Long array[];

    AtomicInteger latestIndex =new AtomicInteger(0);
    AtomicInteger oldestIndex =new AtomicInteger(0);
    public MyLimitorQueue(int size){
        this.size=size;
        array=new Long[size];
    }


    @Override
    public boolean offer(Object o) {
        array[latestIndex.getAndIncrement()%size]=(Long)o;
        return true;
    }


    @Override
    public Object poll() {
        Long old=null;
        try {
            if(( old=array[oldestIndex.getAndIncrement()%size] )<=0){
                return null;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return old;
    }

    @Override
    public Long peek(){
        Long old=null;
        try {
            if(( old=array[oldestIndex.get()%size] )==null){
                return null;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return old;
    }


    @Override
    public Iterator iterator() {
        return null;
    }

    @Override
    public int size() {
        //
        return latestIndex.get()%size- oldestIndex.get()%size;
    }
}
