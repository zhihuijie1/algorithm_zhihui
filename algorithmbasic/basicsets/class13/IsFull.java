package algorithmbasic.basicsets.class13;

// 判断二叉树是否是满二叉树
public class IsFull {

    public static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int data) {
            this.value = data;
        }
    }

    public static class Info {
        private int NodeNumber;
        private int height;

        public Info(int NodeNumber, int height) {
            this.NodeNumber = NodeNumber;
            this.height = height;
        }
    }

    public static boolean isFull1(Node head) {
        Info headInfo = process(head);
        return (Math.pow(2, headInfo.height) - 1) == headInfo.NodeNumber;
    }

    public static Info process(Node head) {
        if (head == null) {
            return new Info(0, 0);
        }
        Info leftInfo = process(head.left);
        Info rightInfo = process(head.right);
        int NodeNumber = leftInfo.NodeNumber + rightInfo.NodeNumber + 1;
        int height = Math.max(leftInfo.height, rightInfo.height) + 1;
        return new Info(NodeNumber, height);
    }


    public static boolean isFull2(Node head) {
        if (head == null) {
            return true;
        }
        return process2(head).isFull;
    }

    public static class Info2 {
        public boolean isFull;
        public int height;

        public Info2(boolean f, int h) {
            isFull = f;
            height = h;
        }
    }

    public static Info2 process2(Node h) {
        if (h == null) {
            return new Info2(true, 0);
        }
        Info2 leftInfo = process2(h.left);
        Info2 rightInfo = process2(h.right);
        boolean isFull = leftInfo.isFull && rightInfo.isFull && leftInfo.height == rightInfo.height;
        int height = Math.max(leftInfo.height, rightInfo.height) + 1;
        return new Info2(isFull, height);
    }

    // for test
    public static Node generateRandomBST(int maxLevel, int maxValue) {
        return generate(1, maxLevel, maxValue);
    }

    // for test
    public static Node generate(int level, int maxLevel, int maxValue) {
        if (level > maxLevel || Math.random() < 0.5) {
            return null;
        }
        Node head = new Node((int) (Math.random() * maxValue));
        head.left = generate(level + 1, maxLevel, maxValue);
        head.right = generate(level + 1, maxLevel, maxValue);
        return head;
    }

    public static void main(String[] args) {
        int maxLevel = 5;
        int maxValue = 100;
        int testTimes = 1000000;
        System.out.println("测试开始");
        for (int i = 0; i < testTimes; i++) {
            Node head = generateRandomBST(maxLevel, maxValue);
            if (isFull1(head) != isFull2(head)) {
                System.out.println("出错了!");
            }
        }
        System.out.println("测试结束");
    }
}