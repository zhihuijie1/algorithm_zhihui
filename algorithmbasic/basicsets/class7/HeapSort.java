package algorithmbasic.basicsets.class7;

import java.util.Arrays;
import java.util.PriorityQueue;

// 堆排序
public class HeapSort {
    public static void heapSort(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        // 先建立一个大根堆
        int N = arr.length;
        int heapSize = N;

        /*
        方法一：
        int index = 0;
        // N * log N
        while (index < N) {
            heapInsert(arr, index);
            index++;
        }
        */
        // 方法二：
        int index = N -1;
        // N
        while(index >= 0) {
            heapfiy(arr, index, heapSize);
            index--;
        }
        // 大根堆已经建立完成
        // N * log N
        while (heapSize > 0) {
            swap(arr, 0, --heapSize);
            heapfiy(arr, 0, heapSize);
        }
    }

    public static void heapfiy(int[] arr, int index, int heapSize) {
        int l = index * 2 + 1;
        while (l < heapSize) {
            int maxIndexSon = l + 1 < heapSize ? (arr[l] < arr[l + 1] ? (l + 1) : (l)) : (l);
            if (arr[maxIndexSon] > arr[index]) {
                swap(arr, maxIndexSon, index);
                index = maxIndexSon;
                l = index * 2 + 1;
            } else {
                break;
            }
        }
    }

    public static void heapInsert(int[] arr, int index) {
        int father = (index - 1) / 2;
        while (arr[father] < arr[index]) {
            swap(arr, father, index);
            index = father;
            father = (index - 1) / 2;
        }
    }

    public static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    // for test
    public static void comparator(int[] arr) {
        Arrays.sort(arr);
    }

    // for test
    public static int[] generateRandomArray(int maxSize, int maxValue) {
        int[] arr = new int[(int) ((maxSize + 1) * Math.random())];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) ((maxValue + 1) * Math.random()) - (int) (maxValue * Math.random());
        }
        return arr;
    }

    // for test
    public static int[] copyArray(int[] arr) {
        if (arr == null) {
            return null;
        }
        int[] res = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            res[i] = arr[i];
        }
        return res;
    }

    // for test
    public static boolean isEqual(int[] arr1, int[] arr2) {
        if ((arr1 == null && arr2 != null) || (arr1 != null && arr2 == null)) {
            return false;
        }
        if (arr1 == null && arr2 == null) {
            return true;
        }
        if (arr1.length != arr2.length) {
            return false;
        }
        for (int i = 0; i < arr1.length; i++) {
            if (arr1[i] != arr2[i]) {
                return false;
            }
        }
        return true;
    }

    // for test
    public static void printArray(int[] arr) {
        if (arr == null) {
            return;
        }
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    // for test
    public static void main(String[] args) {

        // 默认小根堆
        PriorityQueue<Integer> heap = new PriorityQueue<>();
        heap.add(6);
        heap.add(8);
        heap.add(0);
        heap.add(2);
        heap.add(9);
        heap.add(1);

        while (!heap.isEmpty()) {
            System.out.println(heap.poll());
        }

        int testTime = 500000;
        int maxSize = 100;
        int maxValue = 100;
        boolean succeed = true;
        for (int i = 0; i < testTime; i++) {
            int[] arr1 = generateRandomArray(maxSize, maxValue);
            int[] arr2 = copyArray(arr1);
            heapSort(arr1);
            comparator(arr2);
            if (!isEqual(arr1, arr2)) {
                succeed = false;
                break;
            }
        }
        System.out.println(succeed ? "Nice!" : "Fucking fucked!");

        int[] arr = generateRandomArray(maxSize, maxValue);
        printArray(arr);
        heapSort(arr);
        printArray(arr);
    }
}
