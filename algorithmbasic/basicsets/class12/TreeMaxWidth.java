package algorithmbasic.basicsets.class12;


import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

// 求二叉树的最大宽度
public class TreeMaxWidth {
    // 二叉树节点
    public static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int data) {
            this.value = data;
        }
    }

    // 方法一：用容器实现
    public static int maxWidthUseMap(Node head) {
        if (head == null) {
            return 0;
        }
        // head != null
        // key -> value : 当前的Node在哪一层。
        HashMap<Node, Integer> map = new HashMap<>();
        Queue<Node> queue = new LinkedList<>();
        int curLevel = 1; // 当前是第几层。
        int curLevelNodes = 0; // 当前层的节点个数。
        int max = 0; // 二叉树的最大宽度。

        map.put(head, 1);
        queue.add(head);
        while (!queue.isEmpty()) {
            Node node = queue.poll();
            int curNodeLevel = map.get(node); // 当前节点是第几层。
            if (node.left != null) {
                map.put(node.left, curNodeLevel + 1);
                queue.add(node.left);
            }

            if (node.right != null) {
                map.put(node.right, curNodeLevel + 1);
                queue.add(node.right);
            }
            if (curLevel == curNodeLevel) {
                curLevelNodes++;
            } else {
                max = Math.max(max, curLevelNodes);
                curLevelNodes = 1;
                curLevel++;
            }
        }
        max = Math.max(max, curLevelNodes); // 最后一层要单独另算。
        return max;
    }


    // 方法二：
    public static int maxWidthNoMap(Node head) {
        if (head == null) {
            return 0;
        }
        // head != null
        int curCount = 0; // 记录当前层的节点个数
        Node curEnd = null; // 记录当前层的结尾节点
        Node nextEnd = null; // 记录下一层的结尾节点
        int max = 0; // 记录最大的宽度

        Queue<Node> queue = new LinkedList<>();
        curEnd = head;
        queue.add(head);
        while (!queue.isEmpty()) {
            Node node = queue.poll();
            curCount++;
            if (node.left != null) {
                queue.add(node.left);
                nextEnd = node.left;
            }
            if (node.right != null) {
                queue.add(node.right);
                nextEnd = node.right;
            }
            if (node == curEnd) {
                max = Math.max(max, curCount);
                curCount = 0;
                curEnd = nextEnd;
            }
        }
        return max;
    }

    // ---------------------------------------------------------for test---------------------------------------------------------
    public static void main(String[] args) {
        int testTimes = 100000;
        int maxLevel = 100; // 随机生成的二叉树的最大层数
        int maxValue = 100; // 随机生成的二叉树的节点的最大值
        for (int i = 0; i < testTimes; i++) {
            Node head = generateRandomBST(maxLevel, maxValue);
            if (maxWidthUseMap(head) != maxWidthNoMap(head)) {
                System.out.println("oops");
                System.out.println(maxWidthUseMap(head));
                System.out.println(maxWidthNoMap(head));
                break;
            }
        }
        System.out.println("finish");
    }

    public static Node generateRandomBST(int maxLevel, int maxValue) {
        int maxL = (int) (Math.random() * maxLevel);
        Node BST = generate(1, maxL, maxValue);
        return BST;
    }

    public static Node generate(int curlevel, int maxLevel, int maxValue) {
        if (curlevel > maxLevel || Math.random() < 0.5) {
            return null;
        }
        Node head = new Node((int) (Math.random() * maxLevel)); // [0,100)
        head.left = generate(curlevel + 1, maxLevel, maxValue);
        head.right = generate(curlevel + 1, maxLevel, maxValue);
        return head;
    }
}