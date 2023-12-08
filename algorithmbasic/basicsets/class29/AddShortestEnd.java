package algorithmbasic.basicsets.class29;


import java.util.ArrayList;
import java.util.LinkedList;

/**
 * 给定两棵二叉树的头节点head1和head2
 * 想知道head1中是否有某个子树的结构和head2完全一样
 */
public class AddShortestEnd {
    static class Node {
        String value;
        Node left;
        Node right;

        public Node(String value) {
            this.value = value;
        }
    }

    /**
     * 思路：将两个树进行完全的先序遍历(null值也算)，将结果存放到一个数组中。然后使用kmp算法判断head1中是否有head2
     */
    public static Boolean isSameSonTree(Node head1, Node head2) {
        if (head1 == null || head2 == null) {
            return false;
        }
        // 将两个树进行完全的先序遍历(null值也算)，将结果存放到一个数组中
        LinkedList<String> list1 = new LinkedList<>();
        LinkedList<String> list2 = new LinkedList<>();

        priTraversal(head1, list1);
        priTraversal(head2, list2);

        return KMP(list1, list2);
    }

    // 对以head为头节点的树进行先序遍历，并将结果存放到list数组中
    private static void priTraversal(Node head, LinkedList<String> list) {
        if (head == null) {
            list.add("null");
            return;
        }
        priTraversal(head.left, list);
        list.add(head.value);
        priTraversal(head.right, list);
    }

    private static Boolean KMP(LinkedList<String> list1, LinkedList<String> list2) {
        if (list1 == null || list2 == null || list2.size() > list1.size()) {
            return false;
        }
        int x = 0;
        int y = 0;
        int[] NEXT = makeNext(list2);

        while (x < list1.size() && y < list2.size()) {
            if (list1.get(x).equals(list2.get(y))) {
                x++;
                y++;
            } else if (y != 0) {
                y = NEXT[y];
            } else {
                x++;
            }
        }
        return y == list2.size() ? true : false;
    }

    private static int[] makeNext(LinkedList<String> list) {
        if (list.size() == 1) {
            return new int[]{-1};
        }
        if (list.size() == 2) {
            return new int[]{-1, 0};
        }

        int[] NEXT = new int[list.size()];
        NEXT[0] = -1;
        NEXT[1] = 0;
        int c = 0;

        for (int i = 2; i < list.size(); i++) {
            if (list.get(i - 1).equals(list.get(c))) {
                NEXT[i] = ++c;
            } else if (c != 0) {
                c = NEXT[c];
            } else {
                NEXT[i] = 0;
            }
        }
        return NEXT;
    }

    public static void main(String[] args) {
        Node head1 = new Node("1");
        Node head2 = new Node("3");
        Node head3 = new Node("3");
        Node head4 = new Node("4");
        Node head5 = new Node("5");
        Node head6 = new Node("6");

        head1.left = head3;
        head1.right = head4;
        head3.left = head5;
        head3.right = head6;

        head2.left = head6;
        head2.right = head5;

        Boolean u = isSameSonTree(head1, head2);
        System.out.println(u);
    }
}
