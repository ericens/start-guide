package junit.alg.slideWindow;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.LinkedList;

/**

 https://leetcode-cn.com/problems/find-all-anagrams-in-a-string/solution/hua-dong-chuang-kou-tong-yong-si-xiang-jie-jue-zi-/

 *
 */

@Slf4j
public class SidingWindowMaximum {


    /*

 给定一个数组 nums，有一个大小为 k 的滑动窗口从数组的最左侧移动到数组的最右侧。
 你只可以看到在滑动窗口内的 k 个数字。滑动窗口每次只向右移动一位。

 返回滑动窗口中的最大值。

  

 示例:

 输入: nums = [1,3,-1,-3,5,3,6,7], 和 k = 3
 输出: [3,3,5,5,6,7]
 解释:

 滑动窗口的位置                最大值
 ---------------               -----
 [1  3  -1] -3  5  3  6  7      3
 1 [3  -1  -3] 5  3  6  7       3
 1  3 [-1  -3  5] 3  6  7       5
 1  3  -1 [-3  5  3] 6  7       5
 1  3  -1  -3 [5  3  6] 7       6
 1  3  -1  -3  5 [3  6  7]      7
  

 提示：

 你可以假设 k 总是有效的，在输入数组不为空的情况下，1 ≤ k ≤ 输入数组的大小。

  

 进阶：

 你能在线性时间复杂度内解决此题吗？

 在真实的面试中遇到过这道题？

 来源：力扣（LeetCode）
 链接：https://leetcode-cn.com/problems/sliding-window-maximum
 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。






初始状态：L=R=0,队列:{}
i=0,nums[0]=1。队列为空,直接加入。队列：{1}
i=1,nums[1]=3。队尾值为1，3>1，弹出队尾值，加入3。队列：{3}
i=2,nums[2]=-1。队尾值为3，-1<3，直接加入。队列：{3,-1}。此时窗口已经形成，L=0,R=2，result=[3]
i=3,nums[3]=-3。队尾值为-1，-3<-1，直接加入。队列：{3,-1,-3}。队首3对应的下标为1，L=1,R=3，有效。result=[3,3]
i=4,nums[4]=5。队尾值为-3，5>-3，依次弹出后加入。队列：{5}。此时L=2,R=4，有效。result=[3,3,5]
i=5,nums[5]=3。队尾值为5，3<5，直接加入。队列：{5,3}。此时L=3,R=5，有效。result=[3,3,5,5]
i=6,nums[6]=6。队尾值为3，6>3，依次弹出后加入。队列：{6}。此时L=4,R=6，有效。result=[3,3,5,5,6]
i=7,nums

作者：hanyuhuang
     https://leetcode-cn.com/problems/sliding-window-maximum/
链接：https://leetcode-cn.com/problems/sliding-window-maximum/solution/shuang-xiang-dui-lie-jie-jue-hua-dong-chuang-kou-2/
来源：力扣（LeetCode）
著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
    */
    @Test
    public void firsttest(){
        int a[]={3,2,-1,0,5,3,6,7};
        log.info("1{},{}",maxSlidingWindow(a,3));
        log.info("2{},{}",maxSlidingWindow2(a,3));

        int b[]={1,3,-1,-3,5,3,6,7};
        log.info("1{},{}",maxSlidingWindow(b,3));
        log.info("2{},{}",maxSlidingWindow2(b,3));
    }




    public int[] maxSlidingWindow2(int[] nums, int k) {
        if(nums == null || nums.length < 2)
            return nums;
        // 双向队列 保存当前窗口最大值的数组位置 保证队列中数组位置的数值按从大到小排序
        LinkedList<Integer> queue = new LinkedList();
        // 结果数组
        int[] result = new int[nums.length-k+1];
        // 遍历nums数组
        for(int i = 0;i < nums.length;i++){
            // 保证从大到小 如果前面数小则需要依次弹出，直至满足要求,只保留一个
            while(!queue.isEmpty() && nums[queue.peekLast()] <= nums[i]){
                queue.pollLast();
            }
            // 添加当前值对应的数组下标
            queue.addLast(i);
            // 判断当前队列中队首的值是否有效，  已经在win之外
            if(queue.peek() <= i-k){
                queue.poll();
            }
            // 当窗口长度为k时 保存当前窗口中最大值
            if(i+1 >= k){
                result[i+1-k] = nums[queue.peek()];
            }
        }
        return result;
    }

    /**
     *
     * @param nums
     * @param k
     * @return
     */

    public int[] maxSlidingWindow(int[] nums, int k) {

        int max=Integer.MIN_VALUE;
        int maxIndex=0;
        int start=0;
        int end=k;

        int ka[]=new int[nums.length-k+1];


        for (int i = 0; i < nums.length-k ; i++) {
            //第一次初始化
            if(i < k ){
                if(max < nums [i]){
                    max   =nums [i];
                    maxIndex = i ;
                }
                if (k-1 == i) {
                    start = 0;
                    end = i;
                    ka[i - k+1] = max;
                }
            }
            else  {
                start ++;
                end ++;
                if(  nums [i]> max &&  start<=maxIndex && maxIndex <= end ){
                    max=max < nums [i] ? nums [i] : max;
                }
                ka[ i-k+1 ]=max;

            }

        }

        return ka;
    }

}
