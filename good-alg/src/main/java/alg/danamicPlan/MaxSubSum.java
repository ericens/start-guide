package junit.alg.danamicPlan;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

@Slf4j
public class MaxSubSum {

    /**
     https://leetcode-cn.com/problems/maximum-subarray/solution/zui-da-zi-xu-he-cshi-xian-si-chong-jie-fa-bao-li-f/     *

     * 输入: [-2,1,-3,4,-1,2,1,-5,4],
     * 输出: 6
     * 解释: 连续子数组 [4,-1,2,1] 的和最大，为 6。



     * @param a
     */

    public int maxSum(int a[]){
        //表示截止dp[i], 截止第i为止的，最大子序列和，理解错了
        //这步很难理解。
        //应该是 以a[i]结尾的最大子序列和，也就是这个和一定包含了a[i]的
        int dp[]=new int[a.length];

        int i=0;
        int result=dp[0]=a[0];
        i++;
        while (i< a.length){
            dp[i]= Math.max(dp[i-1]+a[i],a[i]);
            log.info("dp:{}",dp);
            result=Math.max(dp[i],result);  // 找到以每个位置结尾的最大子序列和 ，这个各个位置就可以
            i++;
        }


        return result;
    }

    @Test
    public void test(){
        int a[]={-2,1,-3,4,-1,2,1,-5,4};
        maxSum(a);
    }
}
