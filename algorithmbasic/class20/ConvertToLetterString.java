package algorithmbasic.class20;

/**
 * 规定1和A对应、2和B对应、3和C对应...26和Z对应
 * 那么一个数字字符串比如"111”就可以转化为:
 * "AAA"、"KA"和"AK"
 * 给定一个只有数字字符组成的字符串str，返回有多少种转化结果
 */
public class ConvertToLetterString {
    public static int number(String str) {
        if (str == null || str.length() < 1) {
            return 0;
        }

        char[] chars = str.toCharArray();
        return process(chars, 0);
    }

    //返回所有可能出现的情况。
    public static int process(char[] chars, int index) {

        if (index >= chars.length) {
            return 1;
        }

        //这个截止条件十分的重要。
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
}
