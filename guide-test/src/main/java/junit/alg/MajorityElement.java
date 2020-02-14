package junit.alg;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.LinkedList;

/**
 * 给定一个大小为 n 的数组，找到其中的多数元素。多数元素是指在数组中出现次数大于 ⌊ n/2 ⌋ 的元素。
 *
 * 你可以假设数组是非空的，并且给定的数组总是存在多数元素。
 *
 * 示例 1:
 *
 * 输入: [3,2,3]
 * 输出: 3
 * 示例 2:
 *
 * 输入: [2,2,1,1,1,2,2]
 * 输出: 2
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/majority-element
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
@Slf4j
public class MajorityElement {

    public int majorityElement(int[] nums) {
        if(nums == null || nums.length ==0){
            return 0;
        }
        int max=0;
        int count=0;
        for (int i = 0; i < nums.length; i++) {
            if(count==0){
                max = nums [i];
                count=1;
                continue;
            }

            if(nums [i] ==max){
                count++;
            }else {
                count--;
            }
        }

        return max;
    }

    @Test
    public void testM(){
        int a[]={3,2,3};
        log.info("xx:{}",majorityElement(a));


        int b[]={1,3,2,2,2,1,1,1,2,2};
        log.info("xx:{}",majorityElement(b));
    }








}
