package algorithmbasic.basicsets.class27;

/**
 * 给定一个数N，想象只由0和1两种字符，组成的所有长度为N的字符串
 * 如果某个字符串,任何0字符的左边都有1紧挨着,认为这个字符串达标
 * 返回有多少达标的字符串
 * <p>
 * 用1*2的瓷砖，把N*2的区域填满
 * 返回铺瓷砖的方法数
 * <p>
 * 这两道题的方法都是一样的都是与斐波那契递推公式一样的。
 */

/**
 * 用1*2的瓷砖，把N*2的区域填满
 * 返回铺瓷砖的方法数
 */


/**
 * 这两道题的方法都是一样的都是与斐波那契递推公式一样的。
 */

/**
 * 思路：画图
 */
public class StringNcomposedofOneZero {
    public static int f(int N) {
        if (N == 1) {
            return 1;
        }
        if (N == 2) {
            return 1;
        }
        int[][] base = {
                {1, 1},
                {1, 0}};

        int[][] ans = matrixMultip(base, N - 2);
        return ans[0][0] + ans[1][0];
    }

    private static int[][] matrixMultip(int[][] a, int p) {
        //采用二进制分解的方法
        int[][] res = new int[a.length][a[0].length];
        for (int i = 0; i < res.length; i++) {
            res[i][i] = 1;
        }
        int[][] t = a;
        while (p != 0) {
            if ((p & 1) != 0) {
                res = product(res, t);
            }
            t = product(t, t);
            p >>= 1;
        }
        return res;
    }

    private static int[][] product(int[][] a, int[][] b) {
        int[][] ans = new int[a.length][b[0].length];
        int m = a[0].length;
        int n = a.length;
        int k = b[0].length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < k; j++) {
                for (int l = 0; l < m; l++) {
                    ans[i][j] += a[i][l] * b[l][j];
                }
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        System.out.println(f(7));
    }

}
