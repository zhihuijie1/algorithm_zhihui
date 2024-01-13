package algorithmbasic.basicsets.class28;

public class KMP {
    public static int getIndexOf(String s1, String s2) {
        if (s1 == null || s2 == null || s1.length() < 1 || s2.length() < 1 || s2.length() > s1.length()) {
            return -1;
        }
        char[] str1 = s1.toCharArray();
        char[] str2 = s2.toCharArray();
        int[] next = makeNextArray(str2);
        int x = 0;
        int y = 0;
        while (x < str1.length && y < str2.length) {
            /**
             * 跳出循环分三种情况
             * 1：当 x == str1.length && y != str2.length ==> 没有在str1中找到str2.
             * 2：当 x == str1.length && y == str2.length ==> 在最后的时候正好找到了。
             * 3：当 x != str1.length && y == str2.length ==> 在前面就找到了.
             */
            if (str1[x] == str2[y]) {
                x++;
                y++;
            } else if (next[y] == -1) { // y == 0 ， 说明x所在的位置没有一个跟他匹配成功的，那就x++,继续向下找吧。
                x++;
            } else {
                y = next[y]; // 直接来到next[y]位置与x位置进行比较。
            }
        }
        return y == str2.length ? x - y : -1;
    }

    private static int[] makeNextArray(char[] str2) {
        if (str2.length < 2) {
            return new int[]{-1};
        }
        int[] next = new int[str2.length];
        next[0] = -1;
        next[1] = 0;
        int i = 2;
        int cn = 0;
        while (i < str2.length) {
            if (str2[cn] == str2[i - 1]) {
                next[i++] = ++cn;
            } else if (cn > 0) { // 说明i-1位置是有最大相同字串的
                cn = next[cn];
            } else { // cn退到最开始的位置都不相等。说明当前i位置压根没有最大相同字串。
                next[i++] = 0;
            }
        }
        return next;
    }
}
