package algorithmbasic.class22;

/**
 * arr是面值数组，其中的值都是正数且没有重复。再给定一个正数aim。
 * 每个值都认为是一种面值，且认为张数是无限的。
 * 返回组成aim的方法数
 * 例如：arr = {1,2}，aim = 4
 * 方法如下：1+1+1+1、1+1+2、2+2
 * 一共就3种方法，所以返回3
 */
public class CoinsWayNoLimit {
    /**
     * 暴力方法
     */
    public static int coinsWay(int[] arr, int aim) {
        if (arr == null || arr.length == 0 || aim <= 0) {
            return 0;
        }
        return process(arr, aim, 0);
    }

    //返回的是满足目标的方法数
    public static int process(int[] arr, int aim, int index) {
        if(index == arr.length) {
            return aim == 0 ? 1 : 0;
        }
        int ways = 0;
        for(int amount = 0; aim - amount * arr[index] > 0 ; amount++ ) {
            ways += process(arr,aim - amount * arr[index],index + 1);
        }
        return ways;
    }
}
