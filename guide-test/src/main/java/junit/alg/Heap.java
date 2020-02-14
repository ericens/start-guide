package junit.alg;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Heap {
    int size;
    int capacity;
    int [] data;
    public Heap(int capacity){
        this.size =0;
        // 数组大小，比限制大一
        this.capacity=capacity;
        data = new int [capacity+1];
    }



    public boolean insertBig(int o){
        if(size >=capacity){
            return false; //只能插入多少个。
        }
        ++size;
        data [size] = o;
        int i=size;
        while( i/2 >0  && data [i] >data [ i/2 ] ){
            int tmp=data [i];
            data [ i ]=data [ i/2 ];
            data [ i/2 ]=tmp;
            i= i/2;
        }
        return true;
    }

    public int removeBigMax(){
        if(size<=0){
            return -1;
        }
        int result=data [1];
        data [1]=data [size--];

        int i=1;
        while (true){
            int maxIndex =i;
            if(2*i< size && data[2*i] > data [i])
                maxIndex =2*i;
            if(2*i +1 < size && data[2*i+1] > data [maxIndex])
                maxIndex =2*i+1;
            if(i==maxIndex)
                break;

            int tmp=data [maxIndex];
            data [ maxIndex ]=data [ i ];
            data [ i ]=tmp;
            i= maxIndex;

        }
        return result;
    }

    public boolean insertSmall(int o){
        if(size >=capacity){
            return false; //只能插入多少个。
        }
        ++size;
        data [size] = o;
        int i=size;
        while( i/2 >0  && data [i] < data [ i/2 ] ){
            int tmp=data [i];
            data [ i ]=data [ i/2 ];
            data [ i/2 ]=tmp;
            i= i/2;
        }
        return true;
    }

    public int topK(int o){
        int i=0;
        int result=-1;
        if(o < data [1]){
            return o; // 比最小的小
        }
        if(size >=capacity && o > data [1] ){
             result=data[1]; // 需要替换 第一个
             i=1;
        }
        data [i] = o;
        int minIndex=i;
        while( true){
            if(i*2 <= size  && data [i] > data [ i*2 ])
                minIndex=i*2;
            if (i*2+1 <=size && data [minIndex] > data[i*2+1])
                minIndex = i *2+1;
            if(i==minIndex){
                break;
            }

            int tmp=data [i];
            data [ i ]=data [ minIndex ];
            data [ minIndex ]=tmp;
            i= minIndex;
        }
        return result;
    }

    public void print(){
        StringBuilder stringBuilder = new StringBuilder();
        StringBuilder stringBuilder1 = new StringBuilder();
        for (int i = 1; i <= size; i++) {
            stringBuilder.append(data[i]).append(" , ");
            stringBuilder1.append(i).append(" , ");
        }
        log.info("data:  {}",stringBuilder.toString());
    }


    public static void main(String[] args) {
        small();
//        big();

    }

    public static void  big() {
        int coun=70;
        Heap heap = new Heap(coun);
        for (int i = 1; i <= coun; i++) {
            heap.insertBig(i);
            heap.print();
        }

        for (int i = 1; i <= coun; i++) {
            log.info("{} ",heap.removeBigMax());
        }

    }
    public static void  small() {
        int coun=10;
        Heap smallheap = new Heap(coun);
        for (int i = 10; i >0 ; i--) {
            smallheap.insertSmall(i);
            smallheap.print();
        }

        for (int i = 1; i <= coun*5; i++) {
            smallheap.print();
            log.info("{} ",smallheap.topK(i+10));

        }

    }



}
