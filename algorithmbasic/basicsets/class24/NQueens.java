package algorithmbasic.basicsets.class24;

/**
 * N皇后问题是指在N*N的棋盘上要摆N个皇后，
 * 要求任何两个皇后不同行、不同列， 也不在同一条斜线上
 * 给定一个整数n，返回n皇后的摆法有多少种。n=1，返回1
 * n=2或3，2皇后和3皇后问题无论怎么摆都不行，返回0
 * n=8，返回92
 */

public class NQueens {
    public static int right1(int n) {
        if (n <= 0) {
            return 0;
        }
        int[] record = new int[n];
        return process(0, record, n);
    }

    //当前来到index行，一共有n-1行
    //在index行上放皇后，所有的列都尝试
    //必须保证跟之前的所有的皇后都不冲突
    //record[index] -> index行的皇后放在record[index]列。
    //返回：不关心index以上发生了什么，index.... 后续有多少合法的方法数
    public static int process(int index, int[] record, int n) {
        if (index == n) {
            return 1;
        }
        int res = 0;
        for (int L = 0; L < n; L++) {
            //判断当前行列处放皇后的话会不会与前辈引起冲突。
            if (isValid(record, L, index)) {
                //index行，L列处放一个皇后。
                record[index] = L;
                res += process(index + 1, record, n);
            }
        }
        return res;
    }

    //判断当前行列处放皇后的话会不会与前辈引起冲突。
    //判断的标准是：不允许皇后在同一行，同一列，同一斜线。
    private static boolean isValid(int[] record, int L, int index) {
        for (int H = 0; H < index; H++) {
            //不允许皇后在同一列，同一斜线。
            if (record[H] != L || Math.abs(index - H) != Math.abs(L - record[H])) {
                return false;
            }
        }
        return true;
    }
}
