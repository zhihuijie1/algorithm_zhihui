package algorithmbasic.basicsets.class10;

// 给定一个单链表的头节点head，给定一个整数n，将链表按n划分成左边<n、中间==n、右边>n

import java.util.ArrayList;
import java.util.Comparator;

public class SmallerEqualBigger {
    // 内部类
    public static class Node {
        public int value;
        public Node next;

        public Node(int data) {
            this.value = data;
        }
    }

    // 方法一
    public static Node listPartition1(Node head, int pivot) {
        Node cur = head;
        Node SH = null;
        Node ST = null;
        Node EH = null;
        Node ET = null;
        Node BH = null;
        Node BT = null;
        while (cur != null) {
            Node next = cur.next;
            cur.next = null;
            if (cur.value < pivot) {
                if (ST == null) {
                    SH = ST = cur;
                } else {
                    ST.next = cur;
                    ST = cur;
                }
            }

            if (cur.value == pivot) {
                if (ET == null) {
                    EH = ET = cur;
                } else {
                    ET.next = cur;
                    ET = cur;
                }
            }

            if (cur.value > pivot) {
                if (BT == null) {
                    BH = BT = cur;
                } else {
                    BT.next = cur;
                    BT = cur;
                }
            }

            cur = next;
        }
        // 三个区域的连接
        ST.next = EH;
        ET.next = BH;

        if (ST != null) {
            ST.next = EH;
            ET = ET == null ? ST : ET;
        }

        if (ET != null) {
            ET.next = BH;
        }

        return SH != null ? SH : (EH != null ? EH : BH);
    }

    // 方法二：
    public static Node listPartition2(Node head, int pivot) {
        if(head == null) {
            return null;
        }
        Node cur = head;
        ArrayList<Node> list = new ArrayList<>();
        int count = 0;
        while (cur != null) {
            list.add(cur);
            count++;
            Node next = cur.next;
            cur.next = null;
            cur = next;
        }
        list.sort(new myComparator());
        head = list.get(0);
        for (int i = 1; i < count; i++) {
            list.get(i - 1).next = list.get(i);
        }
        return head;
    }

    // 比较器
    public static class myComparator implements Comparator<Node> {
        @Override
        public int compare(Node o1, Node o2) {
            return o1.value - o2.value;
        }
    }

    public static void printArray(Node head) {
        if(head == null) {
            return;
        }
        Node cur = head;
        while(cur != null) {
            System.out.print(cur.value + " --> ");
            cur = cur.next;
        }
    }

    public static void main(String[] args) {
        Node node1 = new Node(1);
        Node node2 = new Node(10);
        Node node3 = new Node(3);
        Node node4 = new Node(2);
        Node node5 = new Node(10);
        Node node6 = new Node(6);
        Node node7 = new Node(1);
        Node node8 = new Node(3);
        node1.next = node2;
        node2.next = node3;
        node3.next = node4;
        node4.next = node5;
        node5.next = node6;
        node6.next = node7;
        node7.next = node8;
        listPartition2(node1 , 3);
        printArray(node1);
    }

}


