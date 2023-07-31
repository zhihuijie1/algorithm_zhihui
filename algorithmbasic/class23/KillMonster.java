package algorithmbasic.class23;

import org.jcp.xml.dsig.internal.dom.DOMUtils;

/**
 * 给定3个参数，N，M，K
 * 怪兽有N滴血，等着英雄来砍自己
 * 英雄每一次打击，都会让怪兽流失[0~M]的血量
 * 到底流失多少？每一次在[0~M]上等概率的获得一个值
 * 求K次打击之后，英雄把怪兽砍死的概率
 */
public class KillMonster {
    //暴力递归方法
    public static double right(int N, int M, int K) {
        if (N < 1 || M <= 0 || K <= 0) {
            return 0;
        }
        double all = Math.pow(M + 1, K);
        return process(N, M, K) / all;
    }

    //N：剩余血量
    //M：伤害的范围
    //K：剩余待砍的次数
    //返回把怪兽打死的总次数。
    public static double process(int N, int M, int K) {
        if (K == 0) {
            return N <= 0 ? 1 : 0;
        }
        if (N <= 0) {
            return Math.pow(M + 1, K);//剪枝注意点: 这是一个全排列，所以每一种结果都要考虑。
        }
        int ways = 0;
        for (int cut = 0; cut <= M; cut++) {
            ways += process(N - cut, M, K - 1);
        }
        return ways;
    }

    //dp方法
    //N：剩余血量
    //M：伤害的范围
    //K：剩余待砍的次数
    //返回把怪兽打死的总次数。
    public static double dp(int N, int M, int K) {
        if (N < 1 || M <= 0 || K <= 0) {
            return 0;
        }
        double all = Math.pow(M + 1, K);
        long[][] dp = new long[K + 1][N + 1];
        dp[0][0] = 1;
        for (int i = 1; i <= K; i++) {
            dp[i][0] = (long) Math.pow(M + 1, i);
        }
        for (int k = 1; k <= K; k++) {
            for (int n = 1; n <= N; n++) {
                int ways = 0;
                for (int cut = 0; cut <= M; cut++) {
                    if (n - cut > 0) {
                        ways += dp[k - 1][n - cut];
                    } else {
                        //当前的n <= 0,直接返回结果。k-1指向下一层。
                        ways += Math.pow(M + 1, k - 1); //注意；k-1
                    }
                }
                dp[k][n] = ways;
            }
        }
        return dp[K][N] / all;
    }

    //dp方法的优化
    public static double dp2(int N, int M, int K) {
        if (N < 1 || M <= 0 || K <= 0) {
            return 0;
        }
        double all = Math.pow(M + 1, K);
        long dp[][] = new long[K + 1][N + 1];
        dp[0][0] = 1;
        for (int k = 1; k <= K; k++) {
            dp[k][0] = (long) Math.pow(M + 1, k);
            for (int n = 1; n <= N; n++) {



                int ways = 0;
                for (int cut = 0; cut <= M; cut++) {
                    ways += process(N - cut, M, K - 1);
                }
                return ways;




            }
        }

    }













    public static void main(String[] args) {
        int NMax = 10;
        int MMax = 10;
        int KMax = 10;
        int testTime = 200;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            int N = (int) (Math.random() * NMax);
            int M = (int) (Math.random() * MMax);
            int K = (int) (Math.random() * KMax);
            double ans1 = right(N, M, K);
            double ans2 = dp(N, M, K);
            double ans3 = dp1(N, M, K);
            if (ans1 != ans2 || ans3 != ans1) {
                System.out.println("Oops!");
                System.out.println(ans1);
                System.out.println(ans2);
                System.out.println(ans3);
                break;
            }
        }
        System.out.println("测试结束");
    }
}