package algorithmbasic.class20;

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
        return process1(stickers, target);
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
        return min + min == Integer.MAX_VALUE ? 0 : 1;
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
        StringBuffer s = new StringBuffer();
        for(char i : str2) {
            for(char j : str1) {
                if(str2[i] != str1[j] && j == str2.length) {
                    s.append(str2[i]);
                     continue;
                }
                if(str2[i] == str1[j]) {
                    str1[j];
                }
            }
        }
    }
}
