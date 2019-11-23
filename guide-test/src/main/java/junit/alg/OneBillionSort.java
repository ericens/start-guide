package junit.alg;

import org.apache.commons.lang3.RandomUtils;

import java.util.BitSet;
import java.util.HashMap;

public class OneBillionSort {
    int max=1*1000*1000*1000;
    BitSet bitSet  = new BitSet(max/32);

    HashMap<Integer,Integer> m=new HashMap();

    /**
     *
     * 1. 很少重复，则m 的元素很少，m的大小很小。
     * 2. 很多重复，m的元素很多，m很多。
     *    10亿个，最平均， 5亿个是重复的，都是重复了。    重复度为2.
     *    能不能计算，最大的重复度呢?
     *       比如1个数，从重复度为1亿。1亿个相同的，9亿个相同的， 4.5亿*2一样。
     *       4
     *
     */

    public void generate(){
        //1亿个
        for (int i = 0; i <1000 ; i++) {
           int v=RandomUtils.nextInt(0,max);
           if(bitSet.get(v)==true){
               //有重复，则放在map里面
               Integer value=m.putIfAbsent(v,2);
               if(value!=null){
                   m.put(v,++value);
               }
           }

           bitSet.set(v,true);
        }

        //此时，数据已经放在，bitSet里面了， 打印只是展示出来。
        // 从大到小访问，就是   逆序，为true的就是，有效数据。
        // 从大到小访问，就是   顺序，
        for (int i = max; i >0  ; i--){
            if (bitSet.get(i)==true){
                System.out.println(i);
            }
        }
    }
    /**
     * 10,  int[0]的10位
     * 50， int[1],  52-32为
     */
    public void setBit(int value,boolean zero){
        int slot=value/32;
        int bit=value%32;
    }


    public static void main(String[] args) {
        int [] array = new int [] {1,2,3,22,0,3};
        BitSet bitSet  = new BitSet(6);
        //将数组内容组bitmap
        for(int i=0;i<array.length;i++)
        {
            bitSet.set(array[i], true);
        }
        System.out.println(bitSet.size());
        System.out.println(bitSet.get(3));

        OneBillionSort oneBillionSort=new OneBillionSort();
        oneBillionSort.generate();

    }





}
