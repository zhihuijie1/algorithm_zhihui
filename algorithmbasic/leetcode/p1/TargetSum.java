package algorithmbasic.leetcode.p1;

import java.util.HashMap;

//https://leetcode.com/problems/target-sum/description/
public class TargetSum {
    //方法一：暴力递归
    public int findTargetSumWays1(int[] nums, int target) {
        return process(nums, 0, target);
    }

    //当前位置: index
    //当前目标值: target
    //返回:满足目标值的方法数
    private int process(int[] arr, int index, int target) {
        if (index == arr.length && target == 0) {
            return 1;// 没有数就是一种方法
        }
        if (index == arr.length) {
            return 0;
        }
        //index还没有到头
        return process(arr, index + 1, target - arr[index]) + process(arr, index + 1, target + arr[index]);
    }

    //方法二：记忆性优化
    public int findTargetSumWays2(int[] nums, int target) {
        HashMap<Integer, HashMap<Integer, Integer>> map = new HashMap<>();
        return process2(nums, 0, target, map);
    }

    //当前位置: index
    //当前目标值: target
    //返回:满足目标值的方法数
    private int process2(int[] arr, int index, int target, HashMap<Integer, HashMap<Integer, Integer>> dp) {

        //[1,4,6,2,8,11]
        // 0 1 2 3 4 5
        //     |
        //I_A_B
        //当前位置_还剩下的目标值_后边目标值的方法数
        if (dp.containsKey(index) && dp.get(index).containsKey(target)) {
            return dp.get(index).get(target);
        }
        int ans = 0;
        if (index == arr.length && target == 0) {
            ans = 1;
        } else if (index == arr.length) {
            ans = 0;
        } else {
            ans = process2(arr, index + 1, target - arr[index], dp) + process2(arr, index + 1, target + arr[index], dp);
        }
        if (!dp.containsKey(index)) {
            dp.put(index, new HashMap<>());
        }
        dp.get(index).put(target, ans);
        return ans;
    }
}