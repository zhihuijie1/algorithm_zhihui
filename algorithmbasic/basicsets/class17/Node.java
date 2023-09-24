package algorithmbasic.basicsets.class17;

import java.util.ArrayList;

//点结构的描述
public class Node {
    /**
     * 属性
     */
    //该点的数值大小
    public int value;
    //有几个边直接进入该点
    public int in;
    //有几个边直接从该点出去
    public int out;
    //该点的邻居节点是谁
    public ArrayList<Node> nexts;
    //该点直接出去的边是谁
    public ArrayList<Edge> edges;

    /**
     * 构造器
     */
    public Node(int value) {
        this.value = value;
        this.in = 0;
        this.out = 0;
        this.nexts = new ArrayList<>();
        this.edges = new ArrayList<>();
    }
}
