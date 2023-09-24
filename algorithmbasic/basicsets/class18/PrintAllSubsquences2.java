package algorithmbasic.basicsets.class18;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

//打印一个字符串的全部子序列，要求不要出现重复字面值的子序列
public class PrintAllSubsquences2 {

    public static List<String> subsNoRepeat(String s) {
        //先将字符串转化成char类型的数组。
        char[] str = s.toCharArray();
        //存储最后的结果。
        List<String> ans = new LinkedList<>();
        //去重工具。
        HashSet<String> set = new HashSet<>();
        int index = 0;
        String path = " ";
        process(str, index, ans, path, set);
        return ans;
    }

    //遍历这个数组，将所有的结果都记录在ans数组中。
    public static void process(char[] str, int index, List<String> ans, String path, HashSet<String> set) {
        if (index == str.length) {
            if (!set.contains(path)) {
                set.add(path);
                ans.add(path);
            }
            return;
        }

        //要当前的数。
        process(str, index + 1, ans, path + String.valueOf(str[index]), set);

        //不要当前的数。
        process(str, index + 1, ans, path, set);
    }

    public static void main(String[] args) {
        String test = "acccc";
        List<String> ans2 = subsNoRepeat(test);
        for (String str : ans2) {
            System.out.println(str);
        }
        System.out.println("=================");
        List<String> ans3 = subsNoRepeat2(test);
        for (String str : ans3) {
            System.out.println(str);
        }
    }

    public static List<String> subsNoRepeat2(String s) {
        char[] str = s.toCharArray();
        String path = "";
        HashSet<String> set = new HashSet<>();
        process2(str, 0, set, path);
        List<String> ans = new ArrayList<>();
        for (String cur : set) {
            ans.add(cur);
        }
        return ans;
    }

    public static void process2(char[] str, int index, HashSet<String> set, String path) {
        if (index == str.length) {
            set.add(path);
            return;
        }
        String no = path;
        process2(str, index + 1, set, no);
        String yes = path + String.valueOf(str[index]);
        process2(str, index + 1, set, yes);
    }
}
