package algorithmbasic.basicsets.class17;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

//图的宽度优先遍历
public class BFS {
    //只要进行宽度优先遍历就要指定一个开始节点。
    //-> 从哪个节点开始进行宽度优先遍历。
    public static void bfs(Node start) {
        //创建一个set目的是为了防止多个直接邻居进入队列从而导致循环无法结束。
        HashSet<Node> set = new HashSet<>();
        //进行宽度优先遍历的必备神器。
        Queue<Node> queue = new LinkedList<>();

        //放的时候一起放。
        queue.add(start);
        set.add(start);
        while (!queue.isEmpty()) {
            Node cur = queue.poll();
            //将当前节点的直接邻居放入队列中。
            for (int i = 0; i < cur.nexts.size(); i++) {
                //如果set中存在说明之前进入过，就不用再进了。
                if (!set.contains(cur.nexts.get(i))) {
                    queue.add(cur.nexts.get(i));
                    set.add(cur.nexts.get(i));
                }
            }
            System.out.print(cur.value + " ");
        }
    }


    public static void main(String[] args) {
        Node a = new Node(1);
        Node b = new Node(2);
        Node c = new Node(3);
        Node d = new Node(4);
        Node e = new Node(5);
        Node f = new Node(6);
        Node k = new Node(7);
        a.nexts.add(b);
        a.nexts.add(c);
        a.nexts.add(k);
        b.nexts.add(e);
        e.nexts.add(f);
        f.nexts.add(c);
        c.nexts.add(d);
        d.nexts.add(c);

        bfs(a);
    }
}