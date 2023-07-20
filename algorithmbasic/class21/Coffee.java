package algorithmbasic.class21;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * 给定一个数组arr，arr[i]代表第i号咖啡机泡一杯咖啡的时间
 * 给定一个正数N，表示N个人等着咖啡机泡咖啡，每台咖啡机只能轮流泡咖啡
 * 只有一台咖啡机，一次只能洗一个杯子，时间耗费a，洗完才能洗下一杯
 * 每个咖啡杯也可以自己挥发干净，时间耗费b，咖啡杯可以并行挥发
 * 假设所有人拿到咖啡之后立刻喝干净，
 * 返回从开始等到所有咖啡机变干净的最短时间
 * 三个参数：int[] arr、int N，int a、int b
 */

public class Coffee {
    public static class Machine {
        private int startTime;
        private int runTime;

        public Machine(int startTime, int runTime) {
            this.startTime = startTime;
            this.runTime = runTime;
        }
    }

    public static class MyComparatoer implements Comparator<Machine> {
        @Override
        public int compare(Machine o1, Machine o2) {
            return (o1.startTime + o1.runTime) - (o2.startTime + o2.runTime);
        }
    }

    //arr[i] -- 第I号机器泡一杯咖啡所需要的时间。
    //n -- 有N个人
    //a -- 洗刷机洗1个杯子所需要的时间
    //b -- 挥发所需时间
    //返回从开始等到所有咖啡机变干净的最短时间
    public static int minTime1(int[] arr, int n, int a, int b) {
        PriorityQueue<Machine> heap = new PriorityQueue<>(new MyComparatoer());
        //初始化小根堆。
        for (int i = 0; i < arr.length; i++) {
            heap.add(new Machine(0, arr[i]));
        }
        int[] drinks = new int[n];//存储着每个顾客买完咖啡后的时间
        for (int i = 0; i < n; i++) {
            Machine machine = heap.poll();
            machine.startTime += machine.runTime;
            heap.add(machine);
            drinks[i] = machine.startTime;
        }
        //drinks一定是由低到高排序的。
        //drinks中存储着刷碗的时间。由低到高排序的。
        return bestTime(drinks, a, b, 0, 0);
    }


    //drinks：所有杯子可以开始洗的时间
    //wash：洗一个杯子的时间
    //air：挥发一个杯子的时间
    //index：当前轮到哪个杯子
    //free：洗的机器什么时间空闲
    //drinks[0 ... ... ] 到全部洗干净所用的最少时间
    public static int bestTime(int[] drinks, int wash, int air, int index, int free) {
        if (index == drinks.length) {
            return 0;
        }
        // index号杯子 决定洗
        //轮到当前杯子时，洗刷机可能还没轮到当前杯子，他还忙。 -- free = 10 drinks[index] = 8
        //轮到当前杯子时，洗刷机而可能空闲，但是当前杯子开始清洗的时间比较晚。 -- free = 7 drinks[index] = 9
        //所以取两者的最大值才是开始清洗的时间。
        int curWash = Math.max(free, drinks[index]) + wash;
        int restWash = bestTime(drinks, wash, air, index + 1, curWash);
        //curWash当前清洗的时间可能比restWash剩余清洗时间要长，也可能要短，但总之我要的是最终全部洗完的时间所以取最大值。
        int p1 = Math.max(curWash, restWash);

        // index号杯子 决定挥发
        int curAir = air;
        int restAir = bestTime(drinks, wash, air, index + 1, free);
        int p2 = Math.max(curAir, restAir);

        return Math.min(p1, p2);
    }
}
