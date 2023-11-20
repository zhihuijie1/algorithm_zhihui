package algorithmbasic.basicsets.class29;

import java.util.Arrays;

public class Manacher {
    /**
     * manacher算法是干嘛的：给我一个字符串返回最大回文子串的长度
     *
     * /**
     * 思路：我想采用由中间向两边阔的形式来判断当前位置回文字串的长度。但是奇数回文字串采取这样的方式是可以的，但是偶数长度的时候就不可以了。
     * (1,2,3,2,1) right (1,2,2,1) error
     * 为了使我这个想法对偶数也适用，就采取了对当前的字串进行一个预处理。1 2 3 2 1 -> # 1 # 2 # 3 # 2 # 1 #
     * 1 2 2 1 -> # 1 # 2 # 2 # 1 #
     * 为什么采用这样的预处理办法：这样即使是偶数长度也可以采用两边阔的方法进行回文长度的判断。第一个：最大回文半径由3 -> 6  第二个：回文长度由2->5
     * 对预处理后的半径进行模2就得到正确的答案。
     *
     * 引入R 与 C。  R -> 最大右边界， C -> 最大右边界所对应的回文中心的下标。
     * 为什么引入最大右边界 -> 后面就知道她的用处了。
     *
     * 假设我现在来到了i位置，判断一下 i 与 R 的位置关系
     * -> R < I ：采用暴力的方法计算当前位置的最大回文字串半径(由中间向两边阔，一个一个的判断)
     * -> R >=I : i'(i' i相对)的回文半径在L(RL相对)内：此时i位置的半径不用计算直接等于i'的回文半径  pArr(i')
     *            i'的回文半径在L外：此时i位置的半径不用计算直接等于R - I
     *            i'的回文半径正好在L上: S 1 2 3 2 1 X 4 X 1 2 3 2 1 X
     *                                  L   I'      C       I   R
     *                                  S != X(c左侧)
     *                                  S != X(c右侧)
     *                                  ==> 但是不能推出 X != X ,相反X(c左侧) == X(c右侧)
     *                                  从R的下一个位置开始一一对比判断
     */
    public static int manacher(String s) {
        if(s == null || s.length() == 0) {
            return 0;
        }
        //对字符串进行一个预处理
        char[] str = manacherString(s);
        //存储每一个位置的最大回文字串的半径
        int[] pArr = new int[str.length];
        int max = Integer.MIN_VALUE;
        int R = -1;//在正确位置的下一个
        int C = -1;
        for (int i = 0; i < str.length; i++) {
            // i位置扩出来的答案，i位置扩的区域，至少是多大。
            //( i - i' ) / 2 + i' == c   ==>  i' = 2 * c - i
            pArr[i] = i < R ? Math.min(pArr[2 * C - i], R - i) : 1; //------------注意：这个地方取最小值，因为parr[i'] > R-L的时候应该取R-L而不是更大的parr[i']

            //对i'的回文半径正好在L上  以及  R < I 的情况进行暴力分析能否继续向外阔
            while((i + pArr[i] < str.length) && (i - pArr[i] > -1) && (str[i + pArr[i]] == str[i - pArr[i]])) {
                pArr[i]++;
            }
            //该阔的都阔到头了
            if(i + pArr[i] > R) {
                R = i + pArr[i];
                C = i;
            }
            max = Math.max(max, pArr[i]);
        }
        return max - 1;
    }

    private static char[] manacherString(String s) {
        char[] a = s.toCharArray();
        char[] b = new char[a.length * 2 + 1];
        for (int i = 0; i < b.length; i++) {
            if(i % 2 == 0) {
                b[i] = '#';
            } else {
                b[i] = a[i / 2];
            }
        }
        return b;
    }

    // ---------------------- for test -------------------------

    // for test
    public static int right(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        char[] str = manacherString(s);
        int max = 0;
        for (int i = 0; i < str.length; i++) {
            int L = i - 1;
            int R = i + 1;
            while (L >= 0 && R < str.length && str[L] == str[R]) {
                L--;
                R++;
            }
            max = Math.max(max, R - L - 1);
        }
        return max / 2;
    }

    // for test
    public static String getRandomString(int possibilities, int size) {
        char[] ans = new char[(int) (Math.random() * size) + 1];
        for (int i = 0; i < ans.length; i++) {
            ans[i] = (char) ((int) (Math.random() * possibilities) + 'a');
        }
        return String.valueOf(ans);
    }

    public static void main(String[] args) {
        int possibilities = 5;
        int strSize = 10;
        int testTimes = 5000000;
        System.out.println("test begin");
        for (int i = 0; i < testTimes; i++) {
            String str = getRandomString(possibilities, strSize);
            int a = manacher(str);
            int b = right(str);
            if (a != b) {
                System.out.println("str" + str);
                System.out.println(a);
                System.out.println(b);
                break;
            }
        }
        System.out.println("test finish");
    }
}
