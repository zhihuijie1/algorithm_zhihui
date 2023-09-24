package algorithmbasic.basicsets.class13;
import java.util.ArrayList;

// 给定一棵二叉树的头节点head，返回这颗二叉树中最大的二叉搜索子树的大小
// 在线测试链接 : https://leetcode.com/problems/largest-bst-subtree
public class MaxSubBSTSize {

    public static class TreeNode {
        public int val;
        public TreeNode left;
        public TreeNode right;

        public TreeNode(int value) {
            val = value;
        }
    }

    public static class Info {
        private Boolean isBST;
        private int Max;
        private int Min;
        private int size;

        public Info(Boolean isBST, int max, int min, int size) {
            this.isBST = isBST;
            Max = max;
            Min = min;
            this.size = size;
        }
    }

    public static int largestBSTSubtree(TreeNode head) {
        if (head == null) {
            return 0;
        }
        Info headInfo = process(head);
        // return headInfo.isBST ? headInfo.size : 0;
        return headInfo.size;
    }

    public static Info process(TreeNode head) {
        if (head == null) {
            return null;
        }
        if (head.left == null && head.right == null) {
            return new Info(true, head.val, head.val, 1);
        }
        Info leftInfo = process(head.left);
        Info rightInfo = process(head.right);

        // 判断是否为BST
        Boolean leftB = leftInfo == null ? true : leftInfo.Max < head.val;
        Boolean rightB = rightInfo == null ? true : rightInfo.Min > head.val;
        Boolean isBST = (leftInfo == null || leftInfo.isBST) && (rightInfo == null || rightInfo.isBST) && leftB && rightB;

        // 求Max
        int Max = head.val;
        Max = leftInfo == null ? Max : Math.max(Max, leftInfo.Max);
        Max = rightInfo == null ? Max : Math.max(Max, rightInfo.Max);

        // 求Min
        int Min = head.val;
        Min = leftInfo == null ? Min : Math.min(Min, leftInfo.Min);
        Min = rightInfo == null ? Min : Math.min(Min, rightInfo.Min);

        // 求size -> 如果当前树是BST -> size
        // 如果当前树不是BST ->
        int Size = 0;
        if (isBST) {
            int leftSize = leftInfo == null ? (0) : (leftInfo.size);
            int rightSize = rightInfo == null ? (0) : (rightInfo.size);
            Size = leftSize + rightSize + 1;
        } else {
            int leftSize = leftInfo == null ? (0) : (leftInfo.size);
            int rightSize = rightInfo == null ? (0) : (rightInfo.size);
            Size = Math.max(leftSize, rightSize);
        }
        return new Info(isBST, Max, Min, Size);
    }
    // 时间复杂度是O(N)


    // -----------------------------------for test -------------------------------------------

    public static int right(TreeNode head) {
        if (head == null) {
            return 0;
        }
        int h = getBSTSize(head);
        if (h != 0) {
            return h;
        }
        return Math.max(right(head.left), right(head.right));
    }

    // 为了验证
    // 对数器方法
    public static int getBSTSize(TreeNode head) {
        if (head == null) {
            return 0;
        }
        ArrayList<TreeNode> arr = new ArrayList<>();
        in(head, arr);
        for (int i = 1; i < arr.size(); i++) {
            if (arr.get(i).val <= arr.get(i - 1).val) {
                return 0;
            }
        }
        return arr.size();
    }

    // 为了验证
    // 对数器方法
    public static void in(TreeNode head, ArrayList<TreeNode> arr) {
        if (head == null) {
            return;
        }
        in(head.left, arr);
        arr.add(head);
        in(head.right, arr);
    }

    // 为了验证
    // 对数器方法
    public static TreeNode generateRandomBST(int maxLevel, int maxValue) {
        return generate(1, maxLevel, maxValue);
    }

    // 为了验证
    // 对数器方法
    public static TreeNode generate(int level, int maxLevel, int maxValue) {
        if (level > maxLevel || Math.random() < 0.5) {
            return null;
        }
        TreeNode head = new TreeNode((int) (Math.random() * maxValue));
        head.left = generate(level + 1, maxLevel, maxValue);
        head.right = generate(level + 1, maxLevel, maxValue);
        return head;
    }

    // 为了验证
    // 对数器方法
    public static void main(String[] args) {
        int maxLevel = 4;
        int maxValue = 100;
        int testTimes = 1000000;
        System.out.println("测试开始");
        for (int i = 0; i < testTimes; i++) {
            TreeNode head = generateRandomBST(maxLevel, maxValue);
            if (largestBSTSubtree(head) != right(head)) {
                System.out.println("出错了！");
            }
        }
        System.out.println("测试结束");
    }
}