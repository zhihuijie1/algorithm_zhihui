package algorithmbasic.basicsets.class22;

/**
 * arr是面值数组，其中的值都是正数且没有重复。再给定一个正数aim。
 * 每个值都认为是一种面值，且认为张数是无限的。
 * 返回组成aim的方法数
 * 例如：arr = {1,2}，aim = 4
 * 方法如下：1+1+1+1、1+1+2、2+2
 * 一共就3种方法，所以返回3
 */
public class CoinsWayNoLimit {
    /**
     * 暴力方法
     */
    public static int coinsWay(int[] arr, int aim) {
        //aim == 0 啥也不用选本身就是一种方法。
        if (arr == null || arr.length == 0 || aim < 0) {
            return 0;
        }
        return process(arr, aim, 0);
    }

    //返回的是满足目标的方法数
    //从左到右模型：index位置面额选择a张，下一位置面额选择b张，一直到最后正好凑成aim的所有方法数。
    public static int process(int[] arr, int aim, int index) {
        if (index == arr.length) {
            return aim == 0 ? 1 : 0;
        }
        int ways = 0;
        for (int zhang = 0; aim - zhang * arr[index] >= 0; zhang++) {
            ways += process(arr, aim - zhang * arr[index], index + 1);
        }
        return ways;
    }

    /**
     * dp方法
     */
    public static int dp1(int[] arr, int aim) {
        if (arr == null || arr.length == 0 || aim < 0) {
            return 0;
        }
        int N = arr.length;
        int[][] dp = new int[aim + 1][N + 1];
        dp[0][N] = 1;
        for (int L = N - 1; L >= 0; L--) {
            for (int H = 0; H <= aim; H++) {
                int ways = 0;
                for (int zhang = 0; H - zhang * arr[L] >= 0; zhang++) {
                    ways += dp[H - zhang * arr[L]][L + 1];
                }
                dp[H][L] = ways;
            }
        }
        return dp[aim][0];
    }

    /**
     * 继续dp优化
     */
    public static int dp2(int[] arr, int aim) {
        if (arr == null || arr.length == 0 || aim < 0) {
            return 0;
        }
        int N = arr.length;
        int[][] dp = new int[aim + 1][N + 1];
        dp[0][N] = 1;
        for (int L = N - 1; L >= 0; L--) {
            for (int H = 0; H <= aim; H++) {
                //当前位置的上面位置
                if (H - arr[L] >= 0) {
                    dp[H][L] = dp[H - arr[L]][L] + dp[H][L + 1];
                } else {
                    dp[H][L] = dp[H][L + 1];
                }
            }
        }
        return dp[aim][0];
    }
}