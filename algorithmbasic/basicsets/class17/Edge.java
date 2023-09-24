package algorithmbasic.basicsets.class17;

public class Edge {
    /**
     * 属性
     */
    //权重
    public int weight;
    //起始节点
    public Node from;
    //终止节点
    public Node to;

    /**
     * 构造器
     */
    public Edge(int weight, Node from, Node to) {
        this.weight = weight;
        this.from = from;
        this.to = to;
    }
}
