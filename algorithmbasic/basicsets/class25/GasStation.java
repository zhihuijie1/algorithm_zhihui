package algorithmbasic.basicsets.class25;

import java.util.LinkedList;

/**
 * 加油站良好出发点问题
 */
public class GasStation {
    public static int canCompleteCircuit(int[] gas, int[] cost) {
        /*1：进行简化，简化的方法：gas - cost*/
        int[] arr = new int[gas.length];
        for (int i = 0; i < gas.length; i++) {
            arr[i] = gas[i] - cost[i];
        }
        /*2：构造累加和数组并进行扩充*/
        int[] res = new int[arr.length * 2 - 1];
        res[0] = arr[0];
        for (int i = 1; i < res.length; i++) {
            res[i] = res[i - 1] + arr[i % arr.length];
        }
        /*3：使用滑动窗口，检索窗口内最小值是否小于0*/
        return slidingWindowMinArray(res, arr.length);
    }

    public static int slidingWindowMinArray(int[] res, int Lend) {
        LinkedList<Integer> minWindow = new LinkedList<>();
        int R = 0;
        for (int L = 0; L < Lend; L++) {
            while (R < Lend + L) {
                while(!minWindow.isEmpty() && res[minWindow.peekLast()] >= res[R] ) {
                    minWindow.pollLast();
                }
                minWindow.addLast(R);
                R++;
            }
            if((L != 0 && res[minWindow.peekFirst()] - res[L - 1] >= 0) || (L == 0 && res[minWindow.peekFirst()] >= 0)) {
                return L;
            }
            while(!minWindow.isEmpty() && minWindow.peekFirst() <= L) {
                minWindow.pollFirst();
            }
        }
        return -1;
    }


    public static void main(String[] args) {
        int[] gas = {3,1,1};
        int[] cost = {1,2,2};
        int ans = canCompleteCircuit(gas, cost);
        System.out.println(ans); //0
    }
}
