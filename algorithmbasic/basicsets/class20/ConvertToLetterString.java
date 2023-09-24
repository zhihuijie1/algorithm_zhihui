package algorithmbasic.basicsets.class20;

/**
 * 规定1和A对应、2和B对应、3和C对应...26和Z对应
 * 那么一个数字字符串比如"111”就可以转化为:
 * "AAA"、"KA"和"AK"
 * 给定一个只有数字字符组成的字符串str，返回有多少种转化结果
 */
public class ConvertToLetterString {

    /**
     * 暴力方法
     */
    public static int number(String str) {
        if (str == null || str.length() < 1) {
            return 0;
        }

        char[] chars = str.toCharArray();
        return process(chars, 0);
    }

    //返回所有可能出现的方法数目。
    public static int process(char[] chars, int index) {

        //之所以返回1是因为走到最后这条路是有效的，所以返回1说明这条路有效，归的时候就会将这条路算入。
        if (index >= chars.length) {
            return 1;
        }

        //这个截止条件十分的重要。
        //如果当前的index独立的面对0字符说明当前的决定是错误的，所以返回0，因为0加任何的数都是0相当于这条路废了。
        if (chars[index] == '0') {
            return 0;
        }

        //只要当前位置
        int nub1 = process(chars, index + 1);

        //要当前位置与后一位置
        if (index + 1 != chars.length) {
            int nub2 = process(chars, index + 2);
            String ans = String.valueOf(chars[index]) + String.valueOf(chars[index + 1]);
            if (Integer.parseInt(ans) <= 26) {
                return nub2 + nub1;
            }
        }
        return nub1;
    }

    /**
     * 迭代版本
     */
    public static int number2(String str) {
        int[] arr = new int[str.length() + 1];

        // index >= chars.length return 1
        arr[str.length()] = 1;
        for (int index = str.length() - 1; index >= 0; index--) {
            //是0直接跳过，因为从后往前构造，所以要不是0，要不是0x等，都是不合理的。如果是从前往后构造的化，x0是合理的。
            if ("0".equals(String.valueOf(str.charAt(index)))) {
                continue;
            }
            //只要当前位置
            int nub1 = arr[index + 1];
            //要当前位置与后一位置
            if (index + 1 != str.length()) {
                int nub2 = arr[index + 2];
                String ans = String.valueOf(str.charAt(index)) + String.valueOf(str.charAt(index + 1));
                if (Integer.parseInt(ans) <= 26) {
                    arr[index] = nub2 + nub1;
                    continue;
                }
            }
            arr[index] = nub1;
        }
        return arr[0];
    }

    public static int dp1(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        char[] str = s.toCharArray();
        int N = str.length;
        int[] dp = new int[N + 1];
        dp[N] = 1;
        for (int i = N - 1; i >= 0; i--) {
            if (str[i] != '0') {
                int ways = dp[i + 1];
                if (i + 1 < str.length && (str[i] - '0') * 10 + str[i + 1] - '0' < 27) {
                    ways += dp[i + 2];
                }
                dp[i] = ways;
            }
        }
        return dp[0];
    }


    public static void main(String[] args) {
        String str = "1232411676101915";
        System.out.println(number(str));

        System.out.println(number2(str));

        System.out.println(dp1(str));
    }
}
