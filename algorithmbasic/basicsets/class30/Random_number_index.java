package algorithmbasic.basicsets.class30;

import java.util.Random;

/**
 * 398. 随机数索引
 * 给你一个可能含有 重复元素 的整数数组 nums ，请你随机输出给定的目标数字 target 的索引。你可以假设给定的数字一定存在于数组中。
 * 实现 Solution 类：
 * Solution(int[] nums) 用数组 nums 初始化对象。
 * int pick(int target) 从 nums 中选出一个满足 nums[i] == target 的随机索引 i 。如果存在多个有效的索引，则每个索引的返回概率应当相等。
 */
public class Random_number_index {
    int[] arr;
    Random random;
    public Random_number_index(int[] nums) {
        arr = nums;
        random = new Random();
    }
    public int pick(int target) {
        int res = -1;
        int count = 0;
        for (int i = 0; i < arr.length; i++) {
            if(arr[i] == target) {
                count++;
                if(random.nextInt(count) == 0) {
                    res = i;
                }
            }
        }
        return res;
    }
}
