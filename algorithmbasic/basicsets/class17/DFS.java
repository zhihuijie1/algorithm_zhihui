package algorithmbasic.basicsets.class17;

import java.util.HashSet;
import java.util.Stack;

//图的深度优先遍历。
public class DFS {
    //只要进行深度优先遍历就要指定一个开始节点。
    //-> 从哪个节点开始进行深度优先遍历。
    public static void dfs(Node start) {
        //创建一个set目的是为了判断当前节点是否来过，如果来过说明绕了一圈又回来了，就直接返回往回走。
        HashSet<Node> set = new HashSet<>();
        process(start, set);
    }

    public static void process(Node start, HashSet<Node> set) {
        if (set.contains(start)) {
            return;
        }
        set.add(start);
        System.out.print(start.value + " ");
        for (Node next : start.nexts) {
            process(next, set);
        }
    }


    //非递归写法
    public static void dfs2(Node start) {
        HashSet<Node> set = new HashSet<>();
        Stack<Node> stk = new Stack();

        stk.push(start);
        set.add(start);
        System.out.print(start.value + " ");
        while (!stk.isEmpty()) {
            Node cur = stk.pop();
            for (Node next : cur.nexts) {
                if (!set.contains(next)) {
                    stk.push(cur);
                    stk.push(next);
                    set.add(next);
                    System.out.print(next.value + " ");
                    break;
                }
            }
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

        dfs(a);
        System.out.println();
        dfs2(a);
    }
}