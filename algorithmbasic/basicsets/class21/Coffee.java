package algorithmbasic.basicsets.class21;

import java.util.Arrays;
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
        int curAir = drinks[index] + air;
        int restAir = bestTime(drinks, wash, air, index + 1, free);
        int p2 = Math.max(curAir, restAir);

        return Math.min(p1, p2);
    }


    /**
     * dp方法
     */
    public static int minTime2(int[] arr, int n, int a, int b) {
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
        return bestTimeDp(drinks, a, b);
    }

    //drinks -- 开始刷碗时间的顺序数组
    //wash -- 刷一个碗的时间
    //air -- 挥发的时间
    public static int bestTimeDp(int[] drinks, int wash, int air) {
        int N = drinks.length;
        //变化的参数主要是： index, free -- 洗的机器什么时候空闲
        //确定变化范围 index -- [0 , N]    free -- [0 , maxFree]
        //确定maxFree值
        int maxFree = 0;
        for (int i = 0; i < N; i++) {
            maxFree = Math.max(drinks[i], maxFree) + wash;
        }

        int[][] dp = new int[N + 1][maxFree + 1];
        //根据暴力递归分析得：应该从下往上构建
        for (int index = N - 1; index >= 0; index--) {
            for (int free = 0; free <= maxFree; free++) {
                // index号杯子 决定洗
                int curWash = Math.max(free, drinks[index]) + wash;
                if (curWash > maxFree) { //如果中间过程中出现了大于maxFree的情况一定是错误的。以后的也都是错误的直接跳过
                    break;
                }
                //int restWash = dp[index+1][curWash]可能出现越界，因为free趋于maxFree时，加wash会越界。
                int restWash = dp[index + 1][curWash];
                int p1 = Math.max(curWash, restWash);

                // index号杯子 决定挥发
                int curAir = drinks[index] + air;
                int restAir = dp[index + 1][free];
                int p2 = Math.max(curAir, restAir);

                dp[index][free] = Math.min(p1, p2);
            }
        }
        return dp[0][0];
    }


    // for test
    public static int[] randomArray(int len, int max) {
        int[] arr = new int[len];
        for (int i = 0; i < len; i++) {
            arr[i] = (int) (Math.random() * max) + 1;
        }
        return arr;
    }

    // for test
    public static void printArray(int[] arr) {
        System.out.print("arr : ");
        for (int j = 0; j < arr.length; j++) {
            System.out.print(arr[j] + ", ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        int len = 10;
        int max = 10;
        int testTime = 5;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            int[] arr = randomArray(len, max);
            int n = (int) (Math.random() * 7) + 1;
            int a = (int) (Math.random() * 7) + 1;
            int b = (int) (Math.random() * 10) + 1;
            int ans1 = right(arr, n, a, b);
            int ans2 = minTime1(arr, n, a, b);
            int ans3 = minTime2(arr, n, a, b);
            if (ans1 != ans2 || ans2 != ans3) {
                printArray(arr);
                System.out.println("n : " + n);
                System.out.println("a : " + a);
                System.out.println("b : " + b);
                System.out.println(ans1 + " , " + ans2 + " , " + ans3);
                System.out.println("===============");
                break;
            }
        }
        System.out.println("测试结束");

    }



    public static int right(int[] arr, int n, int a, int b) {
        int[] times = new int[arr.length];
        int[] drink = new int[n];
        return forceMake(arr, times, 0, drink, n, a, b);
    }

    // 每个人暴力尝试用每一个咖啡机给自己做咖啡
    public static int forceMake(int[] arr, int[] times, int kth, int[] drink, int n, int a, int b) {
        if (kth == n) {
            int[] drinkSorted = Arrays.copyOf(drink, kth);
            Arrays.sort(drinkSorted);
            return forceWash(drinkSorted, a, b, 0, 0, 0);
        }
        int time = Integer.MAX_VALUE;
        for (int i = 0; i < arr.length; i++) {
            int work = arr[i];
            int pre = times[i];
            drink[kth] = pre + work;
            times[i] = pre + work;
            time = Math.min(time, forceMake(arr, times, kth + 1, drink, n, a, b));
            drink[kth] = 0;
            times[i] = pre;
        }
        return time;
    }

    public static int forceWash(int[] drinks, int a, int b, int index, int washLine, int time) {
        if (index == drinks.length) {
            return time;
        }
        // 选择一：当前index号咖啡杯，选择用洗咖啡机刷干净
        int wash = Math.max(drinks[index], washLine) + a;
        int ans1 = forceWash(drinks, a, b, index + 1, wash, Math.max(wash, time));

        // 选择二：当前index号咖啡杯，选择自然挥发
        int dry = drinks[index] + b;
        int ans2 = forceWash(drinks, a, b, index + 1, washLine, Math.max(dry, time));
        return Math.min(ans1, ans2);
    }
}
