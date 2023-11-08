package algorithmbasic.basicsets.class27;

import java.util.Stack;

/**
 * 给定一个整数数组 arr，找到 min(b) 的总和，其中 b 的范围为 arr 的每个（连续）子数组。
 */
public class SumOfSubarrayMinimums {
    /**
     * 思路：[3,1,2,4] , 假设以数组中的某个元素作为最小值，求其连续数组的边界，然后计算连续数组的个数。
     */

    /**
     * 步骤：遍历数组一遍，入单调栈，出栈的时候 进行结算，拿到左右边界，然后计算连续数组的个数。
     * 计算的公式：(j - leftBorder) * (rightBorder - j) * arr[j]
     * 注意：遇到相等的时候，最后一个进行结算
     */

    /**
     * 思维传统：以某一个位置做标准情况下，答案是啥，以下一个位置作为标准情况下答案是啥，将所有情况遍历一下 ，就是总的答案。
     */
    public static int sumSubarrayMins(int[] arr) {
        Stack<Integer> stack = new Stack<>();
        int num = 0;
        for (int i = 0; i < arr.length; i++) {
            if (stack.isEmpty() || arr[i] > arr[stack.peek()]) {
                stack.push(i);
                continue;
            }
            while (!stack.isEmpty() && arr[i] <= arr[stack.peek()]) {
                int j = stack.pop();
                int leftBorder = stack.isEmpty() ? -1 : stack.peek();
                int rightBorder = i;
                //进行结算 -- 对以当前位置作为最小值，连续数组的个数 -- 计算的公式：(j - leftBorder) * (rightBorder - j)
                num += ((j - leftBorder) * (rightBorder - j)) * arr[j];
                System.out.println("num--" + num + "nub--" + arr[j]);
            }
            stack.push(i);
        }
        while (!stack.isEmpty()) {
            int j = stack.pop();
            int leftBorder = stack.isEmpty() ? -1 : stack.peek();
            int rightBorder = arr.length;
            //进行结算 -- 对以当前位置作为最小值，连续数组的个数 -- 计算的公式：(j - leftBorder) * (rightBorder - j)
            num += ((j - leftBorder) * (rightBorder - j)) * arr[j];
            System.out.println("num--" + num + "nub--" + arr[j]);
        }
        return num;
    }


    public static void main(String[] args) {
        int[] arr = {3, 1, 2, 4};
        int result = sumSubarrayMins(arr);
        System.out.println(result);
    }
}
