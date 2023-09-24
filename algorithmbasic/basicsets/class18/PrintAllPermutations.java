package algorithmbasic.basicsets.class18;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

//打印一个字符串的全部排列
public class PrintAllPermutations {
    //方法一：暴力方法。
    public static List<String> permutation1(String s) {
        //str是一个存储字符类型的有序表。
        ArrayList<Character> str = new ArrayList<>();
        //将字符串中的类型存储在str中。
        for (Character c : s.toCharArray()) {
            str.add(c);
        }
        String path = "";
        List<String> ans = new LinkedList<>();
        process(str, path, ans);
        return ans;
    }

    //str:字符数组。
    //path：记录之前选择的答案。
    //ans：记录最终的结果。
    public static void process(ArrayList<Character> str, String path, List<String> ans) {
        if (str.isEmpty()) {
            ans.add(path);
            return;
        }


        for (int i = 0; i < str.size(); i++) {
            char value = str.get(i);
            str.remove(i);
            process(str, path + String.valueOf(value), ans);
            //还原现场,之前在那就放在哪。
            //str.add(i, value);
            str.add(value);
        }
    }


    //方法二：
    public static List<String> permutation2(String s) {
        char[] str = s.toCharArray();
        List<String> ans = new LinkedList<>();
        process(str, 0, ans);
        return ans;
    }

    public static void process(char[] str, int index, List<String> ans) {
        if (index == str.length) {
            ans.add(String.valueOf(str));
            return;
        }

        for (int i = index; i < str.length; i++) {
            swap(str, index, i);
            process(str, index + 1, ans);
            //还原现场,之前在那就放在哪。
            swap(str, index, i);
        }
    }

    public static void swap(char[] str, int i, int j) {
        char temp = str[i];
        str[i] = str[j];
        str[j] = temp;
    }

    //打印一个字符串的全部排列，要求不要出现重复的排列
    public static List<String> permutation3(String s) {
        char[] str = s.toCharArray();
        List<String> ans = new LinkedList<>();

        process2(str, 0, ans);
        return ans;
    }

    public static void process2(char[] str, int index, List<String> ans) {
        if (index == str.length) {
            ans.add(String.valueOf(str));
            return;
        }

        boolean[] visited = new boolean[256];
        for (int i = index; i < str.length; i++) {
            if (!visited[str[i]]) {
                visited[str[i]] = true;
                swap(str, index, i);
                process2(str, index + 1, ans);
                //还原现场,之前在那就放在哪。
                swap(str, index, i);
            }
        }
    }


    public static void main(String[] args) {
        String s = "acc";
        List<String> ans1 = permutation2(s);
        for (String str : ans1) {
            System.out.println(str);
        }
        System.out.println("=======");

        String s2 = "acc";

        List<String> ans2 = permutation3(s2);
        for (String str : ans2) {
            System.out.println(str);
        }
        System.out.println("=======");
    }
}
