package algorithmbasic.basicsets.class17;

import java.util.*;

//图的拓扑排序
public class TopologySort {
    public static List<Node> sortedTopology(Graph graph) {
        //result用来盛放排序结果。
        List<Node> result = new ArrayList<>();
        //inmap存放节点与入度的对应值。
        //key ——> 某个节点， value ——> 剩余入度
        HashMap<Node, Integer> inmap = new HashMap<>();
        //只有节点的入度为0才可以进入此队列。
        Queue<Node> inZeroQueue = new LinkedList<>();

        for (Node node : graph.nodes.values()) {
            inmap.put(node, node.in);
            if (node.in == 0) {
                inZeroQueue.add(node);
            }
        }

        Node cur = inZeroQueue.poll();
        result.add(cur);
        for (Node next : cur.nexts) {
            //剩余入度减1.
            inmap.put(next, inmap.get(next) - 1);
            if (inmap.get(next) == 0) {
                inZeroQueue.add(next);
            }
        }
        return result;
    }


    public static void main(String[] args) {
        Node a = new Node(1);
        Node b = new Node(2);
        Node c = new Node(3);
        Node d = new Node(4);
        Node e = new Node(5);
        Node f = new Node(6);
        a.nexts.add(c);
        b.nexts.add(c);
        b.nexts.add(d);
        c.nexts.add(d);
        d.nexts.add(e);
        d.nexts.add(f);
        e.nexts.add(f);


        Graph graph = new Graph();
        graph.nodes.put(1, a);
        graph.nodes.put(2, b);
        graph.nodes.put(3, c);
        graph.nodes.put(4, d);
        graph.nodes.put(5, e);
        graph.nodes.put(6, f);

        List<Node> list = sortedTopology(graph);
        for (Node n : list) {
            System.out.print(n.value + " ");
        }
    }
}
