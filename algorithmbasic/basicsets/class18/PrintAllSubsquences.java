package algorithmbasic.basicsets.class18;

import java.util.ArrayList;
import java.util.List;

//打印一个字符串的全部子序列
public class PrintAllSubsquences {

    public static List<String> subs(String s) {
        //先将字符串转化成字符串类型的数组。
        char[] str = s.toCharArray();
        int index = 0;
        String path = " ";
        List<String> ans = new ArrayList<>();
        process(str, index, path, ans);
        return ans;
    }

    //str：是固定的参数。
    //来到了str[index]字符，index是位置
    //str[0..index-1]已经走过了！之前的决定，都在path上
    //之前的决定已经不能改变了，就是path
    //str[index....]还能决定，之前已经确定，而后面还能自由选择的话，
    //把所有生成的子序列，放入到ans里去，ans就是存储每一种情况最后的结果。
    public static void process(char[] str, int index, String path, List<String> ans) {
        //最后的位置也已经判断完了，就记录最后的结果。
        if (index == str.length) {
            ans.add(path);
            return;
        }
        //当前位置不要。
        process(str, index + 1, path, ans);
        //当前位置要。
        process(str, index + 1, path + String.valueOf(str[index]), ans);
    }

    public static void main(String[] args) {
        String test = "123";
        List<String> ans1 = subs(test);
        for (String str : ans1) {
            System.out.println(str);
        }
        System.out.println("=================");
    }
}
