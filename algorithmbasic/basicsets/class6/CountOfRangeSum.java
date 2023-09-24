package algorithmbasic.basicsets.class6;
// 区间和的个数
// https://leetcode.com/problems/count-of-range-sum/

public class CountOfRangeSum {

    public static int countRangeSum(int[] arr, int lower, int upper) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        // 根据原始数组arr --> 创建出其对应的前缀和数组presum
        long[] presum = new long[arr.length];
        presum[0] = arr[0];
        for (int i = 1; i < arr.length; i++) {
            presum[i] = presum[i - 1] + arr[i];
        }
        return process(presum, 0, presum.length - 1, lower, upper);
    }

    // 递归函数process：目标是：1：求出该范围上有多少个连续数组符合[lower , upper]上 2：将该范围上排有序
    public static int process(long[] presum, int L, int R, int lower, int upper) {
        if (L == R) {
            if (presum[L] >= lower && presum[L] <= upper) {
                return 1;
            } else {
                return 0;
            }
        }
        // L != R
        int mid = L + ((R - L) >> 1);
        // 将左&右半部分排有序，并返回左半部分有多少个连续数组符合[lower , upper]上
        int leftPart = process(presum, L, mid, lower, upper);
        int rightPart = process(presum, mid + 1, R, lower, upper);
        int mergePart = merge(presum, L, mid, R, lower, upper);
        return leftPart + rightPart + mergePart;
    }

    /*
    merge分为两部分 1：找数 2：排序
    第1部分：重心放在右半部分，在左半部分找。以右组为结尾的数，在左组中有多少个满足[newlower , newupper]

    */
    public static int merge(long[] presum, int L, int mid, int R, int lower, int upper) {
        // 设置滑动窗口保证不回退 [ , )
        int windowsL = L;
        int windowsR = L;
        int p2 = mid + 1;
        int count = 0;
        while (p2 <= R) {
            long newLower = presum[p2] - upper;
            long newUpper = presum[p2] - lower;
            // WindowR期望让它来到的是第一个违规的位置，而WindowL期望让它来到的是第一个达标的位置。
            while (windowsR <= mid && presum[windowsR] <= newUpper) {
                windowsR++;
            }

            while (windowsL <= mid && presum[windowsL] < newLower) {
                windowsL++;
            }
            count += (windowsR - windowsL);
            p2++;
        }
        // 第2部分：排序
        long[] help = new long[R - L + 1];
        int p1 = L;
        p2 = mid + 1;
        int i = 0;
        while (p1 <= mid && p2 <= R) {
            help[i++] = presum[p1] < presum[p2] ? presum[p1++] : presum[p2++];
        }

        while (p1 <= mid) {
            help[i++] = presum[p1++];
        }

        while (p2 <= R) {
            help[i++] = presum[p2++];
        }

        for (int j = 0; j < help.length; j++) {
            presum[L + j] = help[j];
        }
        return count;
    }

}