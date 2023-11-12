package algorithmbasic.basicsets.class27;

/**
 * 矩阵快速幂问题详解：https://hezhaojiang.github.io/post/2020/41da3a83/
 */
/**
 * 矩阵快速幂是一种基础算法，本身与动态规划没有关系，但常用于优化线性递推关系的计算，并且其思路比较固定，本章将矩阵快速幂做基础介绍。
 * 动态规划主要用于解决两类问题，一类是优化问题，求最优解，另一类是组合计数，求方案数。
 * 矩阵快速幂主要用在第二类，即组合计数，求方法数这类问题，可以将时间复杂度从 O (N) 降到 O (logN)。
 */

/**
 * 第一年农场有1只成熟的母牛A，往后的每年：
 * 1）每一只成熟的母牛都会生一只母牛
 * 2）每一只新出生的母牛都在出生的第三年成熟
 * 3）每一只母牛永远不会死
 * 返回N年后牛的数量。
 */
public class cowProduction {
    /**
     *
     * 思路：求N年后牛的数量F(N),F(N) = F(N-1) + F(N-3)，第N年时牛的数量等于前一年牛的数量加上三年前牛的数量（可以生育的牛的数量）。
     * 这就是一个严格的递推公式，可以使用矩阵快速幂优化到 -- LOGN
     */

    /**
     * F(N) = F(N-1) + F(N-3)
     * 这要建立一个三阶矩阵。因为最低的一位到N-3了。
     * 根据矩阵快速幂的固定公式：|Fn......Fn-i| = |Fi...F1|  *  |i * i| ^ (n - i)
     * |Fn,Fn-1,Fn-2| = |F3,F2,F1| * |{a,b,c},{d,e,f},{h,i,g}|^(n - 3)
     * Fn = F3 * A + F2 * D + F1 * H
     */


    /**
     * year cowsNumbers
     * 1    1
     * 2    2
     * 3    3
     * 4    4
     * 5    6
     * 6    9
     */
    public static int c3(int n) {
        if (n < 1) {
            return 0;
        }
        if (n == 1 || n == 2 || n == 3) {
            return n;
        }
        //计算三阶矩阵。
        int[][] base = {
                {1, 1, 0},
                {0, 0, 1},
                {1, 0, 0}};
        //计算三阶矩阵的n-3次方
        int[][] ans = matrixPower(base, n - 3);
        //|Fn,Fn-1,Fn-2| = |F3,F2,F1| * |{a,b,c},{d,e,f},{h,i,g}|^(n - 3)
        //Fn = F3 * A + F2 * D + F1 * H
        return 3 * ans[0][0] + 2 * ans[1][0] + 1 * ans[2][0];
    }

    //计算矩阵的次方，时间复杂度 -- LOGN
    private static int[][] matrixPower(int[][] base, int p) {
        //创建一个单位矩阵
        int[][] res = new int[base.length][base[0].length];
        for (int i = 0; i < base.length; i++) {
            res[i][i] = 1;
        }
        int[][] t = base;
        //采用二进制分解的方法
        while (p != 0) {
            if ((p & 1) != 0) {
                res = product(res, t);
            }
            t = product(t, t);
            p >>= 1;
        }
        return res;
    }

    //矩阵乘法
    private static int[][] product(int[][] a, int[][] b) {
        int m = b[0].length;
        int n = a.length;
        int k = a[0].length;
        int[][] ans = new int[a.length][b[0].length];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                for (int c = 0; c < k; c++) {
                    ans[i][j] += a[i][c] * b[c][j];
                }
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        System.out.println(c3(6));
    }
}
