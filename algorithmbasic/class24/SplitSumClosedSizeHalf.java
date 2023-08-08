package algorithmbasic.class24;

/**
 * 给定一个正数数组arr，请把arr中所有的数分成两个集合
 * 如果arr长度为偶数，两个集合包含数的个数要一样多
 * 如果arr长度为奇数，两个集合包含数的个数必须只差一个
 * 请尽量让两个集合的累加和接近
 * 返回最接近的情况下，较小集合的累加和
 * <p>
 * 字节笔试
 */

public class SplitSumClosedSizeHalf {
    public static int right(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        //计算arr数组的累加和
        int sum = 0;
        for (int nub : arr) {
            sum += nub;
        }
        //偶数长度
        if (arr.length % 2 == 0) {
            return process(arr, 0, arr.length / 2, sum / 2);
        } else { //奇数长度
            //注意此处应该取最大值。
            return Math.max(process(arr, 0, arr.length / 2, sum / 2), process(arr, 0, arr.length / 2 + 1, sum / 2));
        }

    }

    //从index位置开始向后遍历，找到picks个数字组成的最接近rest但不超过rest的累加和。
    public static int process(int[] arr, int index, int picks, int rest) {
        if (index == arr.length) {
            //如果返回-1的话，这种情况本身就是错误的。
            return picks == 0 ? 0 : -1;
        }
        //还没走到头
        if (picks == 0) {
            return 0;
        }
        //既没走到头，picks又不等于0
        //要当前位置的数。
        int p1 = -1;
        int next = -1;
        next = process(arr, index + 1, picks - 1, rest - arr[index]);

        if (next != -1 && arr[index] <= rest) {
            p1 = arr[index] + next;
        }
        //不要当前位置的数
        int p2 = process(arr, index + 1, picks, rest);

        return Math.max(p1, p2);
    }

}
