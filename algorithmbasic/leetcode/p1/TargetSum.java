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

    // 优化点一 :
    // 你可以认为arr中都是非负数
    // 因为即便是arr中有负数，比如[3,-4,2]
    // 因为你能在每个数前面用+或者-号
    // 所以[3,-4,2]其实和[3,4,2]达成一样的效果
    // 那么我们就全把arr变成非负数，不会影响结果的
    // 优化点二 :
    // 如果arr都是非负数，并且所有数的累加和是sum
    // 那么如果target>sum，很明显没有任何方法可以达到target，可以直接返回0
    // 优化点三 :
    // arr内部的数组，不管怎么+和-，最终的结果都一定不会改变奇偶性
    // 所以，如果所有数的累加和是sum，
    // 并且与target的奇偶性不一样，没有任何方法可以达到target，可以直接返回0
    // 优化点四 :
    // 比如说给定一个数组, arr = [1, 2, 3, 4, 5] 并且 target = 3
    // 其中一个方案是 : +1 -2 +3 -4 +5 = 3
    // 该方案中取了正的集合为P = {1，3，5}
    // 该方案中取了负的集合为N = {2，4}
    // 所以任何一种方案，都一定有 sum(P) - sum(N) = target
    // 现在我们来处理一下这个等式，把左右两边都加上sum(P) + sum(N)，那么就会变成如下：
    // sum(P) - sum(N) + sum(P) + sum(N) = target + sum(P) + sum(N)
    // 2 * sum(P) = target + 数组所有数的累加和
    // sum(P) = (target + 数组所有数的累加和) / 2
    // 也就是说，任何一个集合，只要累加和是(target + 数组所有数的累加和) / 2
    // 那么就一定对应一种target的方式
    // 也就是说，比如非负数组arr，target = 7, 而所有数累加和是11
    // 求有多少方法组成7，其实就是求有多少种达到累加和(7+11)/2=9的方法
    // 优化点五 :
    // 二维动态规划的空间压缩技巧

    public int findTargetSumWays3(int[] nums, int target) {
        int sum = 0;
        int n = nums.length;
        for (int i = 0; i < n; i++) {
            sum += nums[i];
        }
        //进行奇偶判断 和 target判断
        return target > sum || ((target & 1) ^ (sum & 1)) != 0 ? 0 : subset(nums, (target + sum) >> 1);
    }

    //dp[i][j]
    //i -- [0,i]这个范围
    //i -- 目标值

    //dp[n][s]

    private static int subset(int[] nums, int s) {
        int n = nums.length;
        int[][] dp = new int[n + 1][s + 1];
        for (int i = 0; i <= n; i++) {
            dp[i][0] = 1;
        }
        for (int i = 1; i <= s; i++) {
            dp[0][i] = 0;
        }
        for (int i = 1; i <= n; i++) {
            for (int j = 0; j <= s; j++) {
                if (nums[i - 1] <= j) {
                    dp[i][j] = dp[i - 1][j - nums[i - 1]] + dp[i - 1][j];
                } else {
                    dp[i][j] = dp[i - 1][j];
                }
            }
        }
        return dp[n][s];
    }
}