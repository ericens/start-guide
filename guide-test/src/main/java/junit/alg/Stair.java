package junit.alg;

import com.sun.tools.javadoc.Start;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

@Slf4j
public class Stair {


    /**
     * 假设你正在爬楼梯。需要 n 阶你才能到达楼顶。
     * 每次你可以爬 1 或 2 个台阶。你有多少种不同的方法可以爬到楼顶呢？
     * 注意：给定 n 是一个正整数。
     * 示例 1：
     * 来源：力扣（LeetCode）
     * 链接：https://leetcode-cn.com/problems/climbing-stairs
     * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
     */

    // a[i] 达到第i阶 的方法
    // a[i]=a[i-1]+a[i-2];
    public int climbStairs(int n) {
        int a[] = new int[n];
        if (n <= 2) {
            return n;
        }
        a[0] = 1; //第一个
        a[1] = 2; //第二个
        a[n - 1] = -1; // 第n个
        for (int i = 2; i < n; i++) {
            a[i] = a[i - 1] + a[i - 2];
        }
        return a[n - 1];
    }

    @Test
    public void climbStairsTest() {
        log.info("{}", climbStairs(1));
        log.info("{}", climbStairs(2));
        log.info("{}", climbStairs(3));
        log.info("{}", climbStairs(4));
        log.info("{}", climbStairs(5));
    }


    /**
     * 一个机器人位于一个 m x n 网格的左上角 （起始点在下图中标记为“Start” ）。
     * 机器人每次只能向下或者向右移动一步。机器人试图达到网格的右下角（在下图中标记为“Finish”）。
     * 问总共有多少条不同的路径？
     * 来源：力扣（LeetCode）
     * 链接：https://leetcode-cn.com/problems/unique-paths
     * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
     * <p>
     * int [i][j]; 从左上角到i行j列的路径数
     * int [i][j]=[i][j-1] +[i-1][j]
     * <p>
     * [0][2]=[0][1]
     */

