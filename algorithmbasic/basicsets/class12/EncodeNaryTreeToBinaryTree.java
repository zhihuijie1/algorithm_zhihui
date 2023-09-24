package algorithmbasic.basicsets.class12;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
// https://leetcode.cn/problems/encode-n-ary-tree-to-binary-tree/
/*
设计一个算法，可以将 N 叉树编码为二叉树，并能将该二叉树解码为原 N 叉树。
一个 N 叉树是指每个节点都有不超过 N 个孩子节点的有根树。
类似地，一个二叉树是指每个节点都有不超过 2 个孩子节点的有根树。
你的编码 / 解码的算法的实现没有限制，你只需要保证一个 N 叉树可以编码为二叉树且该二叉树可以解码回原始 N 叉树即可。

 */
public class EncodeNaryTreeToBinaryTree {

    // N叉树的Node结构
    public static class Node {
        public int val;
        public List<Node> children;

        public Node() {
        }

        public Node(int _val) {
            val = _val;
        }

        public Node(int _val, List<Node> _children) {
            val = _val;
            children = _children;
        }
    }

    // 二叉树的Node结构
    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    // Encodes an n-ary tree to a binary tree.
    public TreeNode encode(Node root) {
        if (root == null) {
            return null;
        }
        TreeNode head = new TreeNode(root.val);
        head.left = en(root.children);
        return head;
    }

    private TreeNode en(List<Node> children) {
        TreeNode head = null; // 抓住该二叉树的头节点
        TreeNode cur = null; // 定位用
        for (Node child : children) {
            TreeNode tNode = new TreeNode(child.val);
            if (head == null) {
                head = tNode;
            } else {
                cur.right = tNode;
            }
            cur = tNode;
            cur.left = en(child.children);
        }
        return head;
    }

    // Decodes your binary tree to an n-ary tree.
    public Node decode(TreeNode root) {
        if (root == null) {
            return null;
        }
        return new Node(root.val, de(root.left));
    }

    public static List<Node> de(TreeNode root) {
        List<Node> children = new LinkedList<>();
        while (root != null) {
            Node node = new Node(root.val, de(root.left));
            children.add(node);
            root = root.right;
        }
        return children;
    }
}
