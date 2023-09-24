package algorithmbasic.basicsets.class13;

// 求二叉树的最大距离
public class MaxDistance {

    public static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int data) {
            this.value = data;
        }
    }

    public static class Info {
        private int height;
        private int distance;

        public Info(int height, int distance) {
            this.distance = distance;
            this.height = height;
        }
    }

    public static int maxDistance(Node head) {
        if (head == null) {
            return 0;
        }
        return process(head).distance;
    }

    public static Info process(Node head) {
        if (head == null) {
            return new Info(0, 0);
        }
        Info leftInfo = process(head.left);
        Info rightInfo = process(head.right);
        int height = Math.max(leftInfo.height, rightInfo.height) + 1;
        int distance = Math.max(Math.max(leftInfo.distance, rightInfo.distance), leftInfo.height + rightInfo.height + 1);
        return new Info(height, distance);
    }
}
