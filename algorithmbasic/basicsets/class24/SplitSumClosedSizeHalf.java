package algorithmbasic.basicsets.class24;

/**
 * 给定一个正数数组arr，请把arr中所有的数分成两个集合
 * 如果arr长度为偶数，两个集合包含数的个数要一样多
 * 如果arr长度为奇数，两个集合包含数的个数必须只差一个
 * 请尽量让两个集合的累加和接近
 * 返回最接近的情况下，较小集合的累加和
 *
 * 字节笔试
 */

public class SplitSumClosedSizeHalf {
    public static int right(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        //计算arr数组的累加和
        int sum = 0;
        for (int nub : arr) {
            sum += nub;
        }
        //偶数长度
        if (arr.length % 2 == 0) {
            return process(arr, 0, arr.length / 2, sum / 2);
        } else { //奇数长度
            //注意此处应该取最大值。
            return Math.max(process(arr, 0, arr.length / 2, sum / 2), process(arr, 0, arr.length / 2 + 1, sum / 2));
        }

    }

    //从index位置开始向后遍历，找到picks个数字组成的最接近rest但不超过rest的累加和。
    public static int process(int[] arr, int index, int picks, int rest) {
        if (index == arr.length) {
            //如果返回-1的话，这种情况本身就是错误的。
            return picks == 0 ? 0 : -1;
        }
        //还没走到头
        if (picks == 0) {
            return 0;
        }
        //既没走到头，picks又不等于0
        //要当前位置的数。
        int p1 = -1;
        int next = -1;
        next = process(arr, index + 1, picks - 1, rest - arr[index]);

        if (next != -1 && arr[index] <= rest) {
            p1 = arr[index] + next;
        }
        //不要当前位置的数
        int p2 = process(arr, index + 1, picks, rest);

        return Math.max(p1, p2);
    }

    public static int dp(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        //计算arr数组的累加和
        int sum = 0;
        for (int nub : arr) {
            sum += nub;
        }
        //创建一个dp表
        int N = arr.length;
        int M = (N + 1) / 2;//向上取证办法，8 -> 4   9 -> 5
        sum /= 2;
        int[][][] dp = new int[N + 1][M + 1][sum + 1];
        //初始化所有的位置都是-1。
        for (int idnex1 = 0; idnex1 <= N; idnex1++) {
            for (int picks1 = 0; picks1 <= M; picks1++) {
                for (int rest1 = 0; rest1 <= sum; rest1++) {
                    dp[idnex1][picks1][rest1] = -1;
                }
            }
        }
        //初始化base case。
        for (int index1 = 0; index1 <= N; index1++) {
            for (int rest1 = 0; rest1 <= sum; rest1++) {
                dp[index1][0][rest1] = 0;
            }
        }
        //计算剩余位置。
        for (int index1 = N - 1; index1 >= 0; index1--) {
            for (int picks1 = 1; picks1 <= M; picks1++) {
                for (int rest1 = 0; rest1 <= sum; rest1++) {
                    int p1 = -1;
                    int next = -1;
                    if(rest1 - arr[index1] >= 0) {
                        next = dp[index1 + 1][picks1 - 1][rest1 - arr[index1]];
                    }
                    if (next != -1 && arr[index1] <= rest1) {
                        p1 = arr[index1] + next;
                    }
                    //不要当前位置的数
                    int p2 = dp[index1 + 1][picks1][rest1];
                    dp[index1][picks1][rest1] = Math.max(p1, p2);
                }
            }
        }
        //偶数长度
        if (arr.length % 2 == 0) {
            return dp[0][arr.length / 2][sum];
        } else { //奇数长度
            //注意此处应该取最大值。
            return Math.max(dp[0][arr.length / 2][sum], dp[0][arr.length / 2 + 1][sum]);
        }
    }



    // for test
    public static int[] randomArray(int len, int value) {
        int[] arr = new int[len];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) (Math.random() * value);
        }
        return arr;
    }

    // for test
    public static void printArray(int[] arr) {
        for (int num : arr) {
            System.out.print(num + " ");
        }
        System.out.println();
    }

    // for test
    public static void main(String[] args) {
        int maxLen = 10;
        int maxValue = 50;
        int testTime = 10000;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            int len = (int) (Math.random() * maxLen);
            int[] arr = randomArray(len, maxValue);
            int ans1 = right(arr);
            int ans2 = dp(arr);
            //int ans3 = dp2(arr);
            if (ans1 != ans2) {
                printArray(arr);
                System.out.println(ans1);
                System.out.println(ans2);
                //System.out.println(ans3);
                System.out.println("Oops!");
                break;
            }
        }
        System.out.println("测试结束");
    }
}