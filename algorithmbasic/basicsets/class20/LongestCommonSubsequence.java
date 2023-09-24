package algorithmbasic.basicsets.class20;

/**
 * 给定两个字符串str1和str2，
 * 返回这两个字符串的最长公共子序列长度
 * 比如 ： str1 = “a12b3c456d”,str2 = “1ef23ghi4j56k”
 * 最长公共子序列是“123456”，所以返回长度6
 */

/**
 * 样本对应模型 -- 分析样本的尾部
 * abc12hg34t
 * defkl123m
 */

public class LongestCommonSubsequence {
    /***
     * 暴力解法
     */
    public static int longestCommonSubsequence1(String s1, String s2) {
        if (s1 == null || s2 == null || s1.length() == 0 || s2.length() == 0) {
            return 0;
        }
        char[] str1 = s1.toCharArray();
        char[] str2 = s2.toCharArray();
        return process(str1, str2, str1.length - 1, str2.length - 1);
    }

    // str1[0...i]和str2[0...j]，这个范围上最长公共子序列长度是多少？
    // 可能性分类:
    // a) 最长公共子序列，一定不以str1[i]字符结尾、也一定不以str2[j]字符结尾
    // b) 最长公共子序列，可能以str1[i]字符结尾、但是一定不以str2[j]字符结尾
    // c) 最长公共子序列，一定不以str1[i]字符结尾、但是可能以str2[j]字符结尾
    // d) 最长公共子序列，必须以str1[i]字符结尾、也必须以str2[j]字符结尾
    // 注意：a)、b)、c)、d)并不是完全互斥的，他们可能会有重叠的情况
    // 但是可以肯定，答案不会超过这四种可能性的范围
    // 那么我们分别来看一下，这几种可能性怎么调用后续的递归。
    // a) 最长公共子序列，一定不以str1[i]字符结尾、也一定不以str2[j]字符结尾
    //    如果是这种情况，那么有没有str1[i]和str2[j]就根本不重要了，因为这两个字符一定没用啊
    //    所以砍掉这两个字符，最长公共子序列 = str1[0...i-1]与str2[0...j-1]的最长公共子序列长度(后续递归)
    // b) 最长公共子序列，可能以str1[i]字符结尾、但是一定不以str2[j]字符结尾
    //    如果是这种情况，那么我们可以确定str2[j]一定没有用，要砍掉；但是str1[i]可能有用，所以要保留
    //    所以，最长公共子序列 = str1[0...i]与str2[0...j-1]的最长公共子序列长度(后续递归)
    // c) 最长公共子序列，一定不以str1[i]字符结尾、但是可能以str2[j]字符结尾
    //    跟上面分析过程类似，最长公共子序列 = str1[0...i-1]与str2[0...j]的最长公共子序列长度(后续递归)
    // d) 最长公共子序列，必须以str1[i]字符结尾、也必须以str2[j]字符结尾
    //    同时可以看到，可能性d)存在的条件，一定是在str1[i] == str2[j]的情况下，才成立的
    //    所以，最长公共子序列总长度 = str1[0...i-1]与str2[0...j-1]的最长公共子序列长度(后续递归) + 1(共同的结尾)
    // 综上，四种情况已经穷尽了所有可能性。四种情况中取最大即可
    // 其中b)、c)一定参与最大值的比较，
    // 当str1[i] == str2[j]时，a)一定比d)小，所以d)参与
    // 当str1[i] != str2[j]时，d)压根不存在，所以a)参与
    // 但是再次注意了！
    // a)是：str1[0...i-1]与str2[0...j-1]的最长公共子序列长度
    // b)是：str1[0...i]与str2[0...j-1]的最长公共子序列长度
    // c)是：str1[0...i-1]与str2[0...j]的最长公共子序列长度
    // a)中str1的范围 < b)中str1的范围，a)中str2的范围 == b)中str2的范围
    // 所以a)不用求也知道，它比不过b)啊，因为有一个样本的范围比b)小啊！
    // a)中str1的范围 == c)中str1的范围，a)中str2的范围 < c)中str2的范围
    // 所以a)不用求也知道，它比不过c)啊，因为有一个样本的范围比c)小啊！
    // 至此，可以知道，a)就是个垃圾，有它没它，都不影响最大值的决策
    // 所以，当str1[i] == str2[j]时，b)、c)、d)中选出最大值
    // 当str1[i] != str2[j]时，b)、c)中选出最大值
    public static int process(char[] str1, char[] str2, int i, int j) {
        //样本对应模型分析样本的尾部


        if (i == 0 && j == 0) {
            return str1[i] == str2[j] ? 1 : 0;
        }

        if (i == 0) {
            if (str1[0] == str2[j]) {
                return 1;
            } else {
                int ans = process(str1, str2, i, j - 1);
                return ans;
            }
        }

        if (j == 0) {
            if (str1[i] == str2[j]) {
                return 1;
            } else {
                int res = process(str1, str2, i - 1, j);
                return res;
            }
        }

        //1:最长公共子序列可能以str1[i]结尾，但是一定不以str2[j-1]结尾。
        //所以str2[j-1]可以直接砍掉。str1[i]因为可能是公共子序列的结尾，所以要保留。
        int res1 = process(str1, str2, i, j - 1);

        //2:最长公共子序列可能以str[j]结尾，但是一定不以str1[i]结尾，所以str1[i]可以直接的砍掉。
        int res2 = process(str1, str2, i - 1, j);

        //3：最长公共子序列可能即以str[j]结尾，也以str1[i]结尾,所以str1[i]与str2[j]相等
        // int res3 = process(str1, str2, i, j); -- 这个写法是严重错误的，导致进入死递归。
        int res3 = str1[i] == str2[j] ? 1 + process(str1, str2, i - 1, j - 1) : 0;

        //这个res4有他没他都可以，因为最后我要求的是最大值，res4的范围比前三个都小，最后得出的结果也是最小的，所以单纯考虑最后
        //结果时，有4没4都可以，为了方便理解，保留。
        int res4 = process(str1, str2, i - 1, j - 1);

        return Math.max(Math.max(res1, res2), Math.max(res3, res4));
    }

    /**
     * dp迭代法
     */
    public static int longestCommonSubsequence2(String s1, String s2) {
        if (s1 == null || s2 == null || s1.length() == 0 || s2.length() == 0) {
            return 0;
        }
        char[] str1 = s1.toCharArray();
        char[] str2 = s2.toCharArray();
        int C = str1.length;
        int L = str2.length;
        int[][] dp = new int[C][L];

        dp[0][0] = str1[0] == str2[0] ? 1 : 0;
        for (int j = 1; j < L; j++) {
            dp[0][j] = str1[0] == str2[j] ? 1 : dp[0][j - 1];
        }

        for (int i = 1; i < C; i++) {
            dp[i][0] = str1[i] == str2[0] ? 1 : dp[i - 1][0];
        }

        for (int c = 1; c < C; c++) {
            for (int l = 1; l < L; l++) {
                int p1 = dp[c][l - 1];
                int p2 = dp[c - 1][l];
                int p3 = str1[c] == str2[l] ? 1 + dp[c - 1][l - 1] : 0;
                dp[c][l] = Math.max(p1, Math.max(p2, p3));
            }
        }
        return dp[C - 1][L - 1];
    }
}