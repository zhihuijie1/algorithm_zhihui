package algorithmbasic.leetcode.p1;

//https://leetcode.com/problems/longest-increasing-path-in-a-matrix/description/
public class LongestIncreasingPath {
    /**
     * Given an m x n integers matrix, return the length of the longest increasing path in matrix.
     * From each cell, you can either move in four directions: left, right, up, or down.
     * You may not move diagonally or move outside the boundary (i.e., wrap-around is not allowed).
     */
    public int longestIncreasingPath(int[][] matrix) {
        if (matrix == null) {
            return 0;
        }
        int N = matrix.length;
        int M = matrix[0].length;
        int dp[][] = new int[N][M];
        int ans = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                ans = Math.max(ans, process(matrix, i, j, dp));
            }
        }
        return ans;
    }

    //返回值 -- 某个位置为起点，最大递增链的长度
    private static int process(int[][] matrix, int i, int j, int[][] dp) {
        if (dp[i][j] != 0) {
            return dp[i][j];
        }
        int N = matrix.length;
        int M = matrix[0].length;
        //因为条件判断已经提前写好了所以不用单独写
        int up = i > 0 && (matrix[i - 1][j] > matrix[i][j]) ? process(matrix, i - 1, j, dp) : 0;
        int down = i < N - 1 && (matrix[i + 1][j] > matrix[i][j]) ? process(matrix, i + 1, j, dp) : 0;
        int left = j > 0 && (matrix[i][j - 1] > matrix[i][j]) ? process(matrix, i, j - 1, dp) : 0;
        int right = j < M - 1 && (matrix[i][j + 1] > matrix[i][j]) ? process(matrix, i, j + 1, dp) : 0;
        int ans = Math.max(Math.max(up, down), Math.max(left, right)) + 1;
        dp[i][j] = ans;
        return ans;
    }
}
