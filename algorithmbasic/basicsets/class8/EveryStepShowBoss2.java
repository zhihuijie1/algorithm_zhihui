package algorithmbasic.basicsets.class8;
/*

暴力方法：自写
 */

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class EveryStepShowBoss2 {
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

    // 属性
    // 存储返回值
    List<List<Integer>> ans = new ArrayList<>();
    // 得奖区
    ArrayList<Customer> daddy = new ArrayList<>();
    // 等候区
    ArrayList<Customer> cands = new ArrayList<>();
    // 查找是否有这个顾客 --> (根据id找customer)
    HashMap<Integer, Customer> map = new HashMap<>();

    // 方法
    public List<List<Integer>> topK2(int[] arr, boolean[] op, int k) {
        for (int i = 0; i < arr.length; i++) {
            int id = arr[i];
            boolean buyOrRefund = op[i];
            // 没有这个顾客并且退货
            if (!map.containsKey(id) && !buyOrRefund) {
                ans.add(this.getCurAns());
                continue;
            }
            // 没有这个顾客 -> 买
            // 有这个顾客 -> 退
            // 有这个顾客 -> 买
            if (!map.containsKey(id)) {
                Customer c = new Customer(id, i, 1);
                map.put(id, c);
                // cands / daddy 中加
                if (daddy.size() < k) {
                    daddy.add(c);
                } else {
                    cands.add(c);
                }
                // 后面会涉及一个调整
            } else { // 有这个顾客 -> 退  有这个顾客 -> 买
                Customer c = map.get(id);
                if (buyOrRefund) {
                    c.buy++;
                } else {
                    c.buy--;
                }
                if (c.buy == 0) {
                    map.remove(id);
                    // 只会执行其中一个
                    cleanZeroBuy(cands);
                    cleanZeroBuy(daddy);
                }
            }
            // 调整
            cands.sort(new CandsComparator());
            daddy.sort(new DaddyComparator());
            // 是否要交换位置
            move(k, i);
            // 生成topK
            ans.add(getCurAns());
        }
        return ans;
    }

    // 将中奖区的topK放到 List<Integer> 中
    public List<Integer> getCurAns() {
        List<Integer> list = new ArrayList<>();
        for (Customer c : this.daddy) {
            list.add(c.id);
        }
        return list;
    }

    // 删除daddy/cands中购买数为0的顾客
    public void cleanZeroBuy(ArrayList<Customer> arr) {
        List<Customer> noZero = new ArrayList<Customer>();
        for (Customer c : arr) {
            if (c.buy != 0) {
                noZero.add(c);
            }
        }
        arr.clear();
        for (Customer c : noZero) {
            arr.add(c);
        }
    }

    // 等候区与中奖区是否要交换位置
    public void move(int k, int time) {
        if (cands.isEmpty()) {
            return;
        }
        if (daddy.size() < k) {
            Customer c = cands.get(0);
            c.enterTime = time;
            cands.remove(0);
            daddy.add(c);
        } else if (cands.get(0).buy > daddy.get(0).buy) {
            Customer c1 = cands.get(0);
            c1.enterTime = time;
            Customer c2 = daddy.get(0);
            c2.enterTime = time;
            cands.remove(0);
            daddy.remove(0);
            daddy.add(c1);
            cands.add(c2);
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
}
