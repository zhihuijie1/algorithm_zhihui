package algorithmbasic.basicsets.class15;

import java.util.Comparator;
import java.util.PriorityQueue;

public class IPO {
    // 最多K个项目
    // W是初始资金
    // Profits[] Capital[] 一定等长
    // 返回最终最大的资金

    public static int findMaximizedCapital(int K, int W, int[] Profits, int[] Capital) {
        //小根堆(根据成本排序)
        PriorityQueue<Program> minCostQ = new PriorityQueue<>(new MinCostComparator());
        //大根堆(根据利润排序)
        PriorityQueue<Program> maxProfitQ = new PriorityQueue<>(new MaxProfitComparator());
        //将利润与成本封装成一个一个的项目。
        for (int i = 0; i < Profits.length; i++) {
            minCostQ.add(new Program(Profits[i], Capital[i]));
        }
        for (int i = 0; i < K; i++) {
            while (!minCostQ.isEmpty() && minCostQ.peek().Capital < W) { //!minCostQ.isEmpty()别忘了写
                maxProfitQ.add(minCostQ.poll());
            }
            if(maxProfitQ.isEmpty()) {
                return W;
            }
            W += maxProfitQ.poll().Profit;//小心空指针异常。
        }
        return W;
    }

    public static class Program {
        int Profit;//利润
        int Capital;//成本

        public Program(int profit, int capital) {
            Profit = profit;
            Capital = capital;
        }
    }

    public static class MinCostComparator implements Comparator<Program> {
        @Override
        public int compare(Program o1, Program o2) {
            return o1.Capital - o2.Capital;
        }
    }

    public static class MaxProfitComparator implements Comparator<Program> {
        @Override
        public int compare(Program o1, Program o2) {
            return o2.Profit - o1.Profit;
        }
    }
}