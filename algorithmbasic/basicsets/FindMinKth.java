package algorithmbasic.basicsets;

/**
 * 在无序数组中求第K小的数
 * 1）改写快排的方法
 * 2）bfprt算法
 */
public class FindMinKth {
    /**
     * 使用快排的方法实现，时间复杂度是O（N）
     * 思路：乱序数组进行荷兰国旗问题的划分 <  | =  | >， 如果K正好处于等于区内，那就直接返回第k小的数
     * 如果K没有处于等于区内那就去<区或>区中的那个，然后继续进行荷兰国旗的划分，直到找到第K小的数。
     */
    static class Info {
        int left;
        int right;

        public Info(int left, int right) {
            this.left = left;
            this.right = right;
        }
    }

    public static int minKth1(int[] array, int k) {
        if (array == null || k < 1 || k > array.length) {
            return -1;
        }
        return process(array, 0, array.length - 1, k);
    }

    public static int process(int[] array, int L, int R, int k) {
        Info info = part(array, L, R);
        if (k <= info.right && k >= info.left) {
            return array[k];
        } else if (k < info.left) {
            return process(array, L, info.left - 1, k);
        } else {
            return process(array, info.right + 1, R, k);
        }
    }

    // 荷兰国旗问题
    // 思路：选中一个目标值，然后进行划分，小于目标值的放在左边，等于目标值的放在中间，大于目标值的放在右边
    private static Info part(int[] array, int LB, int RB) { // key -> 目标值
        int key = array[LB];
        int L = -1;
        int R = array.length;
        for (int i = LB; i <= RB || i < R; i++) {
            if (array[i] < key) {
                swap(array, L + 1, i);
                L++;
            } else if (array[i] > key) {
                swap(array, R - 1, i);
                i--;
                R--;
            }
        }
        return new Info(L + 1, R - 1);
    }

    public static void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    /**
     * 使用bfprt算法实现
     * bfprt算法 -> 精确的选出划分值，保证时间复杂度是可预测的。
     * 算法流程：1：将一个大数组以5个数为一小组进行切分，切分成 N / 5 个数组。
     * 2：对每个数组进行排序
     * 3：创建一个大小为 N / 5 的新数组，并将每一个小数组中的中位数取出来放在这个新数组中
     * 4：对这个新数组进行排序，并将其中位数取出，这个中位数就是划分值。
     * <p>
     * 目的 -> 保证最差情况下一次可以最少取出 N / 10 个数据
     */
    public static int minKth2(int[] array, int k) {
        if (array == null || k < 1 || k > array.length) {
            return -1;
        }
        return process2(array, 0, array.length - 1, k);
    }

    public static int process2(int[] array, int L, int R, int k) {
        int  index = brft(array, L, R);
        Info info = part2(array, L, R, index);
        if (k <= info.right && k >= info.left) {
            return array[k];
        } else if (k < info.left) {
            return process(array, L, info.left - 1, k);
        } else {
            return process(array, info.right + 1, R, k);
        }
    }

    private static Info part2(int[] array, int LB, int RB, int index) {
        int key = index;
        int L = -1;
        int R = array.length;
        for (int i = LB; i <= RB || i < R; i++) {
            if (array[i] < key) {
                swap(array, L + 1, i);
                L++;
            } else if (array[i] > key) {
                swap(array, R - 1, i);
                i--;
                R--;
            }
        }
        return new Info(L + 1, R - 1);
    }

    private static int brft(int[] array, int l, int r) {
        int offsize = array.length % 5 == 0 ? 0 : 1;
        int[] midArray = new int[array.length / 5 + offsize];
        int low = 0;
        int fast = 4;
        int index = 0;
        while (fast < array.length) {
            int mid = getMidNum(array, low, fast); // mid是中位数数值
            midArray[index++] = mid;
            fast += 5;
            low += 5;
        }
        midArray[index] = getMidNum(array, low, array.length - 1);
        return getMidNum(midArray, 0, midArray.length - 1);
    }

    private static int getMidNum(int[] array, int L, int R) {
        //进行插入排序，然后取出中位数
        insertionSort(array, L, R);
        return array[(L + R) / 2];
    }

    private static void insertionSort(int[] arr, int L, int R) {
        for (int i = L + 1; i <= R; i++) {
            for (int j = i - 1; j >= L && arr[j] > arr[j + 1]; j--) {
                swap(arr, j, j + 1);
            }
        }
    }
}
