package junit.alg.danamicPlan;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

@Slf4j

public class MaxSubProduct {



    /**
     https://leetcode-cn.com/problems/maximum-product-subarray/

     输入: [2,3,-2,4]
     输出: 6
     解释: 子数组 [2,3] 有最大乘积 6。


     * @param a
     */

    public int maxSum(int a[]){
        int dp[]=new int[a.length];// dp[i], 包含第i位的 最大乘积
        int result=dp[0]=a[0];
        for (int i = 1; i < a.length; i++) {

        }
        return 0;
    }

    @Test
    public void test(){
        int a[]={-2,1,-3,4,-1,2,1,-5,4};
        maxSum(a);
    }
}
