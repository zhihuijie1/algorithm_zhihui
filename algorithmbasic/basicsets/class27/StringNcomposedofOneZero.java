package algorithmbasic.basicsets.class27;

/**
 * 给定一个数N，想象只由0和1两种字符，组成的所有长度为N的字符串
 * 如果某个字符串,任何0字符的左边都有1紧挨着,认为这个字符串达标
 * 返回有多少达标的字符串
 */

/**
 * 思路：画图
 */
public class StringNcomposedofOneZero {
    public static int f(int N) {
        int[][] base = {
                {1, 1},
                {1, 0}};

        int[][] ans = matrixMultip(base, N - 2);
        return ans[0][0] + ans[1][0];
    }

    private static int[][] matrixMultip(int[][] a, int p) {

    }
}
