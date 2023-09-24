package algorithmbasic.basicsets.class5;

// 翻转对
public class BiggerThanRightTwice {
    public int reversePairs(int[] arr) {
        if (arr == null || arr.length < 2) {
            return 0;
        }
        return process(arr, 0, arr.length - 1);
    }

    // arr[L , R]范围排有序，并且返回该范围上的反转对个数
    public static int process(int[] arr, int L, int R) {
        if (L == R) {
            return 0;
        }
        int mid = L + ((R - L) >> 1);
        return
                process(arr, L, mid)
                        +
                        process(arr, mid + 1, R)
                        +
                        merge(arr, L, mid, R);
    }

    public static int merge(int[] arr, int L, int mid, int R) {
        int p1 = L;
        int p2 = mid + 1;
        // windowsR: [ , )
        int windowsR = mid + 1;
        int count = 0;
        //------------------------- 先计算
        while(p1 <= mid) {
            // 小心越界
            while(windowsR <= R && ((long)arr[windowsR] * 2) < (long)arr[p1]) {
                windowsR++;
            }
            count += (windowsR - mid - 1);
            p1++;
        }
        //------------------------- 后排序
        int[] help = new int[R - L + 1];
        int i = 0;
        p1 = L;
        while (p1 <= mid && p2 <= R) {
            help[i++] = arr[p1] < arr[p2] ? arr[p1++] : arr[p2++];
        }

        while (p1 <= mid) {
            help[i++] = arr[p1++];
        }

        while (p2 <= R) {
            help[i++] = arr[p2++];
        }

        for (int j = 0; j < help.length; j++) {
            arr[L + j] = help[j];
        }

        return count;
    }
}
