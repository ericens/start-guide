package junit.alg;
import java.util.ArrayList;
import	java.util.LinkedList;
import java.util.List;

public class SubSet {
    public List<List<Integer>> subsets(int[] nums) {
        List result=new LinkedList();

        for (int i = 1; i <= nums.length; i++) {
            int len=i;
            for (int index = 0; index < nums.length; index++) {
                List sub=new ArrayList();
                for (int k = 0; k < len &&  index+k< nums.length ; k++) {
                    sub.add(nums[index+k]);
                }
                result.add(sub);
            }

        }

        return result;
    }


}
