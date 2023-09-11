package algorithmbasic.class25;

import java.util.LinkedList;

/**
 * 给定一个整型数组arr，和一个整数num
 * 某个arr中的子数组sub，如果想达标，必须满足：sub中最大值 – sub中最小值 <= num，
 * 返回arr中达标子数组的数量
 */
public class AllLessNumSubArray {
    public static int num(int[] arr, int sum) {
        if(arr == null || arr.length == 0 || sum < 0) {
            return -1;
        }
        LinkedList<Integer> maxWindow = new LinkedList<>();
        LinkedList<Integer> minWindow = new LinkedList<>();
        int L = 0;
        for (int R = 0; R < arr.length; R++) {
            while(arr[R] > maxWindow.peekLast()) {
                maxWindow.pollLast();
            }
            maxWindow.addLast(R);

            while(arr[R] < minWindow.peekLast()) {
                minWindow.pollLast();
            }
            minWindow.addLast(R);


        }
    }
}
