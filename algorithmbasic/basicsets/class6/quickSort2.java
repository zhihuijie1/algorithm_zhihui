package algorithmbasic.basicsets.class6;

import java.util.Stack;

// 快速排序的非递归版本
public class quickSort2 {

    public static class Op {
        int l;
        int r;

        public Op(int l, int r) {
            this.l = l;
            this.r = r;
        }
    }

    public static void quick(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        int N = arr.length;
        Stack<Op> stack = new Stack<>();
        swap(arr, (int) (Math.random() * N), N - 1);
        int[] equalArea = netherlandsFlag(arr, 0, N - 1);
        int el = equalArea[0];
        int er = equalArea[1];
        stack.push(new Op(er + 1, N - 1));
        stack.push(new Op(0, el - 1));
        while (!stack.isEmpty()) {
            // 先放右组后放左组
            // 当只有左组没有右组的时候,就会出现 : er + 1 > op.r 即：L > R
            // 当只有右组没有左组的时候，就会出现 :op.l > el - 1 即：L > R
            //
            Op op = stack.pop();
            if (op.l < op.r) {
                swap(arr, (int) (op.l + Math.random() * (op.r - op.l + 1)), op.r);
                equalArea = netherlandsFlag(arr, op.l, op.r);
                el = equalArea[0];
                er = equalArea[1];
                stack.push(new Op(er + 1, op.r));
                stack.push(new Op(op.l, el - 1));
            }
        }
    }

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
