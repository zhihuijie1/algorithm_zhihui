package algorithmbasic.class25;

import java.util.LinkedList;

/**
 * 给定一个整型数组arr，和一个整数num
 * 某个arr中的子数组sub，如果想达标，必须满足：sub中最大值 – sub中最小值 <= num，
 * 返回arr中达标子数组的数量
 */
public class AllLessNumSubArray {
    public static int num(int[] arr, int sum) {

        /*双端队列 大 --> 小*/
        LinkedList<Integer> deque = new LinkedList<>();
        /*1：通过滑动窗口来锁定范围*/
        for (int L = -1; L < arr.length - 1; L++) {
            for (int R = L; R < arr.length; R++) {
                /*(L , R]就是窗口的范围*/
                /*2：在窗口内进行寻找最大值与最小值*/
                if (arr[R] > arr[R - 1]) {
                    int beforeIndex = R - 1;
                    while (arr[beforeIndex] <= arr[beforeIndex + 1]) {
                        swap(deque, beforeIndex, beforeIndex + 1);
                        beforeIndex--;
                    }
                } else {
                    deque.addLast(R);
                }

                while (!deque.isEmpty() && deque.peekFirst() <= L) {
                    deque.pollFirst();
                }

            }
        }

        /*3：sub中最大值 – sub中最小值 <= num count++*/
    }

    public static void swap(LinkedList<> arr, int index1, int index2) {
        int temp = arr[index1];
        arr[index1] = arr[index2];
        arr[index2] = temp;
    }
}
