package algorithmbasic.basicsets.class27;


public class FibonacciProblem {
    /**
     * 矩阵快速幂求解斐波那契数列
     */

    /**
     * 思路：f(n) = f(n-1) + f(n-2)
     *      [[f(n)],[f(n-1)]] = [[1,1],[1,0]]^(n-1) * [[f(1)],[f(0)]] ---第一个式子
     *      同理：[[f(n-1)],[f(n-2)]] = [[1,1],[1,0]]^(n-2) * [[f(1)],[f(0)]] ---第二个式子
     *      将第二个式子求出后，即可得到f(n-1).f(n-2)  ==>  f(n) = f(n-1) + f(n-2)
     */


    /**
     * 复杂度就集中在计算[[1,1],[1,0]]^(n-2)上，如果是常规计算的话，时间复杂度是O(N)，如果采用二分的方法计算的话时间复杂度是logN
     */
    public static int f(int n) {
        if (n < 1) {
            return 0;
        }
        if (n == 1 || n == 2) {
            return 1;
        }
        int[][] base = {{1, 1}, {1, 0}};
        int[][] res = matrixMultip(base, n - 2);
        //return res[0][0] + res[0][1] + res[1][0] + res[1][1];
        return res[0][0] + res[1][0];
    }


    /**
     * 思路：利用二分的手段进行优化，LOGN， p --> 1(64) 0(32) 0(16) 1(8) 0(4) 1(2) 1(1)
     */
    private static int[][] matrixMultip(int[][] base, int p) {
        //创建一个单位矩阵
        int[][] res = new int[base.length][base[0].length];
        for (int i = 0; i < res.length; i++) {
            res[i][i] = 1;
        }
        //res是单位矩阵
        //t ==> 存放临时的结果
        int[][] t = base;
        while(p != 0) { // ----------------------注意
            if((p & 1) != 0) {
                res = product(res, t);
            }
            p >>= 1;
            t = product(t, t);
        }
        return res;
    }

    /**
     * 矩阵相乘
     */
    private static int[][] product(int[][] res, int[][] t) { // ---------------------妙
        int n = res.length;
        int m = t[0].length;
        int k = res[0].length;
        int[][] ans = new int[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                for (int c = 0; c < k; c++) {
                    ans[i][j] += res[i][c] * t[c][j];
                }
            }
        }
        return ans;
    }


    public static void main(String[] args) {
        int result = f(7);
        System.out.println(result);
    }
}

/**
 * red river college : machine learning and data science -- 20wRMB(2years)
 * full stack web development -- 16wRMB(2years)
 * information security -- 20wRMB(2years)
 * centennial college :cybersecurity -- 10wRMB(1year)
 */
