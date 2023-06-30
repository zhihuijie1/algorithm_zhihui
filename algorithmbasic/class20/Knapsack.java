package algorithmbasic.class20;

/**
 * 背包问题
 * 给定两个长度都为N的数组weights和values，weights[i]和values[i]分别代表 i号物品的重量和价值
 * 给定一个正数bag，表示一个载重bag的袋子，装的物品不能超过这个重量
 * 返回能装下的最大价值
 */
public class Knapsack {

    // w 10 5 7 6
    // v 20 2 3 8
    // bag 16
    // 返回在w数组中满足不超过bag下的最大价值
    /*public static int maxValue(int[] w, int[] v, int bag, int index) {

        if (bag < 0) {
            return 0;
        }

        if (index == w.length) {
            return 0;
        }

        int maxV = Integer.MIN_VALUE;

        for (int i = index; i < w.length; i++) {

            //返回在w数组中满足不超过bag下的最大价值
            int ans = maxValue(w, v, (bag - w[i]), index + 1);

            int mny = ans + v[i];

            maxV = Math.max(maxV, mny);
        }

        return maxV;
    }*/




    public static int maxValue2(int[] w, int[] v, int bag) {
        if (w == null || v == null || w.length != v.length || w.length == 0) {
            return 0;
        }
        // 尝试函数！
        return process(w, v, 0, bag);
    }

    // index 0~N
    // rest 负~bag
    public static int process(int[] w, int[] v, int index, int rest) {
        if (rest < 0) {
            return -1;
        }
        if (index == w.length) {
            return 0;
        }
        int p1 = process(w, v, index + 1, rest);
        int p2 = 0;
        int next = process(w, v, index + 1, rest - w[index]);
        if (next != -1) {
            p2 = v[index] + next;
        }
        return Math.max(p1, p2);
    }

    public static void main(String[] args) {
        /*int[] weights = { 3, 2, 4, 7, 3, 1, 7 };
        int[] values = { 5, 6, 3, 19, 12, 4, 2 };*/

        int[] weights = {3, 2, 4, 7, 3, 1, 7};
        int[] values = {5, 6, 3, 19, 12, 4, 2};

        int bag = 15;
        /*System.out.println(maxValue(weights, values, bag, 0));*/
        System.out.println(maxValue2(weights, values, bag));
    }
}
