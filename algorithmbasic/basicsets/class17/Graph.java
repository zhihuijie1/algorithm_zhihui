package algorithmbasic.basicsets.class17;

import java.util.HashMap;
import java.util.HashSet;

//图结构
public class Graph {
    /**
     * 属性
     */
    HashMap<Integer, Node> nodes;
    HashSet<Edge> edges;

    /**
     * 构造器
     */
    public Graph() {
        this.nodes = new HashMap<>();
        this.edges = new HashSet<>();
    }
}
