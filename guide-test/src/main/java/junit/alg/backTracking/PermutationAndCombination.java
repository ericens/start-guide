package junit.alg.backTracking;
import	java.util.ArrayList;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

@Slf4j
public class PermutationAndCombination {

    /**

     如果不想全排列abc了，而是想对abcd进行全排列了，那么我们必须要修改源代码增加一个for循环，
     而且如果排列的数很多的话，那这个循环也太多了吧

     傻办法
     * @param chars
     */
    public void first(char []chars ){
        for (int i = 0; i < chars.length; i++) {
            for (int j = 0; j <chars.length ; j++) {
                for (int k = 0; k < chars.length; k++) {
                    if(i!=j && j!=k && i!=k){
                        log.info("{},{},{}",chars[i], chars[j],chars[k]);
                    }
                }
            }
        }
    }

    @Test
    public void printFirst(){
        char[] a={'a','b','c'};
        first(a);

    }

    /**
     *

     4*3*2*1

     所以第一个output 有 4中选择
     选完之后剔除，第二个有3中选择。


     针对重复的情况 https://leetcode-cn.com/problems/permutations-ii/solution/hui-su-suan-fa-python-dai-ma-java-dai-ma-by-liwe-2/
     */

    int count=0;
    public void second(ArrayList<Character> intput,int len, List<Character> output){
        if(len== output.size()){
            log.info("..{},{}",++count,output);
            return;
        }

        List haveTry=new ArrayList();
        for (int j = 0; j < intput.size(); j++) {
            ArrayList<Character> tmp=(ArrayList<Character>)intput.clone();

            Character c = tmp.remove(j); //尝试，一共4种选择，
            if(!haveTry.contains(c)){
                haveTry.add(c);
                output.add(c);               //尝试，保存这种选择的结果
                second(tmp,len,output);
                output.remove(c); //回退
            }
        }


    }

    /**
     组合


     //    https://leetcode-cn.com/problems/combinations/
     * @param intput
     * @param len
     * @param output
     *
     *
     */

    public void secondCombine(ArrayList<Character> intput,int len, List<Character> output){
        if(len== output.size()){
            log.info("..{},{}",++count,output);
            return;
        }
        for (int j = 0; j < intput.size(); j++) {
            ArrayList<Character> tmp=(ArrayList<Character>)intput.clone();

            Character c = tmp.remove(j); //组合，不关心顺序，那么我只保留一个顺序，最后一个元素最大的情况
            if(!output.isEmpty() && output.get( output.size() -1)  > c) {
                continue;
            }
            output.add(c);               //尝试，保存这种选择的结果
            secondCombine(tmp,len,output);
            output.remove(c); //回退

        }


    }


    @Test
    public void printSecond(){

        count=0;
        ArrayList<Character> list=new ArrayList<Character> ();
        list.addAll(Arrays.asList('a','b','c','d'));
        second(list,4,new ArrayList<Character>());
        log.info("the :{}",count);


        //输出长度不为 全部
        count=0;
        list.clear();
        list.addAll(Arrays.asList('1','2','3','4'));
        second(list,2,new ArrayList<Character>());
        log.info("the :{}",count);

        //重复情况
        count=0;
        list.clear();
        list.addAll(Arrays.asList('1','1','2','3'));
        second(list,4,new ArrayList<Character>());
        log.info("the :{}",count);


        //输出长度不为 全部
        count=0;
        list.clear();
        list.addAll(Arrays.asList('1','2','3','4'));
        secondCombine(list,2,new ArrayList<Character>());
        log.info("the :{}",count);

    }






}
