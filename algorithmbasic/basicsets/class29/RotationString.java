package algorithmbasic.basicsets.class29;

/**
 * 给我两个字符串一个是str1,一个是str2,判断两个字符串是否互为旋转串。
 */
public class RotationString {
    /**
     * 思路：str = str1 + str1 将两个str1拼接在一起组成一个新串str，然后使用kmp算法判断str2是否是str的一个子串。
     */
    public static Boolean isRotation(String str1, String str2) {
        if (str1.length() != str2.length()) {
            return false;
        }
        //将两个str1拼接在一起组成一个新串str ---------------------------将两个str1拼起来然后判断str2是否是str1的旋转串的做法十分巧妙
        String str = str1 + str1;
        //使用kmp算法判断str2是否是str的一个子串
        return kmp(str, str2);
    }

    public static Boolean kmp(String str1, String str2) {
        if (str1 == null || str2 == null || str1.length() < 1 || str2.length() < 1 || str2.length() > str1.length()) {
            return false;
        }
        char[] strA = str1.toCharArray();
        char[] strB = str2.toCharArray();

        int x = 0; //strA的指针
        int y = 0; //strB的指针
        int[] NEXT = makeNextArray(strB);

        while (x < strA.length && y < strB.length) {
            if (strA[x] == strB[y]) {
                x++;
                y++;
            } else if (y != 0) {
                y = NEXT[y];
            } else {
                x++;
            }
        }
        //退出循环的情况：
        //1：x < strA.length && y = strB.length -> 说明在前面就找到了
        //2：x = strA.length && y = strB.length -> 说明在最后一刻找到的
        //3：x = strA.length && y < strB.length -> 没找到
        return y == strB.length ? true : false;
    }

    private static int[] makeNextArray(char[] strB) {
        if (strB.length == 1) {
            return new int[]{-1};
        }
        if (strB.length == 2) {
            return new int[]{-1, 0};
        }
        int[] Next = new int[strB.length];
        Next[0] = -1;
        Next[1] = 0;

        int c = 0;
        for (int i = 2; i < strB.length; i++) {
            if (strB[i - 1] == strB[c]) {
                Next[i] = ++c;
            } else if (c != 0) {
                c = Next[c];
            } else {
                Next[i] = 0;
            }
        }
        return Next;
    }

    public static void main(String[] args) {
        String str1 = "abcdef";
        String str2 = "bacdef";
        Boolean rotation = isRotation(str1, str2);
        System.out.println(rotation);
    }
}
