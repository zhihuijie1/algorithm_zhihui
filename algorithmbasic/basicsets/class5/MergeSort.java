package algorithmbasic.basicsets.class5;

import java.util.Arrays;

// 归并排序的非递归实现
public class MergeSort {
    public static void mergeSort2(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        process(arr);
    }

    public static void process(int[] arr) {
        int N = arr.length;
        // 步长
        int mergeSize = 1;
        while (mergeSize < N) {
            int L = 0;
            while (L < N) {
                // 越界条件的判断
                if (mergeSize >= N - L) {
                    break;
                }
                // L + mergeSize也有可能越界，所以有了上面的代码
                int mid = L + mergeSize - 1;
                /*
                根据越界条件的判断，mid小于N
                if (mid > N) {
                   break;
                }
                */
                // 有左半部分
                int R = Math.min((mid + mergeSize), (N - 1));
                merge(arr, L, mid, R);
                L = R + 1;
            }
            // 防止数字越界溢出，导致死循环。
            if (mergeSize > (N / 2)) {
                break;
            }
            mergeSize *= 2;
        }
    }

    public static void merge(int[] arr, int L, int mid, int R) {
        int[] help = new int[R - L + 1];
        int p1 = L;
        int p2 = mid + 1;
        int i = 0;
        while (p1 <= mid && p2 <= R) {
            help[i++] = arr[p1] < arr[p2] ? arr[p1++] : arr[p2++];
        }
        // 后面两个循环只会执行一个，因为P1,P2并不是同时走的
        while (p1 <= mid) {
            help[i++] = arr[p1++];
        }
        while (p2 <= R) {
            help[i++] = arr[p2++];
        }
        for (int j = 0; j < help.length; j++) {
            arr[L + j] = help[j];
        }
    }

    // for test ------

    public static void main(String[] args) {
        int testTime = 100000;
        int maxValue = 1000;
        int maxArrayLength = 1000;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            int[] array1 = newArray(maxValue, maxArrayLength);
            int[] array2 = copyArray(array1);
            mergeSort2(array1);
            Arrays.sort(array2);
            for (int j = 0; j < array1.length; j++) {
                if (array1[j] != array2[j]) {
                    System.out.println("OOPS");
                }
            }
        }
        System.out.println("测试结束");
    }


    // 创建数组的函数
    public static int[] newArray(int maxValue, int maxArrayLength) {
        // 随机生成数组的长度  [0,1000)
        int arrayLength = (int) (Math.random() * maxArrayLength);
        int[] array = new int[arrayLength];
        // 给数组赋值
        for (int i = 0; i < arrayLength; i++) {
            // 产生一个随机值  [0,1000)
            int value = (int) (Math.random() * maxValue);
            array[i] = value;
        }
        return array;
    }

    // 拷贝数组的函数   这样数组的heap地址不同
    public static int[] copyArray(int[] array) {
        int[] arr = new int[array.length];
        for (int i = 0; i < array.length; i++) {
            arr[i] = array[i];
        }
        return arr;
    }
}
