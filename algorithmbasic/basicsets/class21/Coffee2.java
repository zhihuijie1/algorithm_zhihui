package algorithmbasic.basicsets.class21;

import javax.naming.ldap.LdapReferralException;
import java.util.Comparator;
import java.util.PriorityQueue;

public class Coffee2 {
    //买咖啡时间最短
    //洗干净时间最短

    private static class Info {
        public int start;
        public int waiting;

        public Info(int start, int waiting) {
            this.start = start;
            this.waiting = waiting;
        }
    }

    public static int minTime1(int[] arr, int n, int a, int b) {
        if (arr == null || arr.length == 0 || n == 0) {
            return 0;
        }
        //买咖啡的时间最短。-- 最终收尾时间最短
        //(0,1) (0,3) (0,7) -- (开始时刻，制作时间) 之和->结束时刻
        //频发进行插入与提取，并需要有序 -> 小根堆
        PriorityQueue<Info> heap = new PriorityQueue<>(new Comparator<Info>() {
            @Override
            public int compare(Info o1, Info o2) {
                //(0,1) (0,3) (0,7) -- (开始时刻，制作时间) 之和->结束时刻
                //根据结束时刻进行排序。-- 结束时刻越早的越先被安排
                return (o1.start + o1.waiting) - (o2.start + o2.waiting);
            }
        });

        for (int i = 0; i < arr.length; i++) {
            heap.add(new Info(0, arr[i]));
        }
        //drinks数组 -- 计算洗干净最短时间，需要知到谁先开始洗，什么时候洗。
        int[] drinks = new int[n];
        for (int i = 0; i < n; i++) {
            Info info = heap.poll();
            info.start += info.waiting;
            heap.add(info);
            drinks[i] = info.start;
        }
        //drinks数组中存放着最优的 开始洗时刻。

        //计算洗干净最短时间。
        return washTime(drinks, a, b, 0, 0);
    }

    public static int washTime(int[] drinks, int wash, int air, int index, int free) { //free -- 刷碗机可以工作时刻
        if (index == drinks.length) {
            return 0;
        }
        //index位置选择洗
        //Math.max(drinks[index],free) -- index位置开始洗时刻
        //time1 -- index位置洗完时刻 / 刷碗机可以工作时刻
        int time1 = Math.max(drinks[index], free) + wash;
        //time2 -- 以后全洗净最短时间。
        int time2 = washTime(drinks, wash, air, index + 1, time1);
        //Math.max(time1, time2) -- 不是加和而是取最大值 -- 以后如果不使用刷碗机而是单纯挥发 可能存在以后洗净时间更短的情况。
        //由于计算的是全洗净的时间所以要取最大值。
        int time3 = Math.max(time1, time2);


        //index位置选择挥发
        //t1 -- index位置洗净时间
        //t2 -- 以后全洗净最短时间。
        int t1 = drinks[index] + air;
        int t2 = washTime(drinks, wash, air, index + 1, free);
        //以后洗净时间可能比当前时间短
        int t3 = Math.max(t1, t2);

        return Math.min(time3, t3);
    }
}