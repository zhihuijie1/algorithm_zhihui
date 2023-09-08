package algorithmbasic.class25;

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
        if(arr == null || arr.length <= 0 || w < 0) {
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
            while(!list.isEmpty() && list.peekLast() <= arr[R]) { //peelLast --> 双端队列最后一个节点
                list.pollLast();
            }
            //尾插下标
            list.addLast(R);

            //弹出窗口头部节点,该弹出的节点 -> R - W
            while(list.peekFirst() <= R - w) {
                list.pollFirst();
            }
            //存放最大值
            res[index++] = list.peekFirst();
        }
        return res;
    }




    //4,3,5,4,3,3,6,7
    //0,1,2,3,4,5,6,7
    //w=3
    //R=6


}
