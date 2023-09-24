package algorithmbasic.basicsets.class16;

//一群朋友中，有几个不相交的朋友圈
//Leetcode题目：https://leetcode.com/problems/friend-circles/
public class findCircleNum {
    public int findCircleNum(int[][] isConnecetd) {
        //先初始化一下并查集，刚开始每一个元素都是一个集合。
        UnionFind.init(isConnecetd.length);
        //注意我们遍历的是正方行的右上角，因为我们默认的是：0跟3认识，那么3就跟0认识。
        for (int i = 0; i < isConnecetd.length; i++) {
            for (int j = i + 1; j < isConnecetd.length; j++) {
                //如果isConnecetd[i][j] == 1,说明i与j认识，就将i与j放到一个集合中。
                if (isConnecetd[i][j] == 1) {
                    UnionFind.union(i, j);
                }
            }
        }
        return UnionFind.sets();
    }


    /**
     * UnionFind内部类
     * 是一个并查集内部类。
     */

    private static class UnionFind {
        /**
         * 属性
         */
        public static int MAX;
        //father数组:下标是集合元素，数组内容是father。
        //father[i] = k:i的父亲是k。
        public static int[] father;
        //size数组:下标是头部集合的元素，数组内容是这个集合的元素个数。
        //size[i] = k:i只有是祖先节点，才有意义。
        public static int[] size;
        //help数组：主要是进行优化用的,里面存储着寻找祖先节点是经过的节点，当找到祖先节点后，将途径的节点直接连接到祖先节点上，
        //从而降低了查找的长度。
        public static int[] help;
        //sets:记录一共有多少个集合
        private static int sets;

        /**
         * init方法
         * 并查集的初始化。
         */
        public static void init(int n) {
            MAX = n;
            sets = n;
            father = new int[MAX];
            size = new int[MAX];
            help = new int[MAX];
            for (int i = 0; i < n; i++) {
                father[i] = i;
                size[i] = 1;
            }
        }


        /**
         * findAncestor方法
         * 传入一个元素，找这个元素的祖先。
         */
        public static int findAncestor(int x) {
            int j = 0;
            while (x != father[x]) {
                //将途径的节点放入到help辅助数组中。
                help[j++] = x;
                x = father[x];
            }
            // x == father[x]现在的x就是祖先节点。
            //将途径的节点直接连到祖先节点上，从而降低了下次查找的长度。
            j--;
            while (j >= 0) {
                father[help[j]] = x;
                j--;
            }
            return x;
        }

        /**
         * isSameSet方法
         * 查询两个元素是否是在同一个集合
         */
        public static boolean isSameSet(int x, int y) {
            //找两个元素的祖先。
            return findAncestor(x) == findAncestor(y);
        }

        /**
         * union方法
         * 将两个元素所在的集合进行合并
         */
        public static void union(int x, int y) {
            //找到元素x所在集合的祖先。
            int fatherX = findAncestor(x);
            //找到元素y所在集合的祖先。
            int fatehrY = findAncestor(y);
            if (fatherX != fatehrY) {
                //找到fatherX所在集合的元素个数
                int nubX = size[fatherX];
                //找到fatherY所在集合的元素个数
                int nubY = size[fatehrY];
                //big指向的是元素个数比较多的头部节点。
                //small指向的是元素个数比较少的头部节点。
                int big = nubX > nubY ? fatherX : fatehrY;
                int small = big == fatherX ? fatehrY : fatherX;
                //将集合元素小的挂在集合元素多的祖先节点下。
                size[big] = size[big] + size[small];
                size[small] = 0;
                father[small] = big;
                sets--;
            }
        }

        /**
         * sets方法
         * 返回一共有多少个节点。
         */
        public static int sets() {
            return sets;
        }
    }

    public static void main(String[] args) {
        findCircleNum f = new findCircleNum();
        int[][] isConnecetd = {{1, 1, 1}, {1, 1, 1}, {1, 1, 1}};
        int res = f.findCircleNum(isConnecetd);
        System.out.println(res);
    }
}