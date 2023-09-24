package algorithmbasic.basicsets.class21;

// 测试链接：https://leetcode.com/problems/longest-palindromic-subsequence/

/**
 * 给定一个字符串str，返回这个字符串的最长回文子序列长度
 * 比如 ： str = “a12b3c43def2ghi1kpm”
 * 最长回文子序列是“1234321”或者“123c321”，返回长度7
 */
public class PalindromeSubsequence {

    /**
     * 暴力递归
     */
    public static int lpsl1(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        char[] str = s.toCharArray();
        return process(str, 0, str.length - 1);
    }

    //str[0 .... tail]
    //返回最长回文子序列的长度
    public static int process(char[] str, int start, int tail) {
        if (start == tail) {
            return 1;
        }
        if (start == tail - 1) {
            //这个1很机灵
            return str[start] == str[tail] ? 2 : 1;
        }

        //s 是  t 不是
        int res1 = process(str, start, tail - 1);

        //s 不是  t 是
        int res2 = process(str, start + 1, tail);

        //s 不是  t 不是
        int res3 = process(str, start + 1, tail - 1);

        //s 是  t 是
        int res4 = str[start] == str[tail] ? 2 + process(str, start + 1, tail - 1) : 0;

        return Math.max(Math.max(res1, res2), Math.max(res3, res4));
    }

    /**
     * 迭代版本
     */
    public static int lpsl2(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        int N = s.length();
        char[] str = s.toCharArray();
        int[][] dp = new int[N][N];
        for (int i = 0; i < N; i++) {
            dp[i][i] = 1;
        }
        for (int l = 1; l < N; l++) {
            dp[l - 1][l] = str[l - 1] == str[l] ? 2 : 1;
            for (int c = l - 2; c >= 0; c--) {
                int res1 = dp[c][l - 1];
                int res2 = dp[c + 1][l];
                int res3 = dp[c + 1][l - 1];
                int res4 = str[c] == str[l] ? 2 + dp[c + 1][l - 1] : 0;
                dp[c][l] = Math.max(Math.max(res1, res2), Math.max(res3, res4));
            }
        }
        return dp[0][N - 1];
    }

    //进一步优化
    public static int lpsl3(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        int N = s.length();
        char[] str = s.toCharArray();
        int[][] dp = new int[N][N];
        for (int i = 0; i < N; i++) {
            dp[i][i] = 1;
        }
        for (int l = 1; l < N; l++) {
            dp[l - 1][l] = str[l - 1] == str[l] ? 2 : 1;
            for (int c = l - 2; c >= 0; c--) {
                int res1 = dp[c][l - 1];//left
                int res2 = dp[c + 1][l];//down
                //因为左下一定小于等于右或者下，所以可以直接的舍去。
                //int res3 = dp[c + 1][l - 1];//leftdown
                int res4 = str[c] == str[l] ? 2 + dp[c + 1][l - 1] : 0;
                dp[c][l] = Math.max(Math.max(res1, res2), res4);
            }
        }
        return dp[0][N - 1];
    }
}