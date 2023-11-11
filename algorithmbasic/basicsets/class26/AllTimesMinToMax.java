package algorithmbasic.basicsets.class26;

import java.util.Arrays;
import java.util.Stack;

/**
 * 给定一个只包含正数的数组arr，arr中任何一个子数组sub，一定都可以算出(sub累加和 )* (sub中的最小值)是什么，那么所有子数组中，这个值最大是多少？
 */
public class AllTimesMinToMax {
    /**
     * 思路：目标：(sub累加和 )* (sub中的最小值)，--> 指标
     * -> 最小值一定是数组中的某一个值。
     * -> 对子数组累加和的要求是：以当前数为中心向两边阔，尽量的长（正数），并且数值小于当前数。
     */

    /**
     * 遍历arr数组
     * 计算每一个元素作为最小值情况下的指标。  n
     * 利用单调栈寻找左右边界(左右侧小于当前位置数值的位置)   n
     * 计算累加和*最小值  1 (使用前缀和数组)
     */
    public static int max2(int[] arr) {
        //建设一个前缀和数组
        long[] sumArr = new long[arr.length];
        sumArr[0] = arr[0];
        for (int i = 1; i < arr.length; i++) {
            sumArr[i] = sumArr[i - 1] + arr[i];
        }
        Stack<Integer> stack = new Stack<>();
        long Max = Integer.MIN_VALUE;
        for (int i = 0; i < arr.length; i++) {
            //判断当前元素与栈顶元素值的大小
            if (stack.isEmpty() || arr[i] > arr[stack.peek()]) {
                stack.push(i);
                continue;
            }
            while (!stack.isEmpty() && arr[i] <= arr[stack.peek()]) { //注意边界条件
                int j = stack.pop();
                //计算以j为中心的指标
                int leftBoarder = stack.isEmpty() ? -1 : stack.peek();
                int rightBorder = i;
                //如果leftBoarder==-1数组越界。
                long sum = leftBoarder == -1 ? sumArr[rightBorder - 1] : sumArr[rightBorder - 1] - sumArr[leftBoarder];
                Max = Math.max(sum * arr[j], Max);
            }
            stack.push(i);
        }
        while(!stack.isEmpty()) {
            int j = stack.pop();
            //计算以j为中心的指标
            int leftBoarder = stack.isEmpty() ? -1 : stack.peek();
            int rightBorder = arr.length;
            //如果leftBoarder==-1数组越界。
            long sum = leftBoarder == -1 ? sumArr[rightBorder - 1] : sumArr[rightBorder - 1] - sumArr[leftBoarder];
            Max = Math.max(sum * arr[j], Max);
        }
        return (int) (Max % 1000000007);
    }

    // -------------------- for test ------------------------
    public static void main(String[] args) {
        int testTime = 20000;
        int size = 100;
        int max = 100;
        System.out.println("Test begin");
        for (int i = 0; i < testTime; i++) {
            //生成一个有重复值的数组。
            int[] arr = gerenareRandomArray(size, max);
            //int[] arr = {78, 86, 7, 19, 82, 89, 77};
            int result1 = max2(arr);
            int result2 = max1(arr);
            if (result1 != result2) {
                System.out.println("OOPS");
                System.out.println(Arrays.toString(arr));
                break;
            }
        }
        System.out.println("Test end");
    }

    private static int[] gerenareRandomArray(int size, int max) {
        int[] arr = new int[(int) (Math.random() * size) + 1];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) (Math.random() * max) + 1;
        }
        return arr;
    }

    //暴力解法：采用滑动窗口实现。
    public static int max1(int[] arr) {
        int MaxMult = Integer.MIN_VALUE;
        for (int i = 0; i < arr.length; i++) {
            int min = Integer.MAX_VALUE;
            int sum = 0;
            for (int j = i; j < arr.length; j++) {
                min = Math.min(min, arr[j]);
                sum += arr[j];
                MaxMult = Math.max(MaxMult, min * sum);
            }
        }
        return MaxMult;
    }
}