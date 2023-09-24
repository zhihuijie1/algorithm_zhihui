package algorithmbasic.basicsets.class22;

/**
 * 给定一个二维数组matrix，一个人必须从左上角出发，最后到达右下角
 * 沿途只可以向下或者向右走，沿途的数字都累加就是距离累加和
 * 返回最小距离累加和
 */
public class MinPathSum {

    /**
     * 暴力方法
     */
    public static int minPathSum1(int[][] m) {
        if (m == null) {
            return 0;
        }
        return process(m, 0, 0, m.length - 1, m[0].length - 1);
    }

    //返回最小距离累加和
    public static int process(int[][] m, int x, int y, int lowerBoundary, int rightBoundary) {

        if (x == lowerBoundary && y == rightBoundary) {
            return m[x][y];
        }

        if (x == lowerBoundary) {
            return m[x][y] + process(m, x, y + 1, lowerBoundary, rightBoundary);
        }

        if (y == rightBoundary) {
            return m[x][y] + process(m, x + 1, y, lowerBoundary, rightBoundary);
        }

        //选择向下
        int p1 = m[x][y] + process(m, x + 1, y, lowerBoundary, rightBoundary);
        //选择向右
        int p2 = m[x][y] + process(m, x, y + 1, lowerBoundary, rightBoundary);
        return Math.min(p1, p2);
    }

    /**
     * dp优化
     */
    public static int minPathSum2(int[][] m) {
        if (m == null) {
            return 0;
        }
        int lowerBoundary = m.length - 1;
        int rightBoundary = m[0].length - 1;
        int[][] dp = new int[m.length][m[0].length];

        dp[lowerBoundary][rightBoundary] = m[lowerBoundary][rightBoundary];

        //下边界由右向左填。
        for (int j = rightBoundary - 1; j >= 0; j--) {
            dp[lowerBoundary][j] = m[lowerBoundary][j] + dp[lowerBoundary][j + 1];
        }

        //右边界由下往上填
        for (int i = lowerBoundary - 1; i >= 0; i--) {
            dp[i][rightBoundary] = m[i][rightBoundary] + dp[i + 1][rightBoundary];
        }

        //剩余位置 由下层往上层,由右向左填
        for (int i = lowerBoundary - 1; i >= 0; i--) {
            for (int j = rightBoundary - 1; j >= 0; j--) {
                int p1 = m[i][j] + dp[i + 1][j];
                int p2 = m[i][j] + dp[i][j + 1];
                dp[i][j] = Math.min(p1, p2);
            }
        }
        return dp[0][0];
    }



    // for test
    public static int[][] generateRandomMatrix(int rowSize, int colSize) {
        if (rowSize < 0 || colSize < 0) {
            return null;
        }
        int[][] result = new int[rowSize][colSize];
        for (int i = 0; i != result.length; i++) {
            for (int j = 0; j != result[0].length; j++) {
                result[i][j] = (int) (Math.random() * 100);
            }
        }
        return result;
    }

    // for test
    public static void printMatrix(int[][] matrix) {
        for (int i = 0; i != matrix.length; i++) {
            for (int j = 0; j != matrix[0].length; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        int rowSize = 10;
        int colSize = 10;
        int[][] m = generateRandomMatrix(rowSize, colSize);
        System.out.println(minPathSum1(m));
        System.out.println(minPathSum2(m));
        System.out.println(minPathSum3(m));

    }

    public static int minPathSum3(int[][] m) {
        if (m == null || m.length == 0 || m[0] == null || m[0].length == 0) {
            return 0;
        }
        int row = m.length;
        int col = m[0].length;
        int[] dp = new int[col];
        dp[0] = m[0][0];
        for (int j = 1; j < col; j++) {
            dp[j] = dp[j - 1] + m[0][j];
        }
        for (int i = 1; i < row; i++) {
            dp[0] += m[i][0];
            for (int j = 1; j < col; j++) {
                dp[j] = Math.min(dp[j - 1], dp[j]) + m[i][j];
            }
        }
        return dp[col - 1];
    }

}
