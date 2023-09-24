package algorithmbasic.basicsets.class9;


import java.util.Arrays;

// 基数排序
public class RadixSort {

    public static void radixSort(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        int n = findmaxbits(arr); // 找到最大的位数
        for (int i = 1; i <= n; i++) {
            // create count数组
            int[] count = createCount(arr, i);
            // create presum数组
            int[] presum = createPresum(count);
            // create help[]
            int[] help = new int[arr.length];
            // 从后往前遍历
            for (int j = arr.length - 1; j >= 0; j--) {
                int bit = getUrBits(arr[j], i);
                help[--presum[bit]] = arr[j];
            }
            // 数组的拷贝
            arraycopy(arr, help);
        }
    }

    // 找到数组中最大值的位数
    public static int findmaxbits(int[] arr) {
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < arr.length; i++) {
            max = Math.max(max, arr[i]);
        }
        int cover = 0;
        while (max != 0) {
            max = max / 10;
            cover++;
        }
        return cover;
    }

    // create count数组
    public static int[] createCount(int[] arr, int i) {
        int[] count = new int[10];
        for (int j = 0; j < arr.length; j++) {
            count[getUrBits(arr[j], i)]++;
        }
        return count;
    }

    // create presum数组
    public static int[] createPresum(int[] count) {
        int[] presum = new int[count.length];
        presum[0] = count[0];
        for (int i = 1; i < presum.length; i++) {
            presum[i] = presum[i - 1] + count[i];
        }
        return presum;
    }

    // ****************************
    // ** 求个位十位百位所对应的数字 **
    // ****************************
    public static int getUrBits(int num, int bit) {
        return (num / (int) Math.pow(10, bit - 1)) % 10;
    }

    // 数组的拷贝
    public static void arraycopy(int[] arr, int[] help) {
        for (int i = 0; i < arr.length; i++) {
            arr[i] = help[i];
        }
    }

    // ------------------------------------------------------------------------------------------
    // for test
    public static void comparator(int[] arr) {
        Arrays.sort(arr);
    }

    // for test
    public static int[] generateRandomArray(int maxSize, int maxValue) {
        int[] arr = new int[(int) ((maxSize + 1) * Math.random())];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) ((maxValue + 1) * Math.random());
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
        int testTime = 500000;
        int maxSize = 100;
        int maxValue = 100000;
        boolean succeed = true;
        for (int i = 0; i < testTime; i++) {
            int[] arr1 = generateRandomArray(maxSize, maxValue);
            int[] arr2 = copyArray(arr1);
            radixSort(arr1);
            comparator(arr2);
            if (!isEqual(arr1, arr2)) {
                succeed = false;
                printArray(arr1);
                printArray(arr2);
                break;
            }
        }
        System.out.println(succeed ? "Nice!" : "Fucking fucked!");

        int[] arr = generateRandomArray(maxSize, maxValue);
        printArray(arr);
        radixSort(arr);
        printArray(arr);
    }
}
