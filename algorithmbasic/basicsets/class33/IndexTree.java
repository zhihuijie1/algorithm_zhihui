package algorithmbasic.basicsets.class33;

// ----------------------- 这个写法的执行规则是一个一个的加进去 -------------------
public class IndexTree {
    // indextree 没法进行范围更新，但是可以进行单点更新，与范围查询
    private static int[] tree;
    private static int N;

    public IndexTree(int[] arr) {
        N = arr.length + 1;
        tree = new int[N];
    }
    //0 3 2 4 1 5 7 3 8 1  2 9  13 数值
    //0 1 2 3 4 5 6 7 8 9 10 11 12 下标 注意必须从1下标开始，才匹配后面的规律

    // 1 2 3 4 5 6 7 8 9  10 11 12 13 14 15 16 下标
    // 1 1 3 1 5 5 7 1 9  9  11 9 13 13 15 1
    // | | | | | | | | |  |  |  |  |  |  |  |   管理的范围
    // 1 2 3 4 5 6 7 8 9  10 11 12 13 14 15 16

    // 计算 1 - index位置的累加和：
    // 假设计算1 - 15位置的累加和是sum(15),
    // sum(15) = sum(8) + sum(12) +  sum(14) + sum[15]
    // 15 -> 1111
    // 1110 -> 14
    // 1100 -> 12
    // 1000 -> 8

    // sum（26） = sum（24）+ sum（16）+ arr[26]
    // 11010       11000     10000      11010

    // 总结一个规律：不断的剔除最后一个1来计算 1 - index 位置的累加和
    //
    // arr[11010] 管理的是[25,26]范围累加和
    // 继续 加上arr[11000] -> 管理的范围是[17,24]范围的累加和
    // 继续 加上arr[10000] -> 管理的范围是[1,16]范围的累加和

    // 剔除最后一个1的方法：
    // index = 11010;
    //         00101;取反
    //         00110;取反后加1
    // -index = 00110;
    // index & -index = 11010 & 00110 = 00010; -> 最后一个1的位置
    // index - (index & -index) ;-> 剔除最后一个1后得到的结果

    // 原理：
    //
    //

    /**
     * 计算 1-index 位置的的累加和
     **/
    public static int sum(int index) {
        int answer = 0;
        //1: 对index位置剔除最后一个1
        //2: 从后往前不断的剔除1
        while (index > 0) {
            answer += tree[index];
            index = index - (-index & index);
        }
        return answer;
    }

    // 对单点位置进行修改
    // public void add(int index, int d) {} 在index位置添加数值d
    // 确定单点位置的修改导致影响的范围：
    // 假设单点修改的位置是：index = 101(5),
    // 则受到影响的是：101(5) 110(6) 1000(8) 1 0000(16) 10 0000(32)
    //
    // 11010(26) -> 11100(28) 10 0000(32) 100 0000(64)

    // 总结一个规律：对index位置进行修改时，确定影响范围的方法：
    // 先将index位置二进制最后一个1剔除出来，然后加上这个剔除出来的1
    // 例如：index = 101101（45） -> 剔除最后一个位置的1 然后index += index & -index;现在index = 101110(46)
    // 继续循环 index = 101110(46) -> 剔除最后一个位置的1 然后index += index & -index;现在index = 110000(48)
    // ......

    // 原理：101(5) 110(6) 1000(8) 1 0000(16) 10 0000(32)
    //
    //
    //

    /**
     * 在 index 位置增加 d
     **/
    public static void add(int index, int d) {
        //1: 确定修改index位置会受到影响的范围
        //2：对受到影响的范围进修修改
        int curIndex = index;
        while (curIndex < N) {
            tree[curIndex] += d;
            curIndex += (curIndex & -curIndex);
        }
    }


    /**
     * 查询范围 [l , r] 之间的累加和
     **/
    public static int find(int l, int r) {
        return sum(r) - sum(l - 1);
    }
}
