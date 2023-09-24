package algorithmbasic.basicsets.class15;

import java.io.*;

// 这个文件课上没有讲
// 原理和课上讲的完全一样
// 最大的区别就是这个文件实现的并查集是用数组结构，而不是map结构
// 请务必理解这个文件的实现，而且还提供了测试链接
// 提交如下的code，并把"Code06_UnionFind"这个类名改成"Main"
// 在测试链接里可以直接通过
// 请同学们务必参考如下代码中关于输入、输出的处理
// 这是输入输出处理效率很高的写法
// 测试链接 : https://www.nowcoder.com/questionTerminal/e7ed657974934a30b2010046536a5372
public class UnionFind2 {
    /**
     * 属性
     */
    public static int MAX = 100001;
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
        father = new int[MAX];
        size = new int[MAX];
        help = new int[MAX];
        sets = n;
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
        if(fatherX != fatehrY) {
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
    public int sets() {
        return sets;
    }


    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StreamTokenizer in = new StreamTokenizer(br);
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        while (in.nextToken() != StreamTokenizer.TT_EOF) {
            int n = (int) in.nval;
            init(n);
            in.nextToken();
            int m = (int) in.nval;
            for (int i = 0; i < m; i++) {
                in.nextToken();
                int op = (int) in.nval;
                in.nextToken();
                int x = (int) in.nval;
                in.nextToken();
                int y = (int) in.nval;
                if (op == 1) {
                    out.println(isSameSet(x, y) ? "Yes" : "No");
                    out.flush();
                } else {
                    union(x, y);
                }
            }
        }
    }
}