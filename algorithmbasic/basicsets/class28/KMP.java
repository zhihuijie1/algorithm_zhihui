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

        while (x < str1.length) {
            if (str1[x] == str2[y]) {
                x++;
                y++;
            } else if (next[y] == -1) {
                x++;
            } else {
                y = next[y];
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
            } else {

            }
        }
    }
}
