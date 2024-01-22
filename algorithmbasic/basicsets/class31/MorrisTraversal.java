package algorithmbasic.basicsets.class31;


public class MorrisTraversal {
    static class Node {
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
        while (cur != null) {
            System.out.print(cur.value + " ");
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

    //morries的先序遍历
    public static void morrisPre(Node head) {
        Node cur = head;
        Node mostRight = null;
        while (cur != null) {
            if (cur.left == null) {
                System.out.print(cur.value + " ");
                cur = cur.right;
            } else {
                mostRight = cur.left;
                while (mostRight.right != null && mostRight.right != cur) {
                    mostRight = mostRight.right;
                }
                if (mostRight.right == null) {
                    mostRight.right = cur;
                    System.out.print(cur.value + " ");
                    cur = cur.left;
                } else {
                    mostRight.right = null;
                    cur = cur.right;
                }
            }
        }
    }

    // morries的中序遍历
    public static void morrisIn(Node head) {
        Node cur = head;
        Node mostRight = null;
        while (cur != null) {
            if (cur.left == null) {
                System.out.print(cur.value + " ");
                cur = cur.right;
            } else {
                mostRight = cur.left;
                while (mostRight.right != null && mostRight.right != cur) {
                    mostRight = mostRight.right;
                }
                if (mostRight.right == null) {
                    mostRight.right = cur;
                    cur = cur.left;
                } else {
                    mostRight.right = null;
                    System.out.print(cur.value + " ");
                    cur = cur.right;
                }
            }
        }
    }

    /**
     * morries的后序遍历
     *
     * @param head 头节点
     */
    public static void morrisLast(Node head) {
        Node cur = head;
        Node mostRight = null;
        while (cur != null) {
            if (cur.left == null) {
                cur = cur.right;
            } else {
                mostRight = cur.left;
                while (mostRight.right != null && mostRight.right != cur) {
                    mostRight = mostRight.right;
                }
                if (mostRight.right != null) {
                    // 第二次来到的时候，将左子树右列倒序打印
                    mostRight.right = null;
                    printList(cur.left);
                    cur = cur.right;
                } else {
                    mostRight.right = cur;
                    cur = cur.left;
                }
            }
        }
        printList(head);
    }

    /**
     * 倒序打印，为了保持空间复杂度是O（1），不可以使用额外的容器，这里采用反转链表的方法。
     * @param head 头节点
     */
    public static void printList(Node head) {
        //先将左子树右列进行反转
        Node c = revorse(head);
        Node cur = c;
        while (cur != null) {
            System.out.print(cur.value + " ");
            cur = cur.right;
        }
        //先将左子树右列反转回去
        revorse(c);
    }

    /**
     * 链表反转函数
     *
     * @param head 头节点
     */
    public static Node revorse(Node head) {
        if (head == null) {
            return null;
        }
        Node cur = head;
        Node next = cur.right;
        Node nextNext = null;
        cur.right = null;
        while (next != null) {
            nextNext = next.right;
            next.right = cur;
            cur = next;
            next = nextNext;
        }
        return cur;
    }

    public static void main(String[] args) {
        Node node1 = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(3);
        Node node4 = new Node(4);
        Node node5 = new Node(5);
        Node node6 = new Node(6);
        Node node7 = new Node(7);
        Node node8 = new Node(8);
        Node node9 = new Node(9);
        node1.left = node2;
        node1.right = node3;
        node2.left = node4;
        node2.right = node5;
        node3.left = node6;
        node3.right = node7;
        node5.right = node8;
        node5.left = null;
        node8.right = node9;
        node8.left = null;
        node9.left = null;
        node9.right = null;
        node7.left = null;
        node7.right = null;
        node6.left = null;
        node6.right = null;
        node4.left = null;
        node4.right = null;

        //morrisLast(node1);

        //morrisPre(node1);
        morris(node1);
        //morrisIn(node1);
        //printList(node2);
        //printList(node2);
    }
}