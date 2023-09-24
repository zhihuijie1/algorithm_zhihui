package algorithmbasic.basicsets.class17;
//图的拓扑排序方法二

import java.util.*;

// OJ链接：https://www.lintcode.com/problem/topological-sorting
public class TopologicalOrderDFS2 {
    /**
     * 节点内部类
     */
    // 不要提交这个类
    public static class DirectedGraphNode {
        public int label;
        public ArrayList<DirectedGraphNode> neighbors;

        public DirectedGraphNode(int x) {
            label = x;
            neighbors = new ArrayList<DirectedGraphNode>();
        }
    }

    /**
     * topSort方法
     * 传入一张图的所有节点，返回排序好后的所有节点。
     */

    public static ArrayList<DirectedGraphNode> topSort(ArrayList<DirectedGraphNode> graph) {
        //采用计算每个节点点次的方法。
        //建立一张表用来记录每个节点对应的点次是多少。
        HashMap<DirectedGraphNode, Record> order = new HashMap<>();
        ArrayList<Record> records = new ArrayList<>();
        ArrayList<DirectedGraphNode> dnodes = new ArrayList<>();

        //遍历图中每个节点，并记录每个节点出现的点次。
        for (DirectedGraphNode cur : graph) {
            Record record = process(cur, order);
            records.add(record);
            //order.put(cur, record);
        }
        //Arrays.sort(records,new MyComparator());
        records.sort(new MyComparator());
        for (Record r : records) {
            dnodes.add(r.node);
        }
        return dnodes;
    }

    /**
     * 比较器
     */
    public static class MyComparator implements Comparator<Record> {
        @Override
        public int compare(Record o1, Record o2) {
            //不要将long类型数据强制转换成int类型，会出现溢出和截断的风险，导致数据出现错误。
            //例如2147483648L -> int:-2147483648
            //它超过了int类型的最大值 2147483647。当将其强制转换为int类型时，结果被截断为int类型的最小值 -2147483648。
            //return (int)(o2.nodes - o1.nodes);
            return o1.nodes == o2.nodes ? 0 : (o1.nodes > o2.nodes ? -1 : 1);
        }
    }

    /**
     * 点次内部类。
     */

    //记录某个节点的点次。
    public static class Record {
        public DirectedGraphNode node;
        //点次。
        public long nodes;

        public Record(DirectedGraphNode node, long nodes) {
            this.node = node;
            this.nodes = nodes;
        }
    }

    /**
     * 求点次的方法。
     */

    //传入一个节点返回这个节点的点次。在递归的过程当中每个节点的点次其实都已经记录好了。
    public static Record process(DirectedGraphNode node, HashMap<DirectedGraphNode, Record> order) {
        if (order.containsKey(node)) {
            return order.get(node);
        }
        //order中没有该点。
        long count = 0;
        for (DirectedGraphNode n : node.neighbors) {
            Record r = process(n, order);
            count += r.nodes;
        }
        Record record = new Record(node, count + 1);
        //先把当前节点及其点次放入map中然后再返回。
        order.put(node, record);
        return record;
    }
}