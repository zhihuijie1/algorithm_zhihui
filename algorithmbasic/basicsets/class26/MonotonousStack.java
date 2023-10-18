package algorithmbasic.basicsets.class26;
//单调栈结构
//功能：数组中任何一个位置，找到该位置右边距离最近的且比该位置小的数，与此同时找到该位置左边距离最近的且比该位置小的数。
//单调栈的设置：从栈低到栈顶存放数字由小到大。

import java.util.Arrays;
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
            while (!stack.isEmpty() && arr[i] < arr[stack.peek()]) {
                int j = stack.pop();//栈顶元素下标
                result[j][0] = stack.isEmpty() ? -1 : stack.peek();//左侧距离最近且小的下标。
                result[j][1] = i;//右侧距离最近且小的下标
            }
            stack.push(i);
            //大于栈顶
        }
        //循环遍历结束后，栈不为空。
        //那就一个一个的开始结算。
        while (!stack.isEmpty()) {
            int j = stack.pop();//栈顶元素下标
            result[j][0] = stack.isEmpty() ? -1 : stack.peek();//左侧距离最近且小的下标。
            result[j][1] = -1;//右侧距离最近且小的下标
        }
        return result;
    }

    //------------------------for test------------------------
    public static void main(String[] args) {
        int size = 10;
        int max = 20;
        int testTime = 20000;
        System.out.println("test begin");
        for (int i = 0; i < testTime; i++) {
            //生成一个没有重复值的数组。
            int[] arr1 = getRandomArrayNoRepeat(size);
            int[][] result1 = getNearLessNoRepeat(arr1);
            int[][] rightresult = rightway(arr1);
            if (isequals(result1, rightresult)) {
                System.out.println("oops");
                break;
            }
        }
        System.out.println("test end");
    }

    //判断二维数组是否相同。
    private static boolean isequals(int[][] result1, int[][] rightresult) {
        if (result1.length != rightresult.length) {
            return false;
        }
        for (int i = 0; i < result1.length; i++) {
            if (result1[i][0] != rightresult[i][0]) {
                return false;
            }
            if (result1[i][1] != rightresult[i][1]) {
                return false;
            }
        }
        return true;
    }

    //暴力解
    private static int[][] rightway(int[] arr1) {
        int[][] result = new int[arr1.length][2];
        for (int i = 0; i < arr1.length; i++) {
            //寻找右侧距离最近且小的数。
            if (i + 1 >= arr1.length) {
                result[i][1] = -1;
            } else {
                for (int j = i + 1; j < arr1.length; j++) {
                    if (arr1[j] < arr1[i]) {
                        result[i][1] = j;
                    }
                }
            }
            //寻找左侧距离最近且小的数。
            if (i - 1 < 0) {
                result[i][0] = -1;
            } else {
                for (int j = i - 1; j >= 0; j--) {
                    if (arr1[j] < arr1[i]) {
                        result[i][0] = j;
                    }
                }
            }
        }
        return result;
    }

    //生成无重复值数组。
    private static int[] getRandomArrayNoRepeat(int size) {
        int[] arr = new int[(int) Math.random() * size + 1]; //数组长度区间[1,10]
        for (int i = 0; i < arr.length; i++) {
            arr[i] = i;
        }
        for (int i = 0; i < arr.length; i++) { //假设数组的长度是5
            int index = (int) (Math.random() * arr.length); //随机下标范围：[0,4]
            //交换i下标与index下标位置的元素。
            int temp = arr[index];
            arr[index] = arr[i];
            arr[i] = temp;
        }
        return arr;
    }
}
