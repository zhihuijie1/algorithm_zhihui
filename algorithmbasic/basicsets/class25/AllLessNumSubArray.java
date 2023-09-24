package algorithmbasic.basicsets.class25;

import java.util.LinkedList;

/**
 * 给定一个整型数组arr，和一个整数num
 * 某个arr中的子数组sub，如果想达标，必须满足：sub中最大值 – sub中最小值 <= num，
 * 返回arr中达标子数组的数量
 */
public class AllLessNumSubArray {
    public static int num(int[] arr, int sum) {
        if (arr == null || arr.length == 0 || sum < 0) {
            return 0;
        }

        int count = 0;
        LinkedList<Integer> maxWindow = new LinkedList<>();
        LinkedList<Integer> minWindow = new LinkedList<>();

        //[L,R]
        int R = 0;
        for (int L = 0; L < arr.length; L++) {
            //R不回退
            while (R < arr.length) {
                //维护大端双端队列
                while (!maxWindow.isEmpty() && arr[R] >= arr[maxWindow.peekLast()]) {
                    maxWindow.pollLast();
                }
                maxWindow.addLast(R);

                //维护小端双端队列
                while (!minWindow.isEmpty() && arr[R] <= arr[minWindow.peekLast()]) {
                    minWindow.pollLast();
                }
                minWindow.addLast(R);

                //进行条件判断
                if (arr[maxWindow.peekFirst()] - arr[minWindow.peekFirst()] > sum) {
                    break;
                } else {
                    R++;
                }
            }
            count += R - L;
            while (!maxWindow.isEmpty() && maxWindow.peekFirst() <= L) {
                maxWindow.pollFirst();
            }
            while (!minWindow.isEmpty() && minWindow.peekFirst() <= L) {
                minWindow.pollFirst();
            }
        }
        return count;
    }

    public static int right(int[] arr, int sum) {
        if (arr == null || arr.length == 0 || sum < 0) {
            return 0;
        }
        int N = arr.length;
        int count = 0;
        for (int L = 0; L < N; L++) {
            for (int R = L; R < N; R++) {
                int max = arr[L];
                int min = arr[L];
                for (int i = L + 1; i <= R; i++) {
                    max = Math.max(max, arr[i]);
                    min = Math.min(min, arr[i]);
                }
                if (max - min <= sum) {
                    count++;
                }
            }
        }
        return count;
    }

    // for test
    public static int[] generateRandomArray(int maxLen, int maxValue) {
        int len = (int) (Math.random() * (maxLen + 1));
        int[] arr = new int[len];
        for (int i = 0; i < len; i++) {
            arr[i] = (int) (Math.random() * (maxValue + 1)) - (int) (Math.random() * (maxValue + 1));
        }
        return arr;
    }

    // for test
    public static void printArray(int[] arr) {
        if (arr != null) {
            for (int i = 0; i < arr.length; i++) {
                System.out.print(arr[i] + " ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        int maxLen = 10;
        int maxValue = 20;
        int testTime = 100000;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            int[] arr = generateRandomArray(maxLen, maxValue);
            int sum = (int) (Math.random() * (maxValue + 1));
            int ans1 = right(arr, sum);
            int ans2 = num(arr, sum);
            if (ans1 != ans2) {
                System.out.println("Oops!");
                printArray(arr);
                System.out.println(sum);
                System.out.println(ans1);
                System.out.println(ans2);
                break;
            }
        }
        System.out.println("测试结束");

    }


    /*public static void main(String[] args) {
        int[] arr = {3, 7, 4, -6, -5};
        int sum = 13; //right -> 15
        int nub = num(arr, sum);
        System.out.println(nub);

    }*/
}
