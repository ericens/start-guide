package junit.alg.slideWindow;


import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

/**
 * https://leetcode-cn.com/problems/longest-substring-without-repeating-characters/
 * https://leetcode-cn.com/circle/article/GMopsy/
 *
 *

 给定一个字符串，请你找出其中不含有重复字符的 最长子串 的长度。

 示例 1:

 输入: "abcabcbb"
 输出: 3
 解释: 因为无重复字符的最长子串是 "abc"，所以其长度为 3。

 来源：力扣（LeetCode）
 链接：https://leetcode-cn.com/problems/longest-substring-without-repeating-characters
 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。



 */
@Slf4j
public class MaxDiffStr {


    public int maxSubStr(String inputStr){
        if(inputStr==null || inputStr.length()==0){
            return 0;
        }

        int max=0; //已经找到 最长子串
        int start=0,end=0;
        Set<Character> set=new HashSet<>();
        while (end<inputStr.length()){
            //end元素不同
            if(!set.contains(inputStr.charAt(end))){
                set.add(inputStr.charAt(end));
                end++;
                if(end-start >max ){
                    max=end-start;
                }
//                log.info("end++, start:{},end:{},subStr:{}",start,end,inputStr.substring(start,end));
            }
            else{
                //end元素相同
                while( set.contains(inputStr.charAt(end)))
                {
                    Character s=inputStr.charAt(start);
                    set.remove(s);
                    start++;
//                    log.info("start++, start:{},end:{},subStr:{}",start,end,inputStr.substring(start,end));
                }
            }

        }
        return max;
    }

    @Test
    public void test(){

        String strs[]={"abcabcbb","bbbbb","pwwkew"};
        for (int i = 0; i < strs.length; i++) {
            log.info("intput:{},max:{}",strs[i],maxSubStr(strs[i]));
        }

    }
}
