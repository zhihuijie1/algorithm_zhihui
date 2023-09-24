package algorithmbasic.basicsets.class16;
// https://leetcode.cn/problems/number-of-islands-ii/

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 方法二：时间复杂度是O(K)
 * 课上讲的如果m*n比较大，会经历很重的初始化，而k比较小，怎么优化的方法
 */

public class numIslands2_2 {

    public static List<Integer> numIslands22(int m, int n, int[][] positions) {
        List<Integer> ans = new ArrayList<>();
        UnionFind2 unionFind2 = new UnionFind2(m, n);
        for (int[] p : positions) {
            ans.add(unionFind2.connect(p[0], p[1]));
        }
        return ans;
    }


    /**
     * 并查集内部类
     */
    public static class UnionFind2 {
        /**
         * 属性
         */
        //之前写的并查集我们需要额外建立一个Dot类来区分相同的元素
        //其实可以直接用一个String来区分，比如说3行4列 写成 ->"3_4"
        static HashMap<String, String> fatherMap;
        static HashMap<String, Integer> sizeMap;
        static int sets;
        //行
        static int H;
        //列
        static int L;
        //辅助数组
        static ArrayList<String> list;

        /**
         * 构造器
         */
        public UnionFind2(int m, int n) {
            fatherMap = new HashMap<>();
            sizeMap = new HashMap<>();
            list = new ArrayList<>();
            sets = 0;
            this.H = m;
            this.L = n;
        }

        /**
         * findAncestor方法
         * 创建一个参数，直至找到他的祖先
         */
        public static String findAncestor(String k) {

            while (!fatherMap.get(k).equals(k)) {
                list.add(k);
                k = fatherMap.get(k);
            }
            // k == fatherMap.get(k).
            for (String s : list) {
                fatherMap.put(s, k);
            }
            list.clear();
            return k;
        }

        /**
         * union方法
         */
        public static void union(String key, String k) {
            //如果有传进来的k，就进行合并。
            if (fatherMap.containsKey(k)) {
                //找到key的祖先fatherA
                String fatherA = findAncestor(key);
                //找到k的祖先fatherB
                String fatherB = findAncestor(k);
                //如果两个祖先不一样的就合并。
                if (!fatherA.equals(fatherB)) {
                    //big指向集合比较大的祖先
                    String big = sizeMap.get(fatherA) > sizeMap.get(fatherB) ? fatherA : fatherB;
                    String small = big == fatherA ? fatherB : fatherA;
                    //进行合并
                    fatherMap.put(small, big);
                    sizeMap.put(big, sizeMap.get(big) + sizeMap.get(small));
                    //sizeMap.remove(small);
                    //这个地方还不能remove，因为有可能其他节点会连接他。
                    sets--;
                }
            }
        }

        /**
         * connect方法
         * 传入两个参数 i,j 说明二维数组positions的 i,j位置出现了1，然后对其进行相连，连接完之后，返回二维数组目前一共有多少集合。
         */

        public static int connect(int i, int j) {
            //先判断一下这个坐标之前出现过没,如果之前出现过，说明早已连接好，就跳过
            String key = String.valueOf(i) + "_" + String.valueOf(j);
            if (!fatherMap.containsKey(key)) {
                //进行动态的初始化。
                fatherMap.put(key, key);
                sizeMap.put(key, 1);
                sets++;
                //上并进行边界判断
                String up = (i - 1 < H && i >= 0) ? String.valueOf(i - 1) + "_" + String.valueOf(j) : null;
                if (up != null) union(key, up);
                //下
                String down = (i + 1 < H && i >= 0) ? String.valueOf(i + 1) + "_" + String.valueOf(j) : null;
                if (down != null) union(key, down);
                //左
                String left = (j - 1 < L && j >= 0) ? String.valueOf(i) + "_" + String.valueOf(j - 1) : null;
                if (left != null) union(key, left);
                //右
                String right = (j + 1 < L && j >= 0) ? String.valueOf(i) + "_" + String.valueOf(j + 1) : null;
                if (right != null) union(key, left);

                union(key, up);
                union(key, down);
                union(key, left);
                union(key, right);
            }
            return sets;
        }
    }

    public static void main(String[] args) {
        int[][] arr = {{0, 1}, {1, 2}, {2, 1}, {1, 0}, {0, 2}, {0, 0}, {1, 1}};
        List<Integer> list = numIslands2_2.numIslands22(3, 3, arr);
        System.out.println(list);
    }
}
