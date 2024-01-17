package algorithmbasic.basicsets.class31;

public class MorrisTraversal {
    public class Node {
        int value;
        Node right;
        Node left;

        public Node(int value) {
            this.value = value;
        }
    }

    // morries遍历
    public static void morris(Node head) {
        Node cur = head;
        Node mostright = null;
        while(cur != null) {
            if (cur.left == null) {
                // 没有左孩子 -> 直接进入右孩子
                cur = cur.right;
            } else {
                // 有左孩子 -> 判断左子树的最右节点是否指向当前节点。
                // 如何找到左子树的最右节点？？
                mostright = cur.left;
                while (mostright.right != null && mostright.right != cur) {
                    mostright = mostright.right;
                }
                if (mostright.right == null) {
                    mostright.right = cur;
                    cur = cur.left;
                } else {
                    mostright.right = null;
                    cur = cur.right;
                }
            }
        }
    }
}