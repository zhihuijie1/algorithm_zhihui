package algorithmbasic.basicsets.class32;

public class SegmentTree {
    /**
     * 线段树主要解决的问题是：在一个区间上进行增，改，查，并且时间复杂度是logN
     * 下标统一以1开始，这样使用下标可以快速的确定左右孩子的下标，左孩子：2*i，右孩子：2*i+1
     */
    public static class SegTree {
        //MAXN:传入数组的长度
        //arr:从下标1开始复刻传入数组 原因：
        //sum:模拟线段树维护区间和,从下标为1开始记录
        //lazy:为累加和懒惰标记
        //change:为更新的值
        //update为更新慵懒标记 原因：如果change[x]==0，会出现歧义到底是这个区间要跟新为0还是这个区间不需要更新。所以引用update这个布尔类型进行标记到底需不需要更新。
        private int MAXN;
        private static int[] arr;
        private static int[] sum;
        private static int[] lazy;
        private static int[] change;
        private static boolean[] update;

        public SegTree(int[] ans) {
            MAXN = ans.length;
            arr = new int[MAXN + 1];
            sum = new int[MAXN << 2];
            lazy = new int[MAXN << 2];
            change = new int[MAXN << 2];
            update = new boolean[MAXN << 2];
            for (int i = 1; i < MAXN + 1; i++) {
                arr[i] = ans[i - 1];
            }
        }

        //在刚开始的时候要先构建sum数组(虚构的二叉树)原因：如果一开始就add/update其实sum这颗二叉树上是没有值的。所有的操作都是基于sum二叉树进行的
        //lazy数组是辅助数组，主要是拦截的作用。
        //
        public static void build(int L, int R, int rt) {
            if (L == R) {
                sum[rt] = arr[L];
                return;
            }
            int mid = (R + L) >> 1;
            build(L, mid, rt << 1);
            build(mid + 1, R, rt << 1 | 1);
            sum[rt] = sum[rt << 1] + sum[rt << 1 | 1];
        }

        //向下抛
        public static void pushDown(int rt, int ln, int rn) {
            //update 在 lazy 判断的前面。原因：进行交叉操作的时候，如果当前位置的已经更新了，那lazy就需要重新进行进行计数。
            if (update[rt]) {
                update[rt << 1] = true;
                update[rt << 1 | 1] = true;
                change[rt << 1] = change[rt];
                change[rt << 1 | 1] = change[rt];
                sum[rt << 1] = ln * change[rt];
                sum[rt << 1 | 1] = rn * change[rt];
                lazy[rt << 1] = 0;
                lazy[rt << 1 | 1] = 0;
                update[rt] = false;
            }
            if (lazy[rt] != 0) {
                lazy[rt << 1] += lazy[rt];
                sum[rt << 1] += lazy[rt] * ln;
                lazy[rt << 1 | 1] += lazy[rt];
                sum[rt << 1 | 1] += lazy[rt] * rn;
                lazy[rt] = 0;
            }
        }

        //任务：在[L,R]这个区间都加上C
        //目前来到的区间是[l,r],sum对应的下标是rt
        public static void add(int L, int R, int C, int l, int r, int rt) {
            if (L <= l && r <= R) {
                sum[rt] += (r - l + 1) * C;
                lazy[rt] += C;
                return;
            }
            int mid = (l + r) >> 1;
            pushDown(rt, mid - l + 1, r - mid);
            if (L <= mid) {
                add(L, R, C, l, mid, rt << 1);
            }
            if (R > mid) {
                add(L, R, C, mid + 1, r, rt << 1 | 1);
            }
            sum[rt] = sum[rt << 1] + sum[rt << 1 | 1];
        }

        public static void update(int L, int R, int C, int l, int r, int rt) {
            if (L <= l && r <= R) {
                update[rt] = true;
                change[rt] = C;
                sum[rt] = (r - l + 1) * C;
                lazy[rt] = 0;
                return;
            }
            //当前任务躲不掉，无法懒更新，要往下发
            int mid = (l + r) >> 1;
            pushDown(rt, mid - l + 1, r - mid);
            if (L <= mid) {
                update(L, R, C, l, mid, rt << 1);
            }
            if (R > mid) {
                update(L, R, C, mid + 1, r, rt << 1 | 1);
            }
            sum[rt] = sum[rt << 1] + sum[rt << 1 | 1];
        }

