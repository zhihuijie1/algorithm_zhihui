package algorithmbasic.basicsets.class22;


import java.util.HashMap;
import java.util.Map;

/**
 * arr是货币数组，其中的值都是正数。再给定一个正数aim。
 * 每个值都认为是一张货币，
 * 认为值相同的货币没有任何不同，
 * 返回组成aim的方法数
 * 例如：arr = {1,2,1,1,2,1,2}，aim = 4
 * 方法：1+1+1+1、1+1+2、2+2
 * 一共就3种方法，所以返回3
 */
public class CoinsWaySameValueSamePapper {
    public static class Info {
        public int[] coins;
        public int[] zhangs;

        public Info(int[] coins, int[] zhangs) {
            this.coins = coins;
            this.zhangs = zhangs;
        }
    }

    //建立Info结构
    public static Info createInfo(int[] arr) {
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < arr.length; i++) {
            if (!map.containsKey(arr[i])) {
                map.put(arr[i], 1);
            } else {
                map.put(arr[i], map.get(arr[i]) + 1);
            }
        }
        //词频统计完成
        int[] coins = new int[map.size()];
        int[] zhangs = new int[map.size()];
        int index = 0;
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            coins[index] = entry.getKey();
            zhangs[index] = entry.getValue();
            index++;
        }
        return new Info(coins, zhangs);
    }

    public static int coinsWay(int[] arr, int aim) {
        if (arr == null || arr.length == 0 || aim < 0) {
            return 0;
        }
        return process(createInfo(arr), aim, 0);
    }

    //从左到右模型
    //返回满足条件的方法总数。
    public static int process(Info info, int aim, int index) {
        if (index == info.coins.length) {
            return aim == 0 ? 1 : 0;
        }
        int[] coins = info.coins;
        int[] zhangs = info.zhangs;
        int ways = 0;
        //当前位置到底拿几张。
        for (int zhang = 0; zhang <= zhangs[index] && aim - coins[index] * zhang >= 0; zhang++) {
            ways += process(info, aim - zhang * coins[index], index + 1);
        }
        return ways;
    }

    //dp模型
    public static int dp(int[] arr, int aim) {
        if (arr == null || arr.length == 0 || aim < 0) {
            return 0;
        }
        Info info = createInfo(arr);
        int N = info.coins.length;
        int[] coins = info.coins;
        int[] zhangs = info.zhangs;
        int[][] dp = new int[aim + 1][N + 1];
        dp[0][N] = 1;

        //当前位置到底拿几张。
        for (int L = N - 1; L >= 0; L--) {
            for (int H = 0; H <= aim; H++) {
                int ways = 0;
                for (int zhang = 0; H - coins[L] * zhang >= 0 && zhang <= zhangs[L]; zhang++) {
                    ways += dp[H - zhang * coins[L]][L + 1];
                }
                dp[H][L] = ways;
            }
        }
        return dp[aim][0];
    }

    //dp方法优化
    //我不想枚举，想用临近位置代替。
    public static int dp2(int[] arr, int aim) {
        if (arr == null || arr.length == 0 || aim < 0) {
            return 0;
        }
        Info info = createInfo(arr);
        int N = info.coins.length;
        int[] coins = info.coins;
        int[] zhangs = info.zhangs;
        int[][] dp = new int[aim + 1][N + 1];
        dp[0][N] = 1;
        //当前位置到底拿几张。
        for (int L = N - 1; L >= 0; L--) {
            for (int H = 0; H <= aim; H++) {
                int key = dp[H][L + 1];
                if (H - coins[L] >= 0) {
                    key += dp[H - coins[L]][L];
                }
                if (H - coins[L] - zhangs[L] * coins[L] >= 0) {
                    key -= dp[H - coins[L] - zhangs[L] * coins[L]][L + 1];
                }
                dp[H][L] = key;
            }
        }
        return dp[aim][0];
    }

    // 为了测试
    public static int[] randomArray(int maxLen, int maxValue) {
        int N = (int) (Math.random() * maxLen);
        int[] arr = new int[N];
        for (int i = 0; i < N; i++) {
            arr[i] = (int) (Math.random() * maxValue) + 1;
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
        int maxLen = 10;
        int maxValue = 20;
        int testTime = 1000000;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            int[] arr = randomArray(maxLen, maxValue);
            int aim = (int) (Math.random() * maxValue);
            int ans1 = coinsWay(arr, aim);
            int ans2 = dp(arr, aim);
            int ans3 = dp2(arr, aim);
            if (ans1 != ans2 || ans1 != ans3) {
                System.out.println("Oops!");
                printArray(arr);
                System.out.println(aim);
                System.out.println(ans1);
                System.out.println(ans2);
                System.out.println(ans3);
                break;
            }
        }
        System.out.println("测试结束");
    }
}
