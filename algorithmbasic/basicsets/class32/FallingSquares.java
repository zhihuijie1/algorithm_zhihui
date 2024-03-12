package algorithmbasic.basicsets.class32;

import java.util.LinkedList;
import java.util.List;


/**
 * 在二维平面上的 x 轴上，放置着一些方块。
 * 给你一个二维整数数组 positions ，其中 positions[i] = [lefti, sideLengthi] 表示：第 i 个方块边长为 sideLengthi ，
 * 其左侧边与 x 轴上坐标点 lefti 对齐。
 * 每个方块都从一个比目前所有的落地方块更高的高度掉落而下。方块沿 y 轴负方向下落，直到着陆到 另一个正方形的顶边 或者是 x 轴上 。
 * 一个方块仅仅是擦过另一个方块的左侧边或右侧边不算着陆。一旦着陆，它就会固定在原地，无法移动。
 * 在每个方块掉落后，你必须记录目前所有已经落稳的 方块堆叠的最高高度 。
 * 返回一个整数数组 ans ，其中 ans[i] 表示在第 i 块方块掉落后堆叠的最高高度。
 *

/**
 * 输入：positions = [[1,2],[2,3],[6,1]]
 * 输出：[2,5,5]
 * 解释：
 * 第 1 个方块掉落后，最高的堆叠由方块 1 组成，堆叠的最高高度为 2 。
 * 第 2 个方块掉落后，最高的堆叠由方块 1 和 2 组成，堆叠的最高高度为 5 。
 * 第 3 个方块掉落后，最高的堆叠仍然由方块 1 和 2 组成，堆叠的最高高度为 5 。
 * 因此，返回 [2, 5, 5] 作为答案。
 */

/**
 * 思路：当输入的数组是[a,b]时，先查询[a, a+b)这个范围上最大的高度是多少(假设是h),然后更新[a, a+b)这个范围上的最大值为: h+b
 * 查询函数的具体细节：
 *      - 维护一个MAX函数，这个函数是统计某个区间的最大值，当调用查询函数时，递归调用MAX函数
 * 更新函数的具体细节：
 *      - 先调用MAX函数，得到当前区间的最大值，然后再更新当前区间的最大值。
 */

/**
 * 复杂度分析：
 */
public class FallingSquares {
    public List<Integer> fallingSquares(int[][] positions) {// [[1,2],[2,3],[6,1]] --> [2, 5, 5]
        List<Integer> ans = new LinkedList<>();
        int index = 0;
        for (int i = 0; i < positions.length; i++) {
            int L = positions[i][0];
            int C = positions[i][1];
            int R = L + C;
            //SegmentTree.build(L, R - 1, 1);
            /*int MAX = SegmentTree.getMAX(L, R - 1, 1);
            SegmentTree.update(L, R - 1, MAX + C, L, R - 1, 1);
            ans.add(index++, MAX + C);*/
        }
        return ans;
    }

    static class SegmentTree {
        static int[] Max;
        static int[] change;
        static boolean[] update;

        public SegmentTree(int size) {
            int N = size + 1;
            Max = new int[N << 2];
            change = new int[N << 2];
            update = new boolean[N << 2];
        }

        public static void pushDown(int rt) {
            if (update[rt]) {
                Max[rt << 1] = change[rt];
                Max[rt << 1 | 1] = change[rt];
                update[rt << 1] = true;
                update[(rt << 1) | 1] = true;
                change[rt << 1] = change[rt];
                change[rt << 1 | 1] = change[rt];
                //change[rt] = 0;
                update[rt] = false;
            }
        }

        public static int getMAX(int L, int R, int l, int r, int rt) {
            if (l >= L && r <= R) {
                return Max[rt];
            }
            pushDown(rt);
            int a = Integer.MIN_VALUE;
            int b = Integer.MIN_VALUE;
            int mid = (l + r) >> 1;
            if (L <= mid) {
                a = getMAX(L, R, l, mid, rt << 1);
            }
            if (R > mid) {
                b = getMAX(L, R, mid + 1, r, rt << 1 | 1);
            }
            return Math.max(a, b);
        }

        public static void update(int L, int R, int C, int l, int r, int rt) {
            if (l >= L && r <= R) {
                change[rt] = C;
                update[rt] = true;
                Max[rt] = C;
                return;
            }
            pushDown(rt);
            int mid = (l + r) >> 1;
            if (L <= mid) {
                update(L, R, C, l, mid, rt << 1);
            }
            if (R > mid) {
                update(L, R, C, mid + 1, r, (rt << 1) | 1);
            }
            Max[rt] = Math.max(Max[rt << 1], Max[rt << 1 | 1]);
        }
    }
}