package algorithmbasic.basicsets.class14;

import java.util.Arrays;
import java.util.Comparator;
import java.util.TreeSet;

//给定一个由字符串组成的数组strs，必须把所有的字符串拼接起来，返回所有可能的拼接结果中字典序最小的结果
public class LowestLexicography {

    //暴力写法
    // lowestString1 : 返回所有可能的拼接结果中字典序最小的结果
    public static String lowestString1(String[] strs) {
        if (strs == null || strs.length == 0) {
            return "";
        }
        TreeSet<String> set = process(strs);
        return set == null || set.size() == 0 ? "" : set.first();
    }

    // process : strs中所有字符串的可能情况全排列，返回所有可能情况。
    public static TreeSet<String> process(String[] strs) {
        TreeSet<String> ans = new TreeSet<>();
        if (strs == null || strs.length == 0) {
            ans.add("");
            return ans;
        }
        for (int index = 0; index < strs.length; index++) {
            String first = strs[index];
            String[] nexts = removeIndex(strs, index);
            TreeSet<String> next = process(nexts);
            for (String cur : next) {
                ans.add(first.concat(cur));
            }
        }
        return ans;
    }

    public static String[] removeIndex(String[] strs, int index) {
        String[] ans = new String[strs.length - 1];
        int ansIndex = 0;
        for (int i = 0; i < strs.length; i++) {
            if (i != index) {
                ans[ansIndex++] = strs[i];
            }
        }
        return ans;
    }

    //贪心写法
    public static String lowestString2(String[] strs) {
        if (strs == null || strs.length == 0) {
            return "";
        }
        Arrays.sort(strs, new MyComparator());
        String ans = strs[0];
        for (int i = 1; i < strs.length; i++) {
            ans = ans.concat(strs[i]);
        }
        return ans;
    }

    public static class MyComparator implements Comparator<String> {
        @Override
        public int compare(String o1, String o2) {
            // compareTo方法是用来比较两个字符串的字典顺序。
            // 如果返回值小于0，则表示(o1 + o2)小于(o2 + o1)，
            // 如果返回值等于0，则表示两者相等，
            // 如果返回值大于0，则表示(o1 + o2)大于(o2 + o1)。
            return (o1 + o2).compareTo(o2 + o1);
        }
    }

    // -------------------------------- for test -------------------------------

    public static void main(String[] args) {
        int testTime = 10000;
        int strArrayLength = 5;
        int strLength = 5;
        System.out.println("test begin");
        for (int i = 0; i < testTime; i++) {
            String[] arr1 = generateRandomStringArray(strArrayLength, strLength);
            String[] arr2 = copyStringArray(arr1);
            if (!lowestString1(arr1).equals(lowestString2(arr2))) {
                System.out.println(arr1);
                System.out.println(arr2);
                System.out.println("ooops");
            }
        }
        System.out.println("finish");
    }

    public static String[] generateRandomStringArray(int strArrayLength, int strLength) {
        String[] string = new String[(int) (Math.random() * strArrayLength + 1)];
        for (int i = 0; i < string.length; i++) {
            char[] c = new char[(int) (Math.random() * strLength + 1)];
            int a = (int) (Math.random() * 26); //[0,25]
            for (int j = 0; j < c.length; j++) {
                c[j] = (Math.random() < 0.5 ? (char) (65 + a) : (char) (95 + a));
            }
            string[i] = c.toString();
        }
        return string;
    }

    public static String[] copyStringArray(String[] ans) {
        String[] ret = new String[ans.length];
        for (int i = 0; i < ans.length; i++) {
            ret[i] = ans[i];
        }
        return ret;
    }
}
