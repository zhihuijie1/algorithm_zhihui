package algorithmbasic.basicsets.class12;

import java.util.LinkedList;
import java.util.Queue;

/*
 * 二叉树可以通过先序、后序或者按层遍历的方式序列化和反序列化，
 * 以下代码全部实现了。
 * 但是，二叉树无法通过中序遍历的方式实现序列化和反序列化
 * 因为不同的两棵树，可能得到同样的中序序列，即便补了空位置也可能一样。
 * 比如如下两棵树
 *         __2
 *        /
 *       1
 *       和
 *       1__
 *          \
 *           2
 * 补足空位置的中序遍历结果都是{ null, 1, null, 2, null}
 *
 * */
public class SerializeAndReconstructTree {
    // 二叉树节点内部类
    public static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int data) {
            this.value = data;
        }
    }

    // 给你一个头节点，将二叉树按先序遍历的顺序进行序列化。
    public static Queue<String> preSerial(Node head) {
        Queue<String> queue = new LinkedList<>();
        // 进行先序的递归遍历，然后序列化。
        pres(head, queue);
        return queue;
    }

    public static void pres(Node head, Queue<String> queue) {
        if (head == null) {
            queue.add(null);
            return;
        }
        queue.add(String.valueOf(head.value));
        pres(head.left, queue);
        pres(head.right, queue);
    }

    // 给你一个字符串类型的队列，按先序遍历进行反序列化,并返回头节点。
    public static Node buildByPreQueue(Queue<String> prelist) {
        if (prelist == null || prelist.size() == 0) {
            return null;
        }
        return preb(prelist);
    }

    public static Node preb(Queue<String> prelist) {
        String value = prelist.poll();
        if (value == null) {
            return null;
        }
        Node head = new Node(Integer.valueOf(value));
        head.left = preb(prelist);
        head.right = preb(prelist);
        return head;
    }

    // 层序遍历的序列化
    public static Queue<String> levelSerial(Node head) {
        Queue<String> ans = new LinkedList<>(); // 装序列化后结果的
        Queue<Node> queue = new LinkedList<>(); // 进行层序遍历使用
        if (head == null) {
            ans.add(null);
            return ans;
        }
        // head != null
        queue.add(head);
        ans.add(String.valueOf(head.value));
        while (!queue.isEmpty()) {
            Node node = queue.poll();
            // 能从queue中弹出的一定不是null
            if (node.left != null) { // 不管孩子是不是空都往序列化队列里放，层序遍历的队列只放非空节点。
                queue.add(node.left);
                ans.add(String.valueOf(node.left.value));
            } else {
                ans.add(null);
            }
            if (node.right != null) {
                queue.add(node.right);
                ans.add(String.valueOf(node.right.value));
            } else {
                ans.add(null);
            }
        }
        return ans;
    }

    // 层序遍历的反序列化
    public static Node buildByLevelQueue(Queue<String> ans) {
        if (ans == null || ans.size() == 0) {
            return null;
        }
        Node head = generateNode(ans.poll());
        if(head == null) {
            return null;
        }
        // head != null
        Queue<Node> queue = new LinkedList<>();
        queue.add(head);
        while (!queue.isEmpty()) {
            Node note = queue.poll();
            note.left = generateNode(ans.poll());
            note.right = generateNode(ans.poll());
            if(note.left != null) {
                queue.add(note.left);
            }
            if(note.right != null) {
                queue.add(note.right);
            }
        }
        return head;
    }

    // 给一个字符串，返回所对应的整形节点。
    public static Node generateNode(String val) {
        if(val == null) {
            return null;
        }
        return new Node(Integer.valueOf(val));
    }
}
