package algorithmbasic.basicsets.class26;

import java.util.Arrays;
import java.util.Stack;

/**
 * 给你一个 m x n 的二进制矩阵 mat ，请你返回有多少个 子矩形 的元素全部都是 1 。
 */
public class CountSubmatricesWithAllOnes {


    /**
     * 思路：在 M * N 的矩阵中，随便找一个矩形，这个矩形一定是以某一行做底边，以某几行做高。
     *      所以可以逐行遍历，计算以当前行做高的矩行有多少个，然后最后将结果进行相加。
     */

    /**
     * 步骤：计算每一行矩阵的高。然后计算以当前行做高的矩形数量。
     */
    public static int numSubmat(int[][] mat) {
        int[] height = new int[mat[0].length];
        int num = 0;
        for (int i = 0; i < mat.length; i++) {
            //计算每一行矩阵的高，将其放入height数组中
            for (int j = 0; j < mat[0].length; j++) {
                if (mat[i][j] != 0) {
                    height[j] = height[j] + 1;
                } else {
                    height[j] = 0;
                }
            }
            //当前行的高度都收集在height数组中，接下来开始计算当前行做底边的矩形数量
            num += rectangleCounts(height);
        }
        return num;
    }

    //计算当前行做底边的矩形数量
    public static int rectangleCounts(int[] height) {
        Stack<Integer> stack = new Stack<>();
        int sum = 0;
        for (int i = 0; i < height.length; i++) {
            if (stack.isEmpty() || height[i] > height[stack.peek()]) {
                stack.push(i);
            } else if (!stack.isEmpty() && height[i] == height[stack.peek()]) { //------------------注意：相同的时候，用作后出现的数进行结算，目的是为了防止算重复。
                stack.pop();
                stack.push(i);
            } else {
                while (!stack.isEmpty() && height[i] < height[stack.peek()]) {
                    int j = stack.pop();
                    int leftBoarder = stack.isEmpty() ? -1 : stack.peek();
                    int rightBorder = i;
                    int L = rightBorder - leftBoarder - 1;
                    int numbers = height[j] - Math.max(leftBoarder == -1 ? 0 : height[leftBoarder], height[rightBorder]);
                    sum += numbers * ((L * (L + 1)) >> 1);
                }
                stack.push(i);
            }
        }
        while (!stack.isEmpty()) {
            int j = stack.pop();
            int leftBoarder = stack.isEmpty() ? -1 : stack.peek();
            int rightBorder = height.length;
            int L = rightBorder - leftBoarder - 1;
            int numbers = height[j] - (leftBoarder == -1 ? 0 : height[leftBoarder]); // ------------------------ 注意
            sum += numbers * ((L * (L + 1)) >> 1);
        }
        return sum;
    }


    public static void main(String[] args) {
        int[][] nub = {{1,0,1},{1,1,0},{1,1,0}};
        numSubmat(nub);
    }
}
