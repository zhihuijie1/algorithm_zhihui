package algorithmbasic.basicsets.class13;

import java.util.ArrayList;

// 判断二叉树是否是搜索二叉树。
// https://leetcode.cn/problems/validate-binary-search-tree/
public class IsBST {
    // 节点内部类
    public static class TreeNode {
        public int val;
        public TreeNode left;
        public TreeNode right;

        public TreeNode(int data) {
            this.val = data;
        }
    }

    public static boolean isBST1(TreeNode head) {
        if (head == null) {
            return true;
        }
        ArrayList<TreeNode> arr = new ArrayList<>();
        in(head, arr);
        for (int i = 1; i < arr.size(); i++) {
            if (arr.get(i).val <= arr.get(i - 1).val) {
                return false;
            }
        }
        return true;
    }

    public static void in(TreeNode head, ArrayList<TreeNode> arr) {
        if (head == null) {
            return;
        }
        in(head.left, arr);
        arr.add(head);
        in(head.right, arr);
    }


    public static class Info {
        Boolean isBst;
        int Max;
        int Min;

        public Info(Boolean isBst, int Max, int Min) {
            this.isBst = isBst;
            this.Max = Max;
            this.Min = Min;
        }
    }


    public static boolean isBST2(TreeNode head) {
        if (head == null) {
            return true;
        }
        return process(head).isBst;
    }

    public static Info process(TreeNode head) {
        if (head == null) {
            return null;
        }
        if (head.left == null && head.right == null) {
            return new Info(true, head.val, head.val);
        }
        Info leftInfo = process(head.left);
        Info rightInfo = process(head.right);

        Boolean leftB = leftInfo == null ? true : leftInfo.Max < head.val;
        Boolean rightB = rightInfo == null ? true : rightInfo.Min > head.val;
        Boolean isBst = (leftInfo == null || leftInfo.isBst) && (rightInfo == null || rightInfo.isBst) && leftB && rightB;
        int max = head.val;
        max = leftInfo == null ? max : Math.max(max, leftInfo.Max);
        max = rightInfo == null ? max : Math.max(max, rightInfo.Max);
        int min = head.val;
        min = leftInfo == null ? min : Math.min(head.val, leftInfo.Min);
        min = rightInfo == null ? min : Math.min(min, rightInfo.Min);
        return new Info(isBst, max, min);
    }

    // for test
    public static TreeNode generateRandomBST(int maxLevel, int maxValue) {
        return generate(1, maxLevel, maxValue);
    }

    // for test
    public static TreeNode generate(int level, int maxLevel, int maxValue) {
        if (level > maxLevel || Math.random() < 0.5) {
            return null;
        }
        TreeNode head = new TreeNode((int) (Math.random() * maxValue));
        head.left = generate(level + 1, maxLevel, maxValue);
        head.right = generate(level + 1, maxLevel, maxValue);
        return head;
    }

    public static void main(String[] args) {
        int maxLevel = 4;
        int maxValue = 100;
        int testTimes = 1000000;
        for (int i = 0; i < testTimes; i++) {
            TreeNode head = generateRandomBST(maxLevel, maxValue);
            if (isBST1(head) != isBST2(head)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("finish!");
    }
}