package algorithmbasic.basicsets.class17;

import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

public class Prim {

    public static Set<Edge> primMST(Graph graph) {
        //盛放结果的容器。
        Set<Edge> result = new HashSet<>();
        //set容器用来判断当前节点是否已经被解锁。
        HashSet<Node> set = new HashSet<>();
        //小根堆用来放边。
        PriorityQueue<Edge> queue = new PriorityQueue<>(new MyComparator());

        for (Node node : graph.nodes.values()) {
            if (!set.contains(node)) {
                //解锁当前节点
                set.add(node);
                //解锁当前节点的直接相邻边。
                for (Edge edge : node.edges) {
                    queue.add(edge);
                }
                //当所有的节点都被解锁后，循环就就结束了。
                while (set.size() != graph.nodes.values().size()) {
                    //堆顶元素就是权重最小的边。
                    Edge smallEdge = queue.poll();
                    //找到该边连接的节点。
                    Node nextnode = smallEdge.to;
                    //如果这个节点没有被解锁过，就进行解锁，如果已经被解锁过了，就继续弹出下一条边。
                    if (!set.contains(nextnode)) {
                        result.add(smallEdge);
                        //解锁这个节点。
                        set.add(nextnode);
                        //解锁这个节点直接连接的边。
                        for (Edge edge : nextnode.edges) {
                            queue.add(edge);
                        }
                    }
                }
            }
            //注意这个break，如果没有森林，写不写break无所谓。如果有森林，写break
            break;
        }
        return result;
    }


    public static class MyComparator implements Comparator<Edge> {
        @Override
        public int compare(Edge o1, Edge o2) {
            //谁的权重小谁放前面。
            return o1.weight - o2.weight;
        }
    }
}
