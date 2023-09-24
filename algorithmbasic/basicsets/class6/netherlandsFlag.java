package algorithmbasic.basicsets.class6;

// 荷兰国旗问题
public class netherlandsFlag {
    // arr[L...R] 玩荷兰国旗问题的划分，以arr[R]做划分值
    // <arr[R] ==arr[R] > arr[R]
    public static int[] netherlandsFlag(int[] arr, int L, int R) {
        if (L > R) {
            return new int[]{Integer.MIN_VALUE, Integer.MIN_VALUE};
        }
        if (L == R) {
            return new int[]{L, R};
        }
        int less = L - 1;
        int more = R;
        int index = L;
        while (index < more) {
            if (arr[index] < arr[R]) {
                //swap(arr, index, less + 1);
                //less++;
                //index++;
                swap(arr, index++, ++less);
            } else if (arr[index] > arr[R]) {
                swap(arr, index, --more);
            } else {
                index++;
            }
        }
        swap(arr, R, more);
        return new int[]{less + 1, more};
    }

    public static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}
