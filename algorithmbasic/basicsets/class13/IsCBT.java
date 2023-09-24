package algorithmbasic.basicsets.class13;
//判断二叉树是不是完全二叉树
import java.util.LinkedList;
import java.util.Queue;

public class IsCBT {
    //二叉树的节点内部类。
    public static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int data) {
            this.value = data;
        }
    }

    public static boolean isCBT1(Node head) {
        //空树默认是完全二叉树。
        if (head == null) {
            return true;
        }
        //基于条件二，我们要使用层序遍历。
        //层序遍历每个节点，判断每个节点是否满足两个条件。
        Boolean flag = false; //开关->遇到左右孩子不双全节点就打开开关。
        Queue<Node> queue = new LinkedList<>();
        queue.add(head);
        while (!queue.isEmpty()) {
            Node cur = queue.poll();
            if (cur.left == null && cur.right != null) {
                return false;
            }
            if (cur.left == null || cur.right == null) {
                //遇到左右孩子不双全节点就打开开关。
                flag = true;
            }
            //左右孩子不双全节点后面的所有节点必须是叶子节点才为完全二叉树。
            if (flag && (cur.left != null || cur.right != null)) {
                return false;
            }
            //层序遍历的条件。
            if (cur.left != null) {
                queue.add(cur.left);
            }
            if (cur.right != null) {
                queue.add(cur.right);
            }
        }
        return true;
    }
}