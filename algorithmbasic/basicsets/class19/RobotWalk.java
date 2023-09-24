package algorithmbasic.basicsets.class19;

/*
假设有排成一行的N个位置记为1~N，N一定大于或等于2
开始时机器人在其中的M位置上(M一定是1~N中的一个)
如果机器人来到1位置，那么下一步只能往右来到2位置；
如果机器人来到N位置，那么下一步只能往左来到N-1位置；
如果机器人来到中间位置，那么下一步可以往左走或者往右走；
规定机器人必须走K步，最终能来到P位置(P也是1~N中的一个)的方法有多少种
给定四个参数 N、M、K、P，返回方法数
 */
public class RobotWalk {

    //N:一共有N个位置记为1-N
    //start：机器人当前所在的位置
    //aim：目标位置
    //k：一共可以走多少步。
    public static int ways1(int N, int start, int aim, int K) {
        if (N < 2 || start < 1 || start > N || aim < 1 || aim > N || K < 1) {
            return -1;
        }
        return process1(start, aim, K, N);
    }

    // process方法的解释：现在在cur位置，目标位置是aim，还剩rest步可走，一共有N个位置（1-N）
    // 机器人从当前的cur位置开始走，走rest步之后，到达目标位置aim的方法数一共是多少。
    private static int process1(int cur, int aim, int rest, int N) {
        //现在还剩0步可走。
        if (rest == 0) {
            return cur == aim ? 1 : 0;
        }
        //如果当前的位置在最左侧，只能往右行走。
        if (cur == 1) {
            return process1(2, aim, rest - 1, N);
        }
        //如果当前的位置在最右侧只能往左走。
        if (cur == N) {
            return process1(N - 1, aim, rest - 1, N);
        }

        //当前的位置不在边缘，在中间，那既可以向左也可以向右走。
        return process1(cur + 1, aim, rest - 1, N) + process1(cur - 1, aim, rest - 1, N);
    }

    //进行优化1
    public static int ways2(int N, int start, int aim, int K) {
        if (N < 2 || start < 1 || start > N || aim < 1 || aim > N || K < 1) {
            return -1;
        }
        //加入一个缓存来存储之前计算过的结果->方便以后遇到一样的情况下直接取结果。
        int[][] dp = new int[N + 1][K + 1];
        //初始化缓存中的数据一上来都是-1.
        for (int i = 0; i <= N; i++) {
            for (int j = 0; j <= K; j++) {
                dp[i][j] = -1;
            }
        }
        return process2(start, K, aim, N, dp);
    }

    //返回的结果是由cur(当前位置),rest(还剩几步可以走)这两个元素决定的。
    //cur 取值范围是:[1,N]
    //rest 取值范围是:[0,K]
    //所以一共有(N * K)种搭配,可以创建一个二维数组来存放结果值。
    private static int process2(int cur, int rest, int aim, int N, int[][] dp) {
        //一进来就判断一下缓存中有无当前情况下的结果。
        //如果缓存中的数据不是-1说明当前情况之前也遇到过，直接返回之前遇到时的处理结果即可。
        if (dp[cur][rest] != -1) {
            return dp[cur][rest];
        }

        //当前情况之前没有遇到过。
        int result = -1;
        //下面的四种情况只会命中一个。
        if (rest == 0) {
            result = cur == aim ? 1 : 0;
        } else if (cur == 1) {
            result = process2(2, rest - 1, aim, N, dp);
        } else if (cur == N) {
            result = process2(N - 1, rest - 1, aim, N, dp);
        } else {
            result = process2(cur - 1, rest - 1, aim, N, dp) + process2(cur + 1, rest - 1, aim, N, dp);
        }

        dp[cur][rest] = result;

        return result;
    }

    public static int ways3(int N, int start, int aim, int K) {
        if (N < 2 || start < 1 || start > N || aim < 1 || aim > N || K < 1) {
            return -1;
        }
        int[][] dp = new int[N + 1][K + 1];
        dp[aim][0] = 1;
        for (int i = 1; i <= K; i++) { //列
            dp[1][i] = dp[2][i - 1];

            for (int j = 2; j <= N - 1; j++) { //行
                dp[j][i] = dp[j - 1][i - 1] + dp[j + 1][i - 1];
            }

            dp[N][i] = dp[N - 1][i - 1];
        }

        return dp[start][K];
    }

    public static void main(String[] args) {
        int a = ways1(7, 3, 5, 4);
        int b = ways2(7, 3, 5, 4);
        int c = ways3(7, 3, 5, 4);
        System.out.println(a);
        System.out.println(b);
        System.out.println(c);
    }
}