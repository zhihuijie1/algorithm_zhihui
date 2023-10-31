package algorithmbasic.basicsets.class26;

import java.util.Stack;

/**
 * 给定一个仅包含 0 和 1 、大小为 rows x cols 的二维二进制矩阵，找出只包含 1 的最大矩形，并返回其面积。
 */
public class MaximalRectangle {
    /**
     * 思路：假设我已经找到最大的矩形了，那这个矩形的底边一定在某一行。所以我采用压缩数组的方法，行自上向下将高度进行压缩，然后采用单调栈的方式
     * 寻找矩阵的宽度区间。
     */

    /**
     * 步骤：从上往下遍历数组，进行数组压缩，将这一行压缩完之后，采用单调栈的方式进行矩阵宽度的区间锁定。
     */
    public static int maximalRectangle(char[][] map) {
        int[] height = new int[map[0].length];//存放压缩后的数组
        int MAX = Integer.MIN_VALUE;
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                //对当前行进行数组压缩
                height[j] = map[i][j] == '0' ? 0 : height[j] + 1; // ------------------- 小细节 ----------------
            }
            //当前行数组压缩完毕，然后进行宽度区间的锁定
            MAX = Math.max(MAX, maxRecFromBottom(height));
        }
        return MAX;
    }

    private static int maxRecFromBottom(int[] height) {
        Stack<Integer> stack = new Stack<>();
        int MAX = Integer.MIN_VALUE;
        for (int i = 0; i < height.length; i++) {
            if (stack.isEmpty() || height[i] >= height[stack.peek()]) {
                stack.push(i);
                continue;
            }
            while (!stack.isEmpty() && height[i] < height[stack.peek()]) {
                int j = stack.pop();
                int leftBorder = stack.isEmpty() ? -1 : stack.peek();
                int rightBorder = i;
                MAX = Math.max(MAX, (rightBorder - leftBorder - 1) * height[j]);
            }
            stack.push(i);
        }
        while (!stack.isEmpty()) {
            int j = stack.pop();
            int leftBorder = stack.isEmpty() ? -1 : stack.peek();
            int rightBorder = height.length;
            MAX = Math.max(MAX, (rightBorder - leftBorder - 1) * height[j]);
        }
        return MAX;
    }

    public static void main(String[] args) {
        char[][] map = {{'1', '0', '1', '0', '0'}, {'1', '0', '1', '1', '1'}, {'1', '1', '1', '1', '1'}, {'1', '0', '0', '1', '0'}};
        int result = maximalRectangle(map);
        System.out.println(result);
    }
}