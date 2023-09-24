package algorithmbasic.basicsets.class23;

/**
 * arr是面值数组，其中的值都是正数且没有重复。再给定一个正数aim。
 * 每个值都认为是一种面值，且认为张数是无限的。
 * 返回组成aim的最少货币数
 */
public class MinCoinsNoLimit {
    /**
     * 暴力递归
     */
    public static int minCoins(int[] arr, int aim) {
        if (arr == null || arr.length == 0 || aim <= 0) {
            return 0;
        }
        return process2(arr, aim, 0);
    }

    //从左往右，进行选择，返回正好组成aim的最少货币数。
    //用 Integer.MAX_VALUE表示搞不定。
    public static int process(int[] arr, int aim, int index) {
        if (index == arr.length) {
            return aim == 0 ? 0 : Integer.MAX_VALUE;
        }
        //coins是收集可以搞定的情况下，最少货币张数。
        int coins = Integer.MAX_VALUE;
        //当前位置的货币拿几张。
        for (int zhang = 0; aim - zhang * arr[index] >= 0; zhang++) {
            int p = process(arr, aim - zhang * arr[index], index + 1);
            //只有在可以搞定的情况下才可以计算最少货币的张数。
            if (p != Integer.MAX_VALUE) {
                coins = Math.min(coins, zhang + p);
            }
        }
        return coins;
    }

    //搞不定就返回-1.
    public static int process2(int[] arr, int aim, int index) {
        if (index == arr.length) {
            return aim == 0 ? 0 : -1;
        }
        int ans = Integer.MAX_VALUE;
        for (int zhang = 0; aim - zhang * arr[index] >= 0; zhang++) {
            int p = process2(arr, aim - zhang * arr[index], index + 1);
            if (p != -1 && p != Integer.MAX_VALUE) {
                ans = Math.min(zhang + p, ans);
            }
        }
        return ans;
    }

    /**
     * dp方法
     */

    public static int dp1(int[] arr, int aim) {
        if (arr == null || arr.length == 0 || aim <= 0) {
            return 0;
        }
        int N = arr.length;
        int[][] dp = new int[N + 1][aim + 1];
        dp[N][0] = 0;
        for (int index = 1; index <= aim; index++) {
            dp[N][index] = Integer.MAX_VALUE;
        }
        for (int index = N - 1; index >= 0; index--) {
            for (int aimIndex = 0; aimIndex <= aim; aimIndex++) {
                int coins = Integer.MAX_VALUE;
                //当前位置的货币拿几张。
                for (int zhang = 0; aimIndex - zhang * arr[index] >= 0; zhang++) {
                    int p = dp[index + 1][aimIndex - zhang * arr[index]];
                    //只有在可以搞定的情况下才可以计算最少货币的张数。
                    if (p != Integer.MAX_VALUE) {
                        coins = Math.min(coins, zhang + p);
                    }
                }
                dp[index][aimIndex] = coins;
            }
        }

        return dp[0][aim];
    }

    /**
     * dp方法进一步优化
     */

    public static int dp2(int[] arr, int aim) {
        if (arr == null || arr.length == 0 || aim <= 0) {
            return 0;
        }
        int N = arr.length;
        int[][] dp = new int[N + 1][aim + 1];
        dp[N][0] = 0;
        for (int index = 1; index <= aim; index++) {
            dp[N][index] = Integer.MAX_VALUE;
        }
        for (int index = N - 1; index >= 0; index--) {
            for (int aimIndex = 0; aimIndex <= aim; aimIndex++) {
                dp[index][aimIndex] = dp[index + 1][aimIndex];
                //现有这个格子，并且这个格子的值是有效的
                if (aimIndex - arr[index] >= 0 && dp[index][aimIndex - arr[index]] != Integer.MAX_VALUE) {
                    dp[index][aimIndex] = Math.min(dp[index][aimIndex - arr[index]] + 1, dp[index][aimIndex]);
                }
            }
        }
        return dp[0][aim];
    }


    // 为了测试
    public static int[] randomArray(int maxLen, int maxValue) {
        int N = (int) (Math.random() * maxLen);
        int[] arr = new int[N];
        boolean[] has = new boolean[maxValue + 1];
        for (int i = 0; i < N; i++) {
            do {
                arr[i] = (int) (Math.random() * maxValue) + 1;
            } while (has[arr[i]]);
            has[arr[i]] = true;
        }
        return arr;
    }

    // 为了测试
    public static void printArray(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    // 为了测试
    public static void main(String[] args) {
        int maxLen = 20;
        int maxValue = 30;
        int testTime = 300000;
        System.out.println("功能测试开始");
        for (int i = 0; i < testTime; i++) {
            int N = (int) (Math.random() * maxLen);
            int[] arr = randomArray(N, maxValue);
            int aim = (int) (Math.random() * maxValue);
            int ans1 = minCoins(arr, aim);
            int ans2 = dp1(arr, aim);
            int ans3 = dp2(arr, aim);
            if (ans1 != ans2 || ans3 != ans1) {
                System.out.println("Oops!");
                printArray(arr);
                System.out.println(aim);
                System.out.println(ans1);
                System.out.println(ans2);
                System.out.println(ans3);
                break;
            }
        }
        System.out.println("功能测试结束");
    }
}
