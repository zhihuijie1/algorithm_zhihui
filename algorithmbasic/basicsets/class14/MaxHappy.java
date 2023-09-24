package algorithmbasic.basicsets.class14;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/*
派对的最大快乐值
 员工信息的定义如下:
class Employee {
    public int happy; // 这名员工可以带来的快乐值
    List<Employee> subordinates; // 这名员工有哪些直接下级
}
公司的每个员工都符合 Employee 类的描述。整个公司的人员结构可以看作是一棵标准的、 没有环的多叉树
树的头节点是公司唯一的老板，除老板之外的每个员工都有唯一的直接上级
叶节点是没有任何下属的基层员工(subordinates列表为空)，除基层员工外每个员工都有一个或多个直接下级
这个公司现在要办party，你可以决定哪些员工来，哪些员工不来，规则：
1.如果某个员工来了，那么这个员工的所有直接下级都不能来
2.派对的整体快乐值是所有到场员工快乐值的累加
3.你的目标是让派对的整体快乐值尽量大
给定一棵多叉树的头节点boss，请返回派对的最大快乐值。
 */
public class MaxHappy {

    public static class Employee {
        public int happy;
        public List<Employee> nexts;

        public Employee(int h) {
            happy = h;
            nexts = new ArrayList<>();
        }
    }

    public static int maxHappy1(Employee boss) {
        if (boss == null) {
            return 0;
        }
        return process1(boss, false);
    }

    public static int process1(Employee cur, boolean up) {
        if (up) { // 如果cur的上级来的话，cur没得选，只能不来
            int ans = 0;
            for (Employee next : cur.nexts) {
                ans += process1(next, false);
            }
            return ans;
        } else { // 如果cur的上级不来的话，cur可以选，可以来也可以不来
            int p1 = cur.happy;
            int p2 = 0;
            for (Employee next : cur.nexts) {
                p1 += process1(next, true);
                p2 += process1(next, false);
            }
            return Math.max(p1, p2);
        }
    }

    public static class Info {
        int noMax;
        int yesMax;

        public Info(int noMax, int yesMax) {
            this.noMax = noMax;
            this.yesMax = yesMax;
        }
    }

    public static int maxHappy2(Employee head) {
        Info info = process(head);
        return Math.max(info.noMax, info.yesMax);
    }

    public static Info process(Employee head) {
        if (head == null) {
            return new Info(0, 0);
        }
        int noMax = 0;
        int yesMax = head.happy;
        for (Employee e : head.nexts) {
            Info info = process(e);
            yesMax += info.noMax;
            noMax += Math.max(info.noMax, info.yesMax);
        }
        return new Info(noMax, yesMax);
    }

    //------------------------------- for test ----------------------------------------
    // 随机生成一个多叉树
    public static void main(String[] args) {
        int testTime = 10000;
        int maxLevel = 4; // 最大层数
        int maxNodeNumbers = 7; // 最大孩子数量
        int maxHappy = 100;
        for (int i = 0; i < testTime; i++) {
            Employee E = genarateBoss(maxLevel, maxNodeNumbers, maxHappy);
            if (maxHappy1(E) != maxHappy2(E)) {
                System.out.println("oops");
            }
        }
        System.out.println("Finish");
    }

    public static Employee genarateBoss(int maxLevel, int maxNodeNumbers, int maxHappy) {
        if (Math.random() < 0.03) {
            return null;
        }
        // 确定好层数与头节点。
        int LevelMax = (int) (Math.random() * maxLevel) + 1;
        Employee boss = process(1, maxLevel, maxNodeNumbers, maxHappy);
        return boss;
    }

    //Process : 给你一个boss节点，把这棵多叉树给弄好。
    public static Employee process(int curLevel, int maxLevel, int maxNodeNumbers, int maxHappy) {
        if (curLevel > maxLevel) {
            return new Employee(0);
        }
        List<Employee> nexts = new LinkedList<>();
        int happy = (int) (Math.random() * maxHappy) + 1;
        int nextSize = (int) (Math.random() * maxNodeNumbers) + 1;
        for (int i = 0; i < nextSize; i++) {
            nexts.add(process(curLevel + 1, maxLevel, maxNodeNumbers, maxHappy));
        }
        Employee employee = new Employee(happy);
        employee.nexts = nexts;
        return employee;
    }
}