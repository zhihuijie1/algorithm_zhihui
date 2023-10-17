package algorithmbasic.basicsets.class26;
//单调栈结构
//功能：数组中任何一个位置，找到该位置右边距离最近的且比该位置小的数，与此同时找到该位置左边距离最近的且比该位置小的数。
//单调栈的设置：从栈低到栈顶存放数字由小到大。

import java.util.Stack;

// arr = [ 3, 1, 2, 3]
//[
//   存放的都是下标
//   0: [-1, 1]
//   1: [-1,-1]
//   2: [1, -1]
//   3: [2, -1]
// ]
public class MonotonousStack {
    //整体思路：遍历数组中的每一个位置，然后确定当前位置左右两边距离最近且小的数。
    public static int[][] getNearLessNoRepeat(int[] arr) {
        //栈里面存的都是下标
        Stack<Integer> stack = new Stack<>();
        int[][] result = new int[arr.length][2];
        for (int i = 0; i < arr.length; i++) {
            //判断当前位置的数与栈顶数之间的大小关系。
            //小于栈顶,弹出栈顶元素，并对栈顶元素进行结算。
            while(arr[i] < arr[stack.peek()]) {
                int j = stack.pop();
                result[][]
            }
            //大于栈顶
        }
    }
}
