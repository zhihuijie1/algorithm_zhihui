package algorithmbasic.basicsets.class22;

/**
 * arr是货币数组，其中的值都是正数。再给定一个正数aim。
 * 每个值都认为是一张货币，
 * 即便是值相同的货币也认为每一张都是不同的，
 * 返回组成aim的方法数
 * 例如：arr = {1,1,1}，aim = 2
 * 第0个和第1个能组成2，第1个和第2个能组成2，第0个和第2个能组成2
 * 一共就3种方法，所以返回3
 */
public class CoinsWayEveryPaperDifferent {
    /**
     * 尝试 -- 范围模型 -- 错误
     */
    public static int coinWays(int[] arr, int aim) {
        return process(arr, aim, 0, arr.length - 1);
    }

    //返回满足条件的方法总数。
    public static int process(int[] arr, int aim, int start, int tail) {

        if (start == tail) {
            return arr[start] == aim ? 1 : 0;
        }

        if (arr[start] > aim) {
            return process(arr, aim, start + 1, tail);
        }

        if (arr[tail] > aim) {
            return process(arr, aim, start, tail - 1);
        }

        // arr[start] < aim | arr[tail] < aim | start < tail
        if (arr[start] + arr[tail] == aim) {
            return 1 + process(arr, aim, start + 1, tail - 1);
        }

        //废了
        return process(arr, aim, start, tail);

    }

    /**
     * 从左往右模型
     */
    public static int coinWays2(int[] arr, int aim) {
        return process2(arr, aim, 0);
    }

    //返回满足条件的方法总数。
    public static int process2(int[] arr, int aim, int index) {
        if (aim < 0) {
            return 0;
        }

        if (index == arr.length) {
            return aim == 0 ? 1 : 0; //走到头了，目标还是0，满足条件的方法数就是1，因为直接就满足。
        }

        if (arr[index] > aim) {
            return process2(arr, aim, index + 1);
        }

        //index位置 要
        int p1 = process2(arr, aim - arr[index], index + 1);

        //index位置 不要
        int p2 = process2(arr, aim, index + 1);

        return p1 + p2;
    }

    /**
     * dp
     */
    public static int coinWays3(int[] arr, int aim) {
        if (aim < 0) {
            return 0;
        }
        int C = aim;
        int L = arr.length;
        int[][] dp = new int[C + 1][L + 1];

        dp[0][L] = 1;
        for (int i = L - 1; i >= 0; i--) {//列
            for (int j = 0; j <= C; j++) {//行
                dp[j][i] = dp[j][i + 1] + (j - arr[i] < 0 ? 0 : dp[j - arr[i]][i + 1]);
            }
        }
        return dp[aim][0];
    }


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
        int maxLen = 20;
        int maxValue = 30;
        int testTime = 1000000;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            int[] arr = randomArray(maxLen, maxValue);
            int aim = (int) (Math.random() * maxValue);
            int ans1 = coinWays3(arr, aim);
            //int ans2 = dp(arr, aim);
            /** if (ans1 != ans2) {
                System.out.println("Oops!");
                printArray(arr);
                System.out.println(aim);
                System.out.println(ans1);
                System.out.println(ans2);
                break;
            }
            * */
        }
        System.out.println("测试结束");
    }
}
