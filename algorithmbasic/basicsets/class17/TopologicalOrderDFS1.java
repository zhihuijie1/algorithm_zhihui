package algorithmbasic.basicsets.class17;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

public class TopologicalOrderDFS1 {

    /**
     * 节点内部类
     */

    public static class DirectedGraphNode {
        public int label;
        public ArrayList<DirectedGraphNode> neighbors;

        public DirectedGraphNode(int x) {
            label = x;
            neighbors = new ArrayList<DirectedGraphNode>();
        }
    }

    /**
     * 深度内部类。
     */

    public static class Deep {
        public long deep;
        public DirectedGraphNode node;

        public Deep(long deep, DirectedGraphNode node) {
            this.deep = deep;
            this.node = node;
        }
    }

    /**
     * topSort方法
     */

    public static ArrayList<DirectedGraphNode> topSort(ArrayList<DirectedGraphNode> graph) {
        HashMap<DirectedGraphNode, Deep> order = new HashMap<>();
        ArrayList<Deep> deeps = new ArrayList<>();
        ArrayList<DirectedGraphNode> dNodes = new ArrayList<>();

        for (DirectedGraphNode node : graph) {
            Deep d = process(node, order);
            deeps.add(d);
        }
        deeps.sort(new MyComparator());
        for (Deep d : deeps) {
            dNodes.add(d.node);
        }
        return dNodes;
    }


    public static Deep process(DirectedGraphNode node, HashMap<DirectedGraphNode, Deep> order) {
        if (order.containsKey(node)) {
            return order.get(node);
        }
        long max = Long.MIN_VALUE;
        for (DirectedGraphNode n : node.neighbors) {
            Deep d = process(n, order);
            max = Math.max(max, d.deep);
        }
        Deep deep = new Deep(max + 1, node);
        order.put(node, deep);
        return deep;
    }

    public static class MyComparator implements Comparator<Deep> {
        @Override
        public int compare(Deep o1, Deep o2) {
            return o1.deep == o2.deep ? 0 : (o1.deep > o2.deep ? -1 : 1);
        }
    }
}
