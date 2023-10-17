package algorithmbasic.questionsets.class1;
import java.util.Arrays;
import java.util.LinkedList;

/**
 * 有序数组，每个点都是x轴坐标上的对应点，一段绳子的长度是m，求绳子可以覆盖的最大点数。
 */
public class CordCoverMaxPoint {
    //计算绳子的起点到绳子的终点有多少的点被覆盖。
    public static int maxPoint1(int[] arr, int L) {
        int res = -1;
        for (int index = 0; index < arr.length; index++) {
            //以arr[index]为结尾，以arr[index-L]为起点，中间有几个数字。
            //痛点：最靠近arr[index-L]所对应数组的下标是多少。
            int nearestIndex = nearestIndex(arr, index, arr[index] - L);
            res = Math.max(res, index - nearestIndex + 1);
        }
        return res;
    }

    //寻找最靠近value的所对应的数组的下标是多少。
    public static int nearestIndex(int[] arr, int R, int value) {
        int L = 0;
        int index = R;/**---------注意-----*/
        while (L <= R) {
            int mid = L + ((R - L) >> 1);
            if (arr[mid] >= value) {/**-------- >=号 --------*/
                index = mid;
                R = mid - 1;
            } else {
                L = mid + 1;
            }
        }
        return index;
    }

    //滑动窗口的方法
    public static int maxPoint2(int[] arr, int Long) {
        LinkedList<Integer> list = new LinkedList<>();
        int R = 0;
        int res = -1;
        for (int L = 0; L < arr.length; L++) {
            while (R < arr.length && arr[R] <= arr[L] + Long) {
                list.add(R);
                R++;
            }
            res = Math.max(res, list.size());
            list.pollFirst();
        }
        return res;
    }

    // for test
    public static int test(int[] arr, int L) {
        int max = 0;
        for (int i = 0; i < arr.length; i++) {
            int pre = i - 1;
            while (pre >= 0 && arr[i] - arr[pre] <= L) {
                pre--;
            }
            max = Math.max(max, i - pre);
        }
        return max;
    }

    // for test
    public static int[] generateArray(int len, int max) {
        int[] ans = new int[(int) (Math.random() * len) + 1];
        for (int i = 0; i < ans.length; i++) {
            ans[i] = (int) (Math.random() * max);
        }
        Arrays.sort(ans);
        return ans;
    }

    public static void main(String[] args) {
        int len = 10;
        int max = 10;
        int testTime = 100000;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            int L = (int) (Math.random() * max);
            int[] arr = generateArray(len, max);
            int ans1 = maxPoint1(arr, L);
            int ans2 = maxPoint2(arr, L);
            int ans3 = test(arr, L);
            if (ans1 != ans2 || ans2 != ans3) {
                System.out.println("oops!");
                System.out.println(ans1);
                System.out.println(ans2);
                System.out.println(ans3);
                System.out.println(Arrays.toString(arr));
                break;
            }
        }
        System.out.println("test end");
    }
}