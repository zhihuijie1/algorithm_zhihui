package algorithmbasic.basicsets.class24;

/**
 * 给定一个正数数组arr，
 * 请把arr中所有的数分成两个集合，尽量让两个集合的累加和接近
 * 返回最接近的情况下，较小集合的累加和
 */
public class SplitSumClosed {
    public static int right(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        //sum -> 累加和
        int sum = 0;
        for (int nub : arr) {
            sum += nub;
        }
        //因为两个集合是二元相对的所以找到一个就相当于找到另一个了。
        int a = process(arr, 0, sum / 2);
        return a;
    }

    //从index = 0位置出发，找到不超过但是最接近rest的累计和，然后返回该累计和。
    //同样这里要求找到不超过但是最接近rest的累加和。同样是二元相对的，找的这个就对应找到另一个。
    public static int process(int[] arr, int index, int rest) {
        if (index == arr.length) {
            return 0;
        }
        //不要index位置
        int p1 = process(arr, index + 1, rest);
        //要index位置
        int p2 = 0;
        if (arr[index] <= rest) {
            p2 = arr[index] + process(arr, index + 1, rest - arr[index]);
        }
        return Math.max(p1, p2);
    }

    public static int dp(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        //sum -> 累加和
        int sum = 0;
        for (int nub : arr) {
            sum += nub;
        }
        int N = arr.length;
        int rest = sum / 2;
        int[][] dp = new int[N + 1][rest + 1];
        for (int i = 0; i <= rest; i++) {
            dp[N][i] = 0;
        }
        for (int H = N - 1; H >= 0; H--) {
            for (int L = 0; L <= rest; L++) {
                //不要index位置
                int p1 = dp[H + 1][L];
                //要index位置
                int p2 = 0;
                if (arr[H] <= L) {
                    p2 = arr[H] + dp[H + 1][L - arr[H]];
                }
                dp[H][L] = Math.max(p1, p2);
            }
        }
        return dp[0][rest];
    }


    public static int[] randomArray(int len, int value) {
        int[] arr = new int[len];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) (Math.random() * value);
        }
        return arr;
    }

    public static void printArray(int[] arr) {
        for (int num : arr) {
            System.out.print(num + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        int maxLen = 20;
        int maxValue = 50;
        int testTime = 10000;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            int len = (int) (Math.random() * maxLen);
            int[] arr = randomArray(len, maxValue);
            int ans1 = right(arr);
            int ans2 = dp(arr);
            if (ans1 != ans2) {
                printArray(arr);
                System.out.println(ans1);
                System.out.println(ans2);
                System.out.println("Oops!");
                break;
            }
        }
        System.out.println("测试结束");
    }
}
