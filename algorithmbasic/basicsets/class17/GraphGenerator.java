package algorithmbasic.basicsets.class17;

//接口转化器
//将一些特殊结构图的表示方法转化成我们熟悉的图的表示方法。
public class GraphGenerator {
    // matrix 所有的边。
    // N*3 的矩阵。
    // [weight, from节点上面的值，to节点上面的值]。
    //
    // [ 5 , 0 , 7] -> 0号节点指向7号节点，边的权重大小是5。
    // [ 3 , 0,  1] -> 0号节点指向1号节点，边的权重大小是3。
    //
    public static Graph createGraph(int[][] matrix) {
        Graph graph = new Graph();
        //遍历每一条边。
        for (int i = 0; i < matrix.length; i++) {
            //起始值
            int fromValue = matrix[i][1];
            //终点值
            int toValue = matrix[i][2];
            //边的权重
            int edgeWeight = matrix[i][0];
            //如果没有一个节点的值是fromValue，那就创建这个节点。
            if (!graph.nodes.containsKey(fromValue)) {
                graph.nodes.put(fromValue, new Node(fromValue));
            }
            if (!graph.nodes.containsKey(toValue)) {
                graph.nodes.put(toValue, new Node(toValue));
            }
            //拿到这条边的起始节点与终止接点。
            Node formNode = graph.nodes.get(fromValue);
            Node toNode = graph.nodes.get(toValue);
            //创建这条边。
            Edge edge = new Edge(edgeWeight, formNode, toNode);
            graph.edges.add(edge);

            //编辑formNode的in , out , nexts, edgs
            formNode.out++;
            formNode.nexts.add(toNode);
            formNode.edges.add(edge);

            //编辑toNode的in out nexts edges
            toNode.in++;
        }
        return graph;
    }
}