        public static long quary(int L, int R, int l, int r, int rt) {
            if (L <= l && r <= R) {
                return sum[rt];
            }
            long ans = 0;
            int mid = (r + l) >> 1;
            pushDown(rt, mid - l + 1, r - mid); //父节点搞不定，就将父节点的重担分发给子节点。
            if (L <= mid) {
                ans += quary(L, R, l, mid, rt << 1);
            }
            if (R > mid) {
                ans += quary(L, R, mid + 1, r, rt << 1 | 1);
            }
            return ans;
        }
    }
    //------------------------------------ for test --------------------------------
    public static class Right {
        private static int[] arr;

        public Right(int[] ans) {
            arr = new int[ans.length + 1];
            for (int i = 0; i < ans.length; i++) {
                arr[i + 1] = ans[i];
            }
        }

        public static void add(int L, int R, int C) {
            for (int i = L; i <= R; i++) {
                arr[i] += C;
            }
        }

        public static void update(int L, int R, int C) {
            for (int i = L; i <= R; i++) {
                arr[i] = C;
            }
        }

        public static long quary(int L, int R) {
            long ans = 0;
            for (int i = L; i <= R; i++) {
                ans += arr[i];
            }
            return ans;
        }
    }

    public static int[] genarateRandomArray(int len, int max) {
        int size = (int) (Math.random() * len) + 1;
        int[] origin = new int[size];
        for (int i = 0; i < size; i++) {
            origin[i] = (int) (Math.random() * max) - (int) (Math.random() * max);
        }
        return origin;
    }

    public static boolean test() {
        int len = 100;
        int max = 1000;
        int testTimes = 5000;
        int addOrUpdateTimes = 1000;
        int queryTimes = 500;
        for (int i = 0; i < testTimes; i++) {
            int[] origin = genarateRandomArray(len, max);
            SegTree seg = new SegTree(origin);
            int S = 1;
            int N = origin.length;
            int root = 1;
            seg.build(S, N, root);
            Right rig = new Right(origin);
            for (int j = 0; j < addOrUpdateTimes; j++) {
                int num1 = (int) (Math.random() * N) + 1;
                int num2 = (int) (Math.random() * N) + 1;
                int L = Math.min(num1, num2);
                int R = Math.max(num1, num2);
                int C = (int) (Math.random() * max) - (int) (Math.random() * max);
                if (Math.random() < 0.5) {
                    seg.add(L, R, C, S, N, root);
                    rig.add(L, R, C);
                } else {
                    seg.update(L, R, C, S, N, root);
                    rig.update(L, R, C);
                }
            }
            for (int k = 0; k < queryTimes; k++) {
                int num1 = (int) (Math.random() * N) + 1;
                int num2 = (int) (Math.random() * N) + 1;
                int L = Math.min(num1, num2);
                int R = Math.max(num1, num2);
                long ans1 = seg.quary(L, R, S, N, root);
                long ans2 = rig.quary(L, R);
                if (ans1 != ans2) {
                    return false;
                }
            }
        }
        return true;
    }

    public static void main(String[] args) {
        int[] origin = { 2, 1, 1, 2, 3, 4, 5 };
        SegTree seg = new SegTree(origin);
        int S = 1; // 整个区间的开始位置，规定从1开始，不从0开始 -> 固定
        int N = origin.length; // 整个区间的结束位置，规定能到N，不是N-1 -> 固定
        int root = 1; // 整棵树的头节点位置，规定是1，不是0 -> 固定
        int L = 2; // 操作区间的开始位置 -> 可变
        int R = 5; // 操作区间的结束位置 -> 可变
        int C = 4; // 要加的数字或者要更新的数字 -> 可变
        // 区间生成，必须在[S,N]整个范围上build
        seg.build(S, N, root);
        // 区间修改，可以改变L、R和C的值，其他值不可改变
        seg.add(L, R, C, S, N, root);
        long sum = seg.quary(L, R, S, N, root);
        System.out.println(sum);
        // 区间更新，可以改变L、R和C的值，其他值不可改变
        seg.update(L, R, C, S, N, root);
        // 区间查询，可以改变L和R的值，其他值不可改变
        sum = seg.quary(L, R, S, N, root);
        System.out.println(sum);

        System.out.println("对数器测试开始...");
        System.out.println("测试结果 : " + (test() ? "通过" : "未通过"));

    }
}

//rt << 1 | 1





//线段树的适用范围：不用调研左边与右边的具体细节，左边与右边的信息直接拿来用就行O(1)










