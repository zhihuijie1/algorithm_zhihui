package algorithmbasic.basicsets.class25;

import java.util.Arrays;
import java.util.LinkedList;

/**
 * 窗口内最大值或最小值更新结构的实现
 * 假设一个固定大小为W的窗口，依次划过arr，
 * 返回每一次滑出状况的最大值
 * 例如，arr = [4,3,5,4,3,3,6,7], W = 3
 * 返回：[5,5,5,4,6,7]
 */
public class SlidingWindowMaxArray {
    //arr = [4,3,5,4,3,3,6,7], W = 3  -- [5,5,5,4,6,7]
    //(L,R]
    public static int[] getMaxWindow(int[] arr, int w) {
        //过滤非法条件
        if (arr == null || arr.length < w || w < 1) {
            return null;
        }
        //创建存储返回值的数组
        //结果数目
        int nub = arr.length - w + 1;
        int[] res = new int[nub];
        int index = 0;//为res服务

        //创建双端队列 --> 存储下标,大-->小,作用：找到窗口内的最大值。
        LinkedList<Integer> list = new LinkedList<>();
        //从头开始遍历
        // [4,3,5,4,3,3,6,7]
        //L R
        for (int R = 0; R < arr.length; R++) {
            while (!list.isEmpty() && arr[list.peekLast()] <= arr[R]) { //peelLast --> 双端队列最后一个节点
                list.pollLast();
            }
            //尾插下标
            list.addLast(R);
            //弹出窗口头部节点,该弹出的节点 -> R - W
            while (!list.isEmpty() && list.peekFirst() <= R - w) {
                list.pollFirst();
            }
            //存放最大值
            if (R - w + 1 >= 0) { //只有形成了窗口你才可以进行最大值的存储。
                res[index++] = arr[list.peekFirst()];
            }
        }
        return res;
    }
    //4,3,5,4,3,3,6,7
    //0,1,2,3,4,5,6,7
    //w=3
    //R=6
    //555467

    // --------------------------------------------------------------------------------------
    // for test
    public static int[] generateRandomArray(int maxSize, int maxValue) {
        int[] arr = new int[(int) ((maxSize + 1) * Math.random())];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) (Math.random() * (maxValue + 1));
        }
        return arr;
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

    // 暴力的对数器方法
    public static int[] right(int[] arr, int w) {
        if (arr == null || w < 1 || arr.length < w) {
            return null;
        }
        int N = arr.length;
        int[] res = new int[N - w + 1];
        int index = 0;
        int L = 0;
        int R = w - 1;
        while (R < N) {
            int max = arr[L];
            for (int i = L + 1; i <= R; i++) {
                max = Math.max(max, arr[i]);

            }
            res[index++] = max;
            L++;
            R++;
        }
        return res;
    }

    public static void main(String[] args) {
        int testTime = 100000;
        int maxSize = 100;
        int maxValue = 100;
        System.out.println("test begin");
        for (int i = 0; i < testTime; i++) {
            int[] arr = generateRandomArray(maxSize, maxValue);
            //System.out.println(Arrays.toString(arr));
            int w = (int) (Math.random() * (arr.length + 1));
            int[] ans1 = getMaxWindow(arr, w);
            int[] ans2 = right(arr, w);
            if (!isEqual(ans1, ans2)) {
                System.out.println(Arrays.toString(arr));
                System.out.println(w);
                System.out.println("Oops!");
                break;
            }
        }
        System.out.println("test finish");
    }
}
