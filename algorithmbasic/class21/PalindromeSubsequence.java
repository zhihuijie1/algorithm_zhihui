package algorithmbasic.class21;

// 测试链接：https://leetcode.com/problems/longest-palindromic-subsequence/

/**
 * 给定一个字符串str，返回这个字符串的最长回文子序列长度
 * 比如 ： str = “a12b3c43def2ghi1kpm”
 * 最长回文子序列是“1234321”或者“123c321”，返回长度7
 */
public class PalindromeSubsequence {
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

    public static void main(String[] args) {
        String str = "a12b3c43def2ghi1kpm";
        System.out.println(lpsl1(str));
    }

}
