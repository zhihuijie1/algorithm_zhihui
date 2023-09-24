package algorithmbasic.basicsets.class17;

import java.util.*;

//最小生成树算法
public class Kruskal {
    public static Set<Edge> kruskalMST(Graph graph) {
        //生成好我的并查集。
        UnionFind unionFind = new UnionFind();
        //图中的每个节点都是一个集合。
        unionFind.makeCollection(graph);
        //小根堆：根据边的权重比较。
        PriorityQueue<Edge> queue = new PriorityQueue<>(new myComparator());
        for (Edge e : graph.edges) {
            queue.add(e);
        }
        Set<Edge> set = new HashSet<>();
        while (!queue.isEmpty()) {
            Edge e = queue.poll();
            if (!unionFind.isSameSet(e.from, e.to)) {
                unionFind.union(e.from, e.to);
                set.add(e);
            }
        }
        return set;
    }

    public static class UnionFind {
        static HashMap<Node, Node> fatherMap = new HashMap<>();
        static HashMap<Node, Integer> sizeMap = new HashMap<>();
        static Stack<Node> stack = new Stack<>();

        public static void makeCollection(Graph graph) {
            fatherMap.clear();
            sizeMap.clear();
            for (Node node : graph.nodes.values()) {
                fatherMap.put(node, node);
                sizeMap.put(node, 1);
            }
        }

        public static boolean isSameSet(Node a, Node b) {
            return findAncestor(a) == findAncestor(b);
        }

        public static void union(Node a, Node b) {
            Node fatherA = findAncestor(a);
            Node fatherB = findAncestor(b);
            if (fatherA != fatherB) {
                Node big = sizeMap.get(fatherA) > sizeMap.get(fatherB) ? fatherA : fatherB;
                Node small = big == fatherA ? fatherB : fatherA;
                fatherMap.put(small, big);
                sizeMap.put(small, 0);
                sizeMap.put(big, sizeMap.get(big) + sizeMap.get(small));
            }
        }

        //出入一个节点，寻找这个节点的祖先节点
        public static Node findAncestor(Node node) {
            while (fatherMap.get(node) != node) {
                stack.add(node);
                node = fatherMap.get(node);
            }
            //fatherMap.get(node) == node
            //先进行优化
            while (!stack.isEmpty()) {
                Node cur = stack.pop();
                fatherMap.put(cur, node);
            }
            return node;
        }
    }

    public static class myComparator implements Comparator<Edge> {
        @Override
        public int compare(Edge o1, Edge o2) {
            return o1.weight - o2.weight;
        }
    }


    public static void main(String[] args) {
        Graph graph = new Graph();
        Node a = new Node(1);
        Node b = new Node(2);
        Node c = new Node(3);
        Node e = new Node(5);
        Node f = new Node(6);
        Node g = new Node(7);
        Node h = new Node(8);
        graph.nodes.put(1, a);
        graph.nodes.put(2, b);
        graph.nodes.put(3, c);
        graph.nodes.put(5, e);
        graph.nodes.put(6, f);
        graph.nodes.put(7, g);
        graph.nodes.put(8, h);
        Edge ac = new Edge(1, a, c);
        Edge bc = new Edge(1, b, c);
        Edge ab = new Edge(3, a, b);
        Edge be = new Edge(10, b, e);
        Edge ec = new Edge(12, e, c);
        Edge fc = new Edge(50, f, c);
        Edge eg = new Edge(1, g, e);
        Edge fg = new Edge(3, f, g);
        Edge fh = new Edge(6, f, h);
        Edge gh = new Edge(9, g, h);
        graph.edges.add(ac);
        graph.edges.add(bc);
        graph.edges.add(ab);
        graph.edges.add(be);
        graph.edges.add(ec);
        graph.edges.add(fc);
        graph.edges.add(eg);
        graph.edges.add(fg);
        graph.edges.add(fh);
        graph.edges.add(gh);
        Set<Edge> set = new HashSet<>();
        set = kruskalMST(graph);
        System.out.println(1);
    }
}
