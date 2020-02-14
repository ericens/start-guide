package junit.alg.backTracking;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
public class BackPack {

    /**
     背包问题，0，2，是组合的变体

     * @param intput
     * @param len
     * @param output
     *
     *
     */

    int max=0;
    int count=0;
    public void pack(ArrayList<Integer> intput,int len,int packWeight, List<Integer> output){


        if(max> packWeight){
            return;
        }

        if(allWeight(output)>max ){
            max=allWeight(output);
        }

        for (int j = 0; j < intput.size(); j++) {
            ArrayList<Integer> tmp=(ArrayList<Integer>)intput.clone();

            Integer c = tmp.remove(j); //组合，不关心顺序，那么我只保留一个顺序，最后一个元素最大的情况

            if(allWeight(output)+c<=packWeight){
                output.add(c);               //尝试，保存这种选择的结果
                pack(tmp,len,packWeight,output);
                output.remove(c); //回退
            }

            /**  放在这里会有问题，4的是取值不到
             else{
             if(allWeight(output)>max){
             max=allWeight(output);
             log.info("..{},{},{}",++count,output,max);
             return;
             }
             }
             */
        }

    }
    public int allWeight(List list){
        int sum=0;
        for (int i = 0; i < list.size(); i++) {
            sum += (Integer) list.get(i);
        }
        return sum;
    }




    @Test
    public void test(){

        //输出长度不为 全部
        count=0;

        ArrayList<Integer> list=new ArrayList();
        list.addAll(Arrays.asList(2,5,4));
        pack(list,2,4,new ArrayList<Integer>());
        log.info("the count:{},packWeight:{},max:{}",count,4,max);

        for (int packWeight = 1; packWeight < 16; packWeight++) {
            max=0;
            count=0;
            pack(list,2,packWeight,new ArrayList<Integer>());
            log.info("the count:{},packWeight:{},max:{}",count,packWeight,max);
        }


    }






}
