package algorithmbasic.basicsets.class13;

//判断二叉树是不是平衡二叉树
public class IsBalanced {


    public static boolean isBalanced1(Node head) {
        boolean[] ans = new boolean[1];
        ans[0] = true;
        process1(head, ans);
        return ans[0];
    }

    public static int process1(Node head, boolean[] ans) {
        if (!ans[0] || head == null) {
            return -1;
        }
        int leftHeight = process1(head.left, ans);
        int rightHeight = process1(head.right, ans);
        if (Math.abs(leftHeight - rightHeight) > 1) {
            ans[0] = false;
        }
        return Math.max(leftHeight, rightHeight) + 1;
    }


    // 节点内部类
    public static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int data) {
            this.value = data;
        }
    }

    // 左右信息都一样，Info返回类型的结构体。
    private static class Info {
        Boolean isBalanced;
        int height;

        public Info(Boolean isBalanced, int height) {
            this.isBalanced = isBalanced;
            this.height = height;
        }
    }

    public static boolean isBalanced2(Node head) {
        if (head == null) {
            return true;
        }
        Info res = process(head);
        return res.isBalanced;
    }

    public static Info process(Node head) {
        if (head == null) {
            return new Info(true, 0);
        }
        Info leftInfo = process(head.left);
        Info rightInfo = process(head.right);
        // 当前节点是否是平衡的，当前节点的高度是多少。
        Boolean isBalanced = leftInfo.isBalanced && rightInfo.isBalanced
                && (Math.abs(leftInfo.height - rightInfo.height) < 2);
        int height = Math.max(leftInfo.height, rightInfo.height) + 1;
        return new Info(isBalanced, height);
    }

    // ----------------------------------------for test ----------------------------------------
    public static void main(String[] args) {
        int testTime = 10000;
        int maxLevel = 100;
        int maxValue = 100;
        for (int i = 0; i < testTime; i++) {
            Node head = generateRandomBST(maxLevel, maxValue);
            if (isBalanced1(head) != isBalanced2(head)) {
                System.out.println("oops");
            }
        }
        System.out.println("finished");
    }

    public static Node generateRandomBST(int maxLevel, int maxValue) {
        int randomLevel = (int) (Math.random() * maxLevel);
        return process(1, randomLevel, maxValue);
    }

    public static Node process(int curlevel, int maxLvel, int maxValue) {
        if(curlevel > maxLvel || Math.random() < 0.5) {
            return null;
        }
        Node head = new Node((int) (Math.random() * maxValue));
        head.left = process(curlevel + 1, maxLvel, maxValue); // 下一个节点需要的条件作为参数。
        head.right = process(curlevel + 1, maxLvel, maxValue);
        return head;
    }
}