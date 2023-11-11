package algorithmbasic.basicsets.class26;

import java.util.Stack;

/**
 * 给定一个非负数组arr，代表直方图，返回直方图的最大长方形面积
 */
public class LargestRectangleInHistogram {
    /**
     * 思路：最终的结果（最大长方形的面积）一定是以数组中某个值作高的。所以我以数组中每个位置i作高，尽量使长方形面积最大，然后最终找到最大的长方形面积。
     */

    /**
     * 利用单调栈来锁定每个位置的左右边界。
     * 遍历数组，采用单调栈来锁定位置的左右边界。
     */
    public static int largestRectangleArea1(int[] height) {
        Stack<Integer> stack = new Stack<>();
        int MAX = Integer.MIN_VALUE;
        for (int i = 0; i < height.length; i++) {
            //通过维护单调栈来锁定每一个位置的左右边界
            while (!stack.isEmpty() && height[i] < height[stack.peek()]) {
                //结算以当前位置做高的最大长方形面积
                int j = stack.pop();//当前位置下标
                int leftBorder = stack.isEmpty() ? -1 : stack.peek();//leftBorder:左边界下标
                int rightBorder = i;
                MAX = Math.max(MAX, (rightBorder - leftBorder - 1) * height[j]);
            }
            stack.push(i);
            //height[i] >= height[stack.peek()]都push进去
        }
        //处理栈内残余数据
        while(!stack.isEmpty()) {
            //结算以当前位置做高的最大长方形面积
            int j = stack.pop();//当前位置下标
            int leftBorder = stack.isEmpty() ? -1 : stack.peek();//leftBorder:左边界下标
            int rightBorder = height.length;
            MAX = Math.max(MAX, (rightBorder - leftBorder - 1) * height[j]);
        }
        return MAX;
    }
}
