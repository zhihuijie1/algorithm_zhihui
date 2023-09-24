package algorithmbasic.basicsets.class15;

import java.util.Arrays;
import java.util.Comparator;

/*
一些项目要占用一个会议室宣讲，会议室不能同时容纳两个项目的宣讲，给你每一个项目开始的时间和结束的时间
你来安排宣讲的日程，要求会议室进行的宣讲的场次最多，返回最多的宣讲场次
 */
public class BestArrange {

    public static class Program {
        public int start;
        public int end;

        public Program(int start, int end) {
            this.start = start;
            this.end = end;
        }
    }

    //暴力解法
    public static int bestArrange1(Program[] programs) {
        if (programs == null || programs.length == 0) {
            return 0;
        }
        return process(programs, 0, 0);
    }
    // 设计一个函数：
    // 还剩下的未被安排的会议放在programs里
    // 现在来到的时间结点是：timeEnd
    // 已经安排好的会议数量是：done
    // 函数process返回的是：剩下的未被安排的会议programs里面宣传的最多的场次。

    public static int process(Program[] programs, int timeEnd, int done) {
        if (programs.length == 0) {
            return done;
        }
        int max = done;
        for (int i = 0; i < programs.length; i++) {
            if (programs[i].start >= timeEnd) {
                /*
                done++;
                timeEnd = programs[i].end;
                Program[] p = copyButExcept(programs, i); //剩下的未被安排的会议少了一个。
                 */
                int res = process(copyButExcept(programs, i), programs[i].end, done+1);// 剩下的未被安排的会议programs里面宣传的最多的场次
                max = Math.max(max, res);
            }
        }
        return max;
    }

    public static Program[] copyButExcept(Program[] programs, int i) {
        Program[] res = new Program[programs.length - 1];
        int index = 0;
        for (int j = 0; j < programs.length; j++) {
            if (j != i) {
                res[index++] = programs[j];
            }
        }
        return res;
    }

    // 贪心方法
    // 会议的开始时间和结束时间，都是数值，不会 < 0
    public static int bestArrange2(Program[] programs) {
        //1:根据end由小到大排序
        Arrays.sort(programs, new MyComparator());
        //2：遍历计数
        int count = 0;
        int arrangedEnd = 0;
        for (int i = 0; i < programs.length; i++) {
            if (programs[i].start >= arrangedEnd) {
                count++;
                arrangedEnd = programs[i].end;
            }
        }
        //3：返回计数结果
        return count;
    }

    private static class MyComparator implements Comparator<Program> {
        @Override
        public int compare(Program o1, Program o2) {
            return o1.end - o2.end;
        }
    }


    // ---------------------- for test ------------------------
    public static Program[] generatePrograms(int programSize, int timeMax) {
        Program[] ans = new Program[(int) (Math.random() * (programSize + 1))];
        for (int i = 0; i < ans.length; i++) {
            int r1 = (int) (Math.random() * (timeMax + 1));
            int r2 = (int) (Math.random() * (timeMax + 1));
            if (r1 == r2) {
                ans[i] = new Program(r1, r1 + 1);
            } else {
                ans[i] = new Program(Math.min(r1, r2), Math.max(r1, r2));
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        int programSize = 12;
        int timeMax = 20;
        int timeTimes = 100000;
        for (int i = 0; i < timeTimes; i++) {
            Program[] programs = generatePrograms(programSize, timeMax);
            int a = bestArrange1(programs);
            int b = bestArrange2(programs);
            if (b != a) {
                System.out.println("Oops!");
                System.out.println(a);
                System.out.println(b);
                break;
            }
        }
        System.out.println("finish!");
    }
}