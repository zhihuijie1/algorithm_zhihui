package algorithmbasic.basicsets.class11;

import java.util.Stack;

// 二叉树先序、中序、后序的非递归遍历
public class UnRecursiveTraversalBT {
    // 内部类
    public static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int value) {
            this.value = value;
        }
    }

    // 方法
    // 非递归的前序遍历 -- 头左右
    public static void pre(Node head) {
        if (head == null) {
            return;
        }
        Stack<Node> s = new Stack<>();
        s.push(head);
        while (!s.isEmpty()) { // 先压右后压左  ->  头 左 右
            head = s.pop();
            System.out.print(head.value + " ");
            if (head.right != null) {
                s.push(head.right);
            }
            if (head.left != null) {
                s.push(head.left);
            }
        }
    }

    // 非递归的后序遍历 -- 左右头
    public static void pos1(Node head) { // 先压左后压右 -> 头 右 左
        if (head == null) {
            return;
        }
        Stack<Node> s1 = new Stack<>();
        Stack<Node> s2 = new Stack<>();
        s1.push(head);
        while (!s1.isEmpty()) {
            head = s1.pop(); // s1弹出的顺序是：头 右 左
            s2.push(head); // s2压入的顺序是：头 右 左
            if (head.left != null) {
                s1.push(head.left);
            }
            if (head.right != null) {
                s1.push(head.right);
            }
        }
        while (!s2.isEmpty()) { // s2弹出的顺序是：左 右 头
            System.out.print(s2.pop().value + " ");
        }
    }

    // 非递归的中序遍历 -- 左右头
    /*
    public static void in(Node head) {
        if (head == null) {
            return;
        }
        Stack<Node> s = new Stack<>();
        while () {
            if (head.right != null) {
                s.push(head.right);
            }
            s.push(head);
            if (head.left != null) {
                s.push(head.left);
            }
            head = s.pop();
        }
    }
     */

    // 自写1
    public static void in2(Node head) {
        if (head == null) {
            return;
        }
        Stack<Node> s = new Stack<>();
        s.push(head);
        while (head != null) {
            head = head.left;
            if (head != null) {
                s.push(head);
            }
        }
        while (!s.isEmpty()) {
            head = s.pop();
            System.out.print(head.value + " ");
            head = head.right;
            if (head != null) {
                s.push(head);
                while (head != null) {
                    head = head.left;
                    if (head != null) {
                        s.push(head);
                    }
                }
            }
        }
    }
    // 自写2 改进
    public static void in3(Node head) {
        if (head == null) {
            return;
        }
        Stack<Node> s = new Stack<>();
        while (!s.isEmpty() || head != null) {
            if(head != null) {
                s.push(head);
                head = head.left;
            }else {
                head = s.pop();
                System.out.print(head.value + " ");
                head = head.right;
            }
        }
    }
    // 左神：
    public static void in(Node cur) {
        System.out.print("in-order: ");
        if (cur != null) {
            Stack<Node> stack = new Stack<Node>();
            while (!stack.isEmpty() || cur != null) {
                if (cur != null) {
                    stack.push(cur);
                    cur = cur.left;
                } else {
                    cur = stack.pop();
                    System.out.print(cur.value + " ");
                    cur = cur.right;
                }
            }
        }
        System.out.println();
    }

    public static void main(String[] args) {
        Node head = new Node(1);
        head.left = new Node(2);
        head.right = new Node(3);
        head.left.left = new Node(4);
        head.left.right = new Node(5);
        head.right.left = new Node(6);
        head.right.right = new Node(7);

        //pre(head);
        //System.out.println("========");
        in3(head);
        System.out.println("========");

        in(head);
        System.out.println("========");
        //pos1(head);
        //System.out.println("========");
        //pos2(head);
        //System.out.println("========");
    }
}
