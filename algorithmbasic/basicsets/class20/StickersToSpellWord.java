package algorithmbasic.basicsets.class20;

import java.util.HashMap;

/**
 * 给定一个字符串str，给定一个字符串类型的数组arr，出现的字符都是小写英文
 * arr每一个字符串，代表一张贴纸，你可以把单个字符剪开使用，目的是拼出str来
 * 返回需要至少多少张贴纸可以完成这个任务
 * 例子：str= "babac"，arr = {"ba","c","abcd"},每张贴纸都是无穷张
 * ba + ba + c  3  abcd + abcd 2  abcd+ba 2
 * 所以返回2
 */
public class StickersToSpellWord {
    /**
     * 暴力方法
     */
    public static int minStickers1(String[] stickers, String target) {
        if (stickers == null || target == null || "".equals(target)) {
            return 0;
        }
        int nub = process1(stickers, target);
        return nub == Integer.MAX_VALUE ? -1 : nub;
    }

    //返回至少多少张贴纸可以完成这个任务。
    public static int process1(String[] stickers, String target) {
//        if ("".equals(target)) {
//            return 0;
//        }
        if (target.length() == 0) {
            return 0;
        }
        int min = Integer.MAX_VALUE;
        //随机取出一个str。
        for (String str : stickers) {
            //将target中与str重合的部分去除，重新返回一个String。
            String ret = minus(str, target);
            if (ret.length() != target.length()) {
                int nub = process1(stickers, ret);
                min = Math.min(min, nub);
            }
        }
        return min + (min == Integer.MAX_VALUE ? 0 : 1);
    }

    //将target中与str重合的部分去除，重新返回一个String。
    private static String minus(String str, String target) {
        /*//将字符串target中与str重合的部分用“”替代。
        String ret = target.replace(str, "");
        return ret;
        之所以错是因为str必须是target中的连续字符子集。
        */
        char[] str1 = str.toCharArray();
        char[] str2 = target.toCharArray();
        //记录字母的数目。
        int[] count = new int[26];
        //记录target含有哪些字母以及这些字母的数目。
        for (char ch : str2) {
            count[ch - 'a']++;
        }
        //将与target重和的字母去除。
        for (char ch : str1) {
            count[ch - 'a']--;
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < count.length; i++) {
            while (count[i] > 0) {
                stringBuilder.append((char) (i + 'a'));
                count[i]--;
            }
        }
        return stringBuilder.toString();
    }

    /**
     * 小优化 --> 剪枝
     */
    public static int minStickers2(String[] stickers, String target) {
        if (stickers == null || target == null || "".equals(target)) {
            return 0;
        }
        //对stickers数组中字符串词频进行统计。
        int N = stickers.length;
        int[][] count = new int[N][26];
        for (int i = 0; i < stickers.length; i++) {
            char[] chars = stickers[i].toCharArray();
            for (int j = 0; j < chars.length; j++) {
                count[i][chars[j] - 'a']++;
            }
        }
        int nub = process2(count, target);
        return nub == Integer.MAX_VALUE ? -1 : nub;
    }

    //返回完成任务所需的最少纸牌数目。
    public static int process2(int[][] stickers, String target) {
        if (target.length() == 0) {
            return 0;
        }
        //对target词频进行统计。
        char[] tchars = target.toCharArray();
        int[] tcount = new int[26];
        for (int i = 0; i < tchars.length; i++) {
            tcount[tchars[i] - 'a']++;
        }
        int min = Integer.MAX_VALUE;
        //剪枝 -- 找stickers中有target字符串首字母的字符串。
        for (int i = 0; i < stickers.length; i++) {
            //tchars[0] -- target字符串的首字母。
            //tchar[0]-'a' -- target的首字母是谁，对应的数组下标是几。
            if (stickers[i][tchars[0] - 'a'] > 0) {
                //删除stickers[i]中与target一样的字母，并拼接出剩余字符串。
                StringBuilder sb = new StringBuilder();
                for (int j = 0; j < 26; j++) {
                    int num = tcount[j] - stickers[i][j];
                    //tcount[j] > stickers[i][j] -- 正数
                    //tcount[j] = stickers[i][j] -- 0
                    //tcount[j] < stickers[i][j] -- 负数
                    //只有正数才会拼接。
                    for (int k = 0; k < num; k++) {
                        sb.append((char) (j + 'a'));
                    }
                }
                String str = sb.toString();
                int num = process2(stickers, str);
                min = (num == Integer.MAX_VALUE ? min : Math.min(min, num + 1));
            }
        }
        return min;
    }

    /**
     * target是一直变化的变量，但是无法确定这个字符串的变化范围，无法使用表格法/迭代法。
     * 使用记忆性搜索。
     */

    public static int minStickers3(String[] stickers, String target) {
        if (stickers == null || target == null || "".equals(target)) {
            return 0;
        }
        //对stickers数组中字符串词频进行统计。
        int N = stickers.length;
        int[][] count = new int[N][26];
        for (int i = 0; i < stickers.length; i++) {
            char[] chars = stickers[i].toCharArray();
            for (int j = 0; j < chars.length; j++) {
                count[i][chars[j] - 'a']++;
            }
        }
        HashMap<String, Integer> map = new HashMap();
        map.put("", 0);
        int nub = process3(count, target, map);
        return nub == Integer.MAX_VALUE ? -1 : nub;
    }

    //返回完成任务所需的最少纸牌数目。
    public static int process3(int[][] stickers, String target, HashMap<String, Integer> map) {
        if (map.containsKey(target)) {
            return map.get(target);
        }
        /*if (target.length() == 0) {
            return 0;
        }*/
        //对target词频进行统计。
        char[] tchars = target.toCharArray();
        int[] tcount = new int[26];
        for (int i = 0; i < tchars.length; i++) {
            tcount[tchars[i] - 'a']++;
        }
        int min = Integer.MAX_VALUE;
        //剪枝 -- 找stickers中有target字符串首字母的字符串。
        for (int i = 0; i < stickers.length; i++) {
            //tchars[0] -- target字符串的首字母。
            //tchar[0]-'a' -- target的首字母是谁，对应的数组下标是几。
            if (stickers[i][tchars[0] - 'a'] > 0) {
                //删除stickers[i]中与target一样的字母，并拼接出剩余字符串。
                StringBuilder sb = new StringBuilder();
                for (int j = 0; j < 26; j++) {
                    int num = tcount[j] - stickers[i][j];
                    //tcount[j] > stickers[i][j] -- 正数
                    //tcount[j] = stickers[i][j] -- 0
                    //tcount[j] < stickers[i][j] -- 负数
                    //只有正数才会拼接。
                    for (int k = 0; k < num; k++) {
                        sb.append((char) (j + 'a'));
                    }
                }
                String str = sb.toString();
                int num = process3(stickers, str, map);
                min = (num == Integer.MAX_VALUE ? min : Math.min(min, num + 1));
                map.put(target, min);
            }
        }
        return min;
    }


    public static void main(String[] args) {
        String[] stickers = {"notice", "possible"};
        String target = "basicbasic";
        int i = minStickers2(stickers, target);
        System.out.println(i);

        int i1 = minStickers1(stickers, target);
        System.out.println(i1);

        int i2 = minStickers3(stickers, target);
        System.out.println(i2);
        //-2147483648
    }
}