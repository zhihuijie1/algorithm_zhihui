package algorithmbasic.basicsets.class23;

/**
 * 给定一个正数n，求n的裂开方法数，
 * 规定：后面的数不能比前面的数小
 * 比如4的裂开方法有：
 * 1+1+1+1、1+1+2、1+3、2+2、4
 * 5种，所以返回5
 */
public class SplitNumber {
    public static int ways(int n) {
        if (n <= 0) {
            return 0;
        }
        return process(1, n);
    }

    //pre:前面的数是几
    //rest:待裂开的数是几
    //返回可以裂开的方法总数
    //pre = 3 rest = 6
    //               3  3 ~
    //               4  2
    //               5  1
    //               6  0 ~
    public static int process(int pre, int rest) {
        if (rest == 0) {
            return 1;
        }
        if (rest < pre) {
            return 0;
        }
        if (rest == pre) {
            return 1;
        }
        //rest > pre
        int ways = 0;
        for (int i = pre; i <= rest; i++) {
            int result = process(i, rest - i);
            ways += result;
        }
        return ways;
    }


    //只有两个变量 pre 与 rest 变化的范围是：[0 , n]
    public static int dp(int n) {
        if (n <= 0) {
            return 0;
        }
        int[][] dp = new int[n+1][n+1];
        //令行为pre列为rest
        for(int pre = 0; pre <= n; pre++) {
            //填好第一个base case
            dp[pre][0] = 1;
            //填好第三个base case
            dp[pre][pre] = 1;
        }
        //填剩余位置
        //通过画图分析得出位置依赖的关系，应该由下往上填表
        for (int pre = n - 1; pre >= 0; pre--) {
            for (int rest = pre + 1; rest <= n; rest++) {
                //每一个位置的依赖关系如下
                int ways = 0;
                for (int i = pre; i <= rest; i++) {
                    int result = dp[i][rest - i];
                    ways += result;
                }
                dp[pre][rest] =  ways;
            }
        }
        return dp[1][n];
    }

    public static int dp2(int n) {
        if (n <= 0) {
            return 0;
        }
        int[][] dp = new int[n+1][n+1];
        //令行为pre列为rest
        for(int pre = 0; pre <= n; pre++) {
            //填好第一个base case
            dp[pre][0] = 1;
            //填好第三个base case
            dp[pre][pre] = 1;
        }
        //填剩余位置
        //通过画图分析得出位置依赖的关系，应该由下往上填表
        for (int pre = n - 1; pre >= 0; pre--) {
            for (int rest = pre + 1; rest <= n; rest++) {
                //每一个位置的依赖关系如下
                dp[pre][rest] = dp[pre][rest - pre];
                dp[pre][rest] += dp[pre+1][rest];
            }
        }
        return dp[1][n];
    }

    public static void main(String[] args) {
        System.out.println(ways(39));
        System.out.println(dp(39));
        System.out.println(dp2(39));
    }
}