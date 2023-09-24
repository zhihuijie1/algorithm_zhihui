package algorithmbasic.basicsets.class16;
// https://leetcode.cn/problems/number-of-islands-ii/

import java.util.ArrayList;
import java.util.List;

public class numIslands2_1 {

    /**
     * 方法一：时间复杂度是O(m * n + k)
     */
    public static List<Integer> numIslands21(int m, int n, int[][] positions) {
        //对并查集进行初始化操作，创建好一维数组father，size，以及辅助数组help。设置记录集合数量的变量sets。
        UnionFind1 unionFind1 = new UnionFind1(m, n);
        List<Integer> ans = new ArrayList<>();
        for (int[] p : positions) {
            ans.add(unionFind1.connect(p[0], p[1]));
        }
        return ans;
    }

    /**
     * 并查集内部类。
     */
    public static class UnionFind1 {
        /**
         * 属性
         */
        static int[] father;
        static int[] size;
        static int[] help;
        static int sets;
        //列的长度大小
        static int L;
        //行的长度大小
        static int H;

        /**
         * 构造器
         * 这里我们没有一上来就直接进行初始化操作，我们需要走一步看一步。
         */
        public UnionFind1(int m, int n) {
            father = new int[m * n];
            size = new int[m * n];
            help = new int[m * n];
            sets = 0;
            L = n;
            H = m;
        }

        /**
         * index方法
         * 传入二维数组的行列参数：i,j 找到对应的一维数组的下标 k
         * 公式：K = i * positions[0].length + j
         */

        public static int index(int i, int j) {
            return i * L + j;
        }

        /**
         * findAncestor方法
         * 传入一个节点，然后从这个节点开始一直往上找，直到直到最上边为止，返回最上面的节点。
         */
        public static int findAncestor(int cur) {
            int j = 0;
            while (cur != father[cur]) {
                //进行优化，将途径的节点进行记录。
                help[j++] = cur;
                cur = father[cur];
            }
            //cur == father[cur]
            j--;
            while (j >= 0) {
                father[help[j--]] = cur;
            }
            return cur;
        }


        /**
         * union合并方法
         * 将 i,j   m,n两个位置所在的集合进行合并
         */

        public static void union(int i, int j, int m, int n) {
            if (m < 0 || m >= H || n < 0 || n >= L) {
                return;
            }
            //根据二维数组的下标找到一维数组的下标。
            int i1 = index(i, j);
            int i2 = index(m, n);
            if (size[i1] != 0 && size[i2] != 0) {
                //找到各自节点的祖先节点。
                int fatherA = findAncestor(i1);
                int fatherB = findAncestor(i2);
                if (fatherA != fatherB) {
                    //找到祖先节点fatherA所在集合大小sizeA。
                    int sizeA = size[fatherA];
                    //找到祖先节点fatherB所在集合大小sizeB。
                    int sizeB = size[fatherB];
                    //big指向集合数量较多的祖先节点。
                    int big = sizeA > sizeB ? fatherA : fatherB;
                    //small指向集合数量较少的祖先节点。
                    int small = big == fatherA ? fatherB : fatherA;
                    //进行合并
                    //注意这个地方size[small]不要被置为0.
                    father[small] = big;
                    size[big] = size[big] + size[small];
                    sets--;
                }
            }
        }


        /**
         * connect连接方法
         * 传入两个参数 i,j 说明二维数组positions的 i,j位置出现了1，然后对其进行相连，连接完之后，返回二维数组目前一共有多少集合。
         */
        public static int connect(int i, int j) {
            int k = index(i, j);
            //之前没来过
            if (size[k] == 0) {
                //初始化当前位置的father与size数组。
                size[k] = 1;
                father[k] = k;
                sets++;
                union(i, j, i + 1, j);
                union(i, j, i - 1, j);
                union(i, j, i, j + 1);
                union(i, j, i, j - 1);
            }
            return sets;
        }
    }



}