package algorithmbasic.basicsets.class33;

// ----------------------- 这个写法的执行规则是原数组已经给出，然后对原数组进行修改然后进行操作 -------------------
public class IndexTree2D {
    // 二维indexTree
    // 注意必须从1下标开始，才匹配后面的规律
    //   1  2  3  4  5  6  7  8  9  10  11
    // 1 *  *  *  *  *  *  *  *  *   *  *
    // 2 *  *  *  *  *  *  *  *  *   *  *
    // 3 *  *  *  *  *  *  *  *  *   *  *
    // 4 *  *  *  *  *  *  *  *  *   *  *
    // 5 *  *  *  *  *  *  *  *  *   *  *
    // 6 *  *  *  *  *  *  *  *  *   *  *
    // 7 *  *  *  *  *  *  *  *  *   *  *
    // 8 *  *  *  *  *  *  *  *  *   *  *
    // 9 *  *  *  *  *  *  *  *  *   *  *
    // 10*  *  *  *  *  *  *  *  *   *  *
    // 11*  *  *  *  *  *  *  *  *   *  *

    //计算区间的累加和
    //更新某点的值

    private static int[][] tree;
    private static int[][] change;
    private static int N;
    private static int M;

    public IndexTree2D(int[][] arr) {
        //根据原数组进行重构
        N = arr.length;
        M = arr[0].length;
        tree = new int[N + 1][M + 1];
        change = new int[N][M];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                update(i, j, arr[i][j]);
            }
        }
    }

    //对当前行当前列的当前值进行修改，然后尽心范围修改
    //担任两个角色 -- 1:开始时的新数组构建  2:构建完毕后的单点更新
    public static void update(int row, int col, int res) {
        if (row < 0 || col < 0 || row > N || col > M) {
            return;
        }
        int add = change[row][col] - res;//增量
        for (int i = row + 1; i < N + 1; i += (i & -i)) {
            for (int j = col + 1; j < M + 1; j += (j * -j)) {
                tree[i][j] += add;
            }
        }
    }

    //计算从[1,1]开始到[row, col]的累加和
    public static int sum(int row, int col) {
        int add = 0;
        for (int i = row + 1; i > 0; i -= i & -i) {
            for (int j = col + 1; j > 0; j -= j & -j) {
                add += tree[i][j];
            }
        }
        return add;
    }

    //查询指定范围的累加和
    public static int sumRegion(int row1, int col1, int row2, int col2) {
        if (row1 < 0 || row2 < 0 || col1 < 0 || col2 < 0 || row2 < row1 || col2 < col1) {
            return -1;
        }
        return sum(row2, col2) - sum(row2, col1 - 1) - sum(row1 - 1, col2) + sum(row1 - 1, col1 - 1);
    }
}


/*
时间复杂度分析：
重构数组的时候  对原数组中的数进行了二分统计 -- 类似于归并排序的merge过程 将一个范围小范围的累加和 放在最后一个数值上
查询的时候依据的时二分的规则 时间复杂度是 logN
计算sum时 时间复杂度是 logM * logN
*/