package algorithmbasic.class20;

/**
 * 背包问题
 * 给定两个长度都为N的数组weights和values，weights[i]和values[i]分别代表 i号物品的重量和价值
 * 给定一个正数bag，表示一个载重bag的袋子，装的物品不能超过这个重量
 * 返回能装下的最大价值
 */
public class Knapsack {

    public static int maxValue(int[] w, int[] v, int bag) {

        return process2(w, v, bag, 0);
    }

    //从前往后遍历一遍数组，找到最优解并返回。
    private static int process2(int[] w, int[] v, int bag, int index) {
        if (bag < 0) {
            return -1;
        }
        if (index == w.length) {
            return 0;
        }


        //不要当前这个数
        int nice1 = process2(w, v, bag, index + 1);

        //要当前这个数
        int nice2 = process2(w, v, bag - w[index], index + 1);
        //我要当前数，但要了之后bag < 0 ,那当前数必须丢弃。
        if (nice2 != -1) {
            nice2 = (nice2 + v[index]);
        } else {
            nice2 = 0;
        }

        return Math.max(nice1, nice2);
    }


    public static int maxValue2(int[] w, int[] v, int bag) {
        if (w == null || v == null || w.length != v.length || w.length == 0) {
            return 0;
        }
        // 尝试函数！
        return process(w, v, 0, bag);
    }

    // index 0~N
    // rest 负~bag
    public static int process(int[] w, int[] v, int index, int bag) {
        if (bag < 0) {
            return -1;
        }
        if (index == w.length) {
            return 0;
        }
        //不要当前的数
        int p1 = process(w, v, index + 1, bag);

        //要当前的数
        int p2 = 0;
        int next = process(w, v, index + 1, bag - w[index]);
        if (next != -1) {
            p2 = v[index] + next;
        }
        return Math.max(p1, p2);
    }

    public static void main(String[] args) {
        /*int[] weights = { 3, 2, 4, 7, 3, 1, 7 };
        int[] values = { 5, 6, 3, 19, 12, 4, 2 };*/

        int[] weights = {3, 2, 4, 7, 3, 1, 7, 5, 9, 3};
        int[] values = {5, 6, 3, 19, 12, 4, 2, 4, 8, 6};

        int bag = 15;
        System.out.println(maxValue(weights, values, bag));
        System.out.println(maxValue2(weights, values, bag));
    }
}