    public int uniquePaths(int m, int n) {
        int a[][] = new int[m][n];
        for (int i = 0; i < m; i++) {
            a[i][0] = 1;
        }

        for (int j = 0; j < n; j++) {
            a[0][j] = 1;
        }

        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                a[i][j] = a[i][j - 1] + a[i - 1][j];
            }
        }
        return a[m - 1][n - 1];
    }

    @Test
    public void uniquePathsTest() {
        log.info("{}", uniquePaths(3, 3));
        log.info("{}", uniquePaths(4, 4));
        log.info("{}", uniquePaths(5, 5));
        log.info("{}", uniquePaths(6, 6));
        log.info("{}", uniquePaths(7, 3));
    }

    /**


     一个机器人位于一个 m x n 网格的左上角 （起始点在下图中标记为“Start” ）。

     机器人每次只能向下或者向右移动一步。机器人试图达到网格的右下角（在下图中标记为“Finish”）。

     现在考虑网格中有障碍物。那么从左上角到右下角将会有多少条不同的路径？

     来源：力扣（LeetCode）
     链接：https://leetcode-cn.com/problems/unique-paths-ii
     著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
     *
     *
     *
     * @param obstacleGrid
     * @return
     */

    public int uniquePathsWithObstacles(int[][] obstacleGrid) {

        int m=obstacleGrid.length;
        int n=obstacleGrid[0].length;

        if(obstacleGrid[0][0]==1 ){
            //无解
            return 0;
        }
        int d[][]=new int[m][n];

        d[0][0]=1;

        for (int i = 1; i < m; i++) {
            if(obstacleGrid[i-1][0]==0 && obstacleGrid[i][0]==0){
                d[i][0] = d[i-1][0]  ;
            }else{
                d[i][0] = 0;
            }
        }

        for (int j = 1; j < n; j++) {
            if(obstacleGrid[0][j-1]==0 && obstacleGrid[0][j]==0){
                d[0][j] = d[0][j-1]  ;
            }else{
                d[0][j] = 0;
            }
        }

        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {

                int preMax= (obstacleGrid[i][j-1]==0?d[i][j-1]:0) +
                        (obstacleGrid[i-1][j]==0?d[i-1][j]:0);

                d[i][j]=obstacleGrid[i][j]==0 ?preMax:0;

            }
        }

        return d[m-1][n-1];
    }

    @Test
    public void uniquePathsWithObstaclesTest() {
        int z1[][]={
                {0,1}
        };
        log.info("{}",uniquePathsWithObstacles(z1));


        int x[][]={
                {0,0,0},
                {0,1,0},
                {0,0,0}
        };

        log.info("{}",uniquePathsWithObstacles(x));


        int y[][]={
                {0,0,0},
                {0,1,0},
                {0,0,0},
                {0,0,0}
        };
        log.info("{}",uniquePathsWithObstacles(y));

        int z[][]={
                {0,0,0},
                {1,1,1},
                {0,0,0},
                {0,0,0}
        };
        log.info("{}",uniquePathsWithObstacles(z));





    }

    /**
     给定一个代表每个房屋存放金额的非负整数数组，计算你在不触动警报装置的情况下，能够偷窃到的最高金额。
     示例 1:
     输入: [1,2,3,1]
     输出: 4
     解释: 偷窃 1 号房屋 (金额 = 1) ，然后偷窃 3 号房屋 (金额 = 3)。
          偷窃到的最高金额 = 1 + 3 = 4 。

     来源：力扣（LeetCode）
     链接：https://leetcode-cn.com/problems/house-robber
     著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。


     d[i] ： 到第 i个房屋时，可以偷的金额

     d[i]= max(d[i-1]+num  d[i-2]
     举例来说：1 号房间可盗窃最大值为 33 即为 dp[1]=3，2 号房间可盗窃最大值为 44 即为 dp[2]=4，3 号房间自身的值为 22 即为 num=2，

     那么 dp[3] = MAX( dp[2], dp[1] + num )

     * @param nums
     * @return
     */

    public int rob(int[] nums) {

        if(nums.length<=0){
            return 0;
        }

        int d[]=new int[nums.length+1];
        d[0]=0;
        d[1]=nums[0];

        for (int i = 2; i < d.length ; i++) {
            d[i]= Math.max(d[i-1],  d[i-2]+nums[i-1]);
        }

        return d[d.length-1];

    }


    public int rob1(int[] nums) {
        int len = nums.length;
        if(len == 0)
            return 0;
        int[] dp = new int[len + 1];
        dp[0] = 0;
        dp[1] = nums[0];

        for(int i = 2; i <= len; i++) {
            dp[i] = Math.max(dp[i-1], dp[i-2] + nums[i-1]);
        }
        return dp[len];
    }

    @Test
    public void robTest(){
        int ss[] = {2,1,1,2};
        log.info(" xx {}",rob(ss));

        int b[] = {2,1};
        log.info(" xx {}",rob(b));

        int a[] = {1,2,3,1};
        log.info(" xx {}",rob(a));

        int c[] = {2,7,9,3,1};
        log.info(" xx {}",rob(c));

    }


    /**
     * 给定一个整数数组 nums ，找到一个具有最大和的连续子数组（子数组最少包含一个元素），返回其最大和。
     *
     * 示例:
     *
     * 输入: [-2,1,-3,4,-1,2,1,-5,4],
     * 输出: 6
     * 解释: 连续子数组 [4,-1,2,1] 的和最大，为 6。
     * 进阶:
     *

     d[i] 第一个元素的最大和。

     d[i] = max(d[i], d[i-1]+ num[i]
     d[0]=num[0]



     * 来源：力扣（LeetCode）
     * 链接：https://leetcode-cn.com/problems/maximum-subarray
     * @param nums
     * @return
     */
    public int maxSubArray(int[] nums) {
        if(nums.length==0){
            return 0;
        }
        int d[]=new int[nums.length];
        int max=d[0]=nums[0];

        for (int i = 1; i < nums.length; i++) {
            d[i] = Math.max(nums[i], d[i-1] + nums[i] )  ;
            if(d[i]>max){
                max=d[i];
            }
            log.info("d i {}, {} ",i,d [i]);
        }

        return max;
    }

    @Test
    public void maxSubArrayTest(){
        int ss[] = {-2,1,-3,4,-1,2,1,-5,4};
        log.info(" xx {}",maxSubArray(ss));

        int b[] = {2,-1};
        log.info(" xx {}",maxSubArray(b));

        int a[] = {1,2,-3,1};
        log.info(" xx {}",maxSubArray(a));

        int c[] = {2,8,-9,3,1};
        log.info(" xx {}",maxSubArray(c));

    }



    /**
     * 给定一个数组，它的第 i 个元素是一支给定股票第 i 天的价格。
     * 如果你最多只允许完成一笔交易（即买入和卖出一支股票），设计一个算法来计算你所能获取的最大利润。
     * 注意你不能在买入股票前卖出股票。
     * <p>
     * 示例 1:
     * 输入: [7,1,5,3,6,4]
     * 输出: 5
     * 解释: 在第 2 天（股票价格 = 1）的时候买入，在第 5 天（股票价格 = 6）的时候卖出，最大利润 = 6-1 = 5 。
     * 注意利润不能是 7-1 = 6, 因为卖出价格需要大于买入价格。
     * 来源：力扣（LeetCode）
     * 链接：https://leetcode-cn.com/problems/best-time-to-buy-and-sell-stock
     * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
     * <p>
     * [i] 在i天买入，可以获得最大利润
     */

    public int maxProfit(int[] prices) {
        if (prices.length==1)
            return 0;

        int b[] = new int[prices.length - 1];
        for (int i = 0; i < b.length; i++) {
            b[i] = 0;
            for (int j = i+1; j < prices.length; j++) {
                if (prices[j] - prices[i] > b[i]) {
                    b[i] = prices[j] - prices[i];
                }
            }
        }

        int max = 0;
        for (int i = 0; i < b.length; i++) {
            if (b[i] > max) {
                max = b[i];
            }
        }
        return max;
    }

    @Test
    public void maxProfitTest() {
        int a[] = {7, 1, 5, 3, 6, 4};
        log.info("{}", maxProfit(a));

        int b[] = {7, 6, 4, 3, 1};
        log.info("{}", maxProfit(b));

    }
}
