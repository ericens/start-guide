package junit.alg;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Test;

import	java.util.List;

import java.util.Arrays;

@Slf4j
public class MaxArea {

    int [] initArray(int size){
        int []arr=new int[size];

        for (int i = 0; i < size ; i++) {
            arr[i]= RandomUtils.nextInt(1,11);
        }

        int arrx[]={2,3,4,5,18,17,6};
        arr=arrx;
        return arr;
    }

    public void print(String message, int arr[]){
        System.out.println("message: "+message);
        StringBuffer stringBuffer=new StringBuffer();
        stringBuffer.append(arr[0]);

        for (int i = 1; i < arr.length ; i++) {
            stringBuffer.append(",").append(arr[i]);
        }
        System.out.println(stringBuffer);
    }

    /**
     * 确实是两两组合了
     * @param height
     * @return
     */

    public int maxArea(int[] height) {
        int maxarea = 0;
        for (int i = 0; i < height.length; i++)
            for (int j = i + 1; j < height.length; j++)
                maxarea = Math.max(maxarea, Math.min(height[i], height[j]) * (j - i));
        return maxarea;
    }


    //这种2分递归的关系并不纯在。
    public int cal二分查找(int[] height) {

        if(height.length==0){
            return 0;
        }
        if(height.length==1){
            return 0;
        }
        if(height.length==2){
            return (height.length-1)* Math.min(height[0],height[1]);
        }



        int start=height[0];
        int end=height[height.length -1];

        int []left= Arrays.copyOfRange(height,0,(height.length-1)/2);
        int []right= Arrays.copyOfRange(height,(height.length-1)/2, height.length );
        int leftarea=cal二分查找(left);
        int rightArea=cal二分查找(right);


        return  Math.max(Math.max(leftarea,rightArea), (height.length-1)* Math.min(start,end));

    }
    //单步累加的关系，不成立
    public int cal单步递进(int[] height) {

        if(height.length==0){
            return 0;
        }
        if(height.length==1){
            return 0;
        }
        if(height.length==2){
            return (height.length-1)* Math.min(height[0],height[1]);
        }


        //这种2分递归的关系并不纯在。
        int start=height[0];
        int end=height[height.length -1];

        int []right= Arrays.copyOfRange(height,1, height.length );
        int rightArea=cal单步递进(right);


        return  Math.max(rightArea,(height.length-1)* Math.min(start,end));

    }


    @Test
    public void test(){
        MaxArea area=new MaxArea();
        int ar[]=area.initArray(5);
        area.print("init:",ar);

        log.info("area :{}",area.maxArea(ar));


    }


}
