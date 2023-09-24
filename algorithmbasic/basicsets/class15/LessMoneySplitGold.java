package algorithmbasic.basicsets.class15;

import java.util.*;

/*
一块金条切成两半，是需要花费和长度数值一样的铜板
比如长度为20的金条，不管怎么切都要花费20个铜板，一群人想整分整块金条，怎么分最省铜板?
例如，给定数组{10,20,30}，代表一共三个人，整块金条长度为60，金条要分成10，20，30三个部分。
如果先把长度60的金条分成10和50，花费60；再把长度50的金条分成20和30，花费50；一共花费110铜板
但如果先把长度60的金条分成30和30，花费60；再把长度30金条分成10和20，花费30；一共花费90铜板
输入一个数组，返回分割的最小代价.
 */
public class LessMoneySplitGold {

    //暴力方法
    public static int lessMoney1(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        return process(arr, 0);
    }

    //构造一个函数：
    //等待合并的数都放在arr数组里
    //之前合并产生的总代价是pre
    //返回值的意义是：返回整体最小的
    //返回值的意义是：返回当前数组arr最小花费
    public static int process(int[] arr, int pre) {
        if (arr.length == 1) {// 说明都合并完了。
            return 0;
        }
        int ans = Integer.MAX_VALUE;
        for (int i = 0; i < arr.length; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                //process(copyAndMergeTwo(arr, i, j), pre + arr[i] + arr[j]):会返回一个整体的最小值。
                //这个整体的最小值会因为i,j的选择不同从而值不同。因此我们要选出一个最小的。
                ans = Math.min(ans, arr[i] + arr[j] + process(copyAndMergeTwo(arr, i, j), pre + arr[i] + arr[j]));
            }
        }
        return ans;
    }

    /**
     * @copyAndMergeTwo 传进一个数组以及这个数组上的两个指针，删除两个指针指向的内容，添加两指针指向的内容之和，
     * 然后再将剩下的内容拷贝一份返回一个新数组。
     */
    public static int[] copyAndMergeTwo(int[] arr, int i, int j) {
        int[] ans = new int[arr.length - 1];
        int index = 0;
        for (int k = 0; k < arr.length; k++) {
            if (k != i && k != j) {
                ans[index++] = arr[k];
            }
        }
        ans[index] = arr[i] + arr[j];
        return ans;
    }

    //贪心方法
    public static int lessMoney2(int[] arr) {
        //1:创建一个小根堆
        PriorityQueue<Integer> heap = new PriorityQueue<>();
        //2：入堆
        for (int i = 0; i < arr.length; i++) {
            heap.add(arr[i]);
        }
        //3:一次弹出两个，进行相加，得到的结果进行标注一下，然后再存放在堆中。直到堆弹空循环才停止。
        int count = 0;
        while (heap.size() > 1) {
            int cur = heap.poll() + heap.poll();
            count += cur;
            heap.add(cur);
        }
        //4:对特殊标注进行相加
        return count;
    }


    public static int lessMoney3(int[] arr) {
        PriorityQueue<Integer> pQ = new PriorityQueue<>();
        for (int i = 0; i < arr.length; i++) {
            pQ.add(arr[i]);
        }
        int sum = 0;
        int cur = 0;
        while (pQ.size() > 1) {
            cur = pQ.poll() + pQ.poll();
            sum += cur;
            pQ.add(cur);
        }
        return sum;
    }


    // ---------------------- for test ------------------------
    public static int[] generateRandomArray(int maxSize, int maxValue) {
        int[] arr = new int[(int) ((maxSize + 1) * Math.random())];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) (Math.random() * (maxValue + 1));
        }
        return arr;
    }

    public static void main(String[] args) {
        int testTime = 100000;
        int maxSize = 6;
        int maxValue = 1000;
        for (int i = 0; i < testTime; i++) {
            int[] arr = generateRandomArray(maxSize, maxValue);
            if (lessMoney2(arr) != lessMoney1(arr)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("finish!");
    }
}