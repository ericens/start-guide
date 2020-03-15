package junit.charSet;

import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;

public class AtomicReferenceDemo {
    static class Pair {
        final private int first;
        final private int second;

        public Pair(int first, int second) {
            this.first = first;
            this.second = second;
        }

        public int getFirst() {
            return first;
        }

        public int getSecond() {
            return second;
        }
    }

    public static void main(String[] args) {
        Pair p = new Pair(100, 200);
        AtomicReference<Pair> pairRef = new AtomicReference<>(p);
        pairRef.compareAndSet(p, new Pair(200, 200));

        System.out.println(pairRef.get().getFirst());



    }

    public static void  go(){
        Pair pair = new Pair(100, 200);
        int stamp = 1;
        AtomicStampedReference<Pair> pairRef = new AtomicStampedReference<Pair>(pair, stamp);
        int newStamp = 2;
        pairRef.compareAndSet(pair, new Pair(200, 200), stamp, newStamp);
    }


}
