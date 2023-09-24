package algorithmbasic.basicsets.class8;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

// 用加强堆实现
public class EveryStepShowBoss3 {
    // 顾客内部类
    public static class Customer {
        public int id;
        public int enterTime;
        public int buy;

        public Customer(int id, int enterTime, int buy) {
            this.id = id;
            this.enterTime = enterTime;
            this.buy = buy;
        }
    }

    // 比较器
    // 中奖区的比较器  ->  最烂的在前面
    public class DaddyComparator implements Comparator<Customer> {
        @Override
        public int compare(Customer o1, Customer o2) {
            return o1.buy != o2.buy ? (o1.buy - o2.buy) : (o1.enterTime - o2.enterTime);
        }
    }

    // 等待区的比较器  ->  最好的在前面
    public class CandsComparator implements Comparator<Customer> {
        @Override
        public int compare(Customer o1, Customer o2) {
            return o1.buy != o2.buy ? (o2.buy - o1.buy) : (o1.enterTime - o2.enterTime);
        }
    }

    // 属性
    // 查找是否有这个顾客 --> (根据id找customer)
    HashMap<Integer, Customer> map;
    // 得奖区
    HeapGreater<Customer> daddy;
    // 等候区
    HeapGreater<Customer> cands;
    // 中奖区最多容纳人数
    final int daddyLimit;


    // 构造器
    public EveryStepShowBoss3(int daddyLimit) {
        this.daddyLimit = daddyLimit;
        map = new HashMap<>();
        daddy = new HeapGreater<>(new DaddyComparator());
        cands = new HeapGreater<>(new CandsComparator());
    }

    // 方法
    public List<List<Integer>> topK(int[] arr, boolean[] op, int k) {
        // 返回的结果存放区
        List<List<Integer>> ans = new ArrayList<>();
        EveryStepShowBoss3 everyStepShowBoss3 = new EveryStepShowBoss3(k);
        for (int i = 0; i < arr.length; i++) {
            operate(i, arr[i], op[i], ans);
        }
        return ans;
    }

    public void operate(int time, int id, boolean buyOrRefund, List<List<Integer>> ans) {
        // 没有这个顾客并且退货
        if (!map.containsKey(id) && !buyOrRefund) {
            ans.add(this.getCurAns());
            return;
        }
        // 没有这个顾客 -> 买
        // 有这个顾客 -> 退
        // 有这个顾客 -> 买
        if (!map.containsKey(id)) {
            Customer c = new Customer(id, time, 1);
            map.put(id, c);
            if (daddy.size() < daddyLimit) {
                // 会自动调整
                daddy.push(c);
            } else {
                cands.push(c);
            }
        } else {
            Customer c = map.get(id);
            if (buyOrRefund) {
                c.buy++;
            } else {
                c.buy--;
            }
            if (cands.contains(c)) {
                if (c.buy == 0) {
                    cands.remove(c);
                    map.remove(id);
                } else {
                    // 调整一下
                    cands.resign(c);
                }
            } else {
                if (c.buy == 0) {
                    daddy.remove(c);
                    map.remove(id);
                } else {
                    // 调整一下
                    daddy.resign(c);
                }
            }
        }
        // 是否要交换位置
        move(time);
        // 生成topK
        ans.add(this.getCurAns());
    }

    public void move(int time) {
        if (cands.isEmpty()) {
            return;
        }
        if (daddy.size() < daddyLimit) {
            Customer c = cands.pop();
            c.enterTime = time;
            daddy.push(c);
        } else {
            if (cands.peek().buy > daddy.peek().buy) {
                Customer c1 = cands.pop();
                c1.enterTime = time;
                Customer c2 = daddy.pop();
                c2.enterTime = time;
                cands.push(c2);
                daddy.push(c1);
            }
        }
    }

    // 将中奖区的topK放到 List<Integer> 中
    public List<Integer> getCurAns() {
        List<Customer> c = daddy.getAllElements();
        List<Integer> list = new ArrayList<>();
        for (Customer cus : c) {
            list.add(cus.id);
        }
        return list;
    }
}
