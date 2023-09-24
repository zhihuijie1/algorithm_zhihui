package algorithmbasic.basicsets.class10;

import java.util.Stack;

// 给定一个单链表的头节点head，请判断该链表是否为回文结构
public class IsPalindromeList {

    public static class Node {
        public int val;
        public Node next;

        public Node(int val) {
            this.val = val;
        }
    }

    // 方法一：采用容器的暴力方法

    public static boolean isPalindrome1(Node head) {
        Stack<Integer> stack = new Stack<>();
        Node cur = head;
        // 遍历第一遍，将链表中的数据放入栈中。
        while (cur != null) {
            stack.push(cur.val);
            cur = cur.next;
        }
        // 第二次遍历，将栈中的数据一个一个的弹出来，进行比对。
        cur = head;
        while (cur != null) {
            if (cur.val != stack.pop()) {
                return false;
            }
            cur = cur.next;
        }
        return true;
    }

    // 方法二：
    public static boolean isPalindrome2(Node head) {
        if (head == null || head.next == null) {
            return true;
        }
        Node slow = head;
        Node fast = head;
        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;
        }
        // slow:中点
        Node n1 = slow;
        Node n2 = n1.next;
        Node p = null;
        slow.next = null;
        while (n2 != null) {
            p = n2.next;
            n2.next = n1;
            n1 = n2;
            n2 = p;
        }

        // 1 -> 2 -> 3 <- 2 <- 1
        // 1 -> 2 -> 2 <- 1
        Node n3 = head; // left first
        n2 = n1; // right first
        Boolean ans = true;
        while (n2 != null && n3 != null) {
            if (n3.val != n2.val) {
                ans = false;
                break;
            }
            n2 = n2.next; // from right to mid
            n3 = n3.next; // from left to mid
        }
        // n1: right first
        // n2: right second
        // n3: right third
        n2 = n1.next;
        n1.next = null;
        while (n2 != null) {
            n3 = n2.next;
            n2.next = n1;
            n1 = n2;
            n2 = n3;
        }
        return ans;
    }


    public static void printLinkedList(Node node) {
        System.out.print("Linked List: ");
        while (node != null) {
            System.out.print(node.val + " ");
            node = node.next;
        }
        System.out.println();
    }

    public static void main(String[] args) {

        Node head = null;
        printLinkedList(head);
        System.out.print(isPalindrome1(head) + " | ");
        System.out.print(isPalindrome2(head) + " | ");
        //System.out.println(isPalindrome3(head) + " | ");
        printLinkedList(head);
        System.out.println("=========================");

        head = new Node(1);
        printLinkedList(head);
        System.out.print(isPalindrome1(head) + " | ");
        System.out.print(isPalindrome2(head) + " | ");
        //System.out.println(isPalindrome3(head) + " | ");
        printLinkedList(head);
        System.out.println("=========================");

        head = new Node(1);
        head.next = new Node(2);
        printLinkedList(head);
        System.out.print(isPalindrome1(head) + " | ");
        System.out.print(isPalindrome2(head) + " | ");
        //System.out.println(isPalindrome3(head) + " | ");
        printLinkedList(head);
        System.out.println("=========================");

        head = new Node(1);
        head.next = new Node(1);
        printLinkedList(head);
        System.out.print(isPalindrome1(head) + " | ");
        System.out.print(isPalindrome2(head) + " | ");
        //System.out.println(isPalindrome3(head) + " | ");
        printLinkedList(head);
        System.out.println("=========================");

        head = new Node(1);
        head.next = new Node(2);
        head.next.next = new Node(3);
        printLinkedList(head);
        System.out.print(isPalindrome1(head) + " | ");
        Boolean a = isPalindrome2(head);
        System.out.print(a + " | ");
        //System.out.println(isPalindrome3(head) + " | ");
        printLinkedList(head);
        System.out.println("=========================");

        head = new Node(1);
        head.next = new Node(2);
        head.next.next = new Node(1);
        printLinkedList(head);
        System.out.print(isPalindrome1(head) + " | ");
        System.out.print(isPalindrome2(head) + " | ");
        //System.out.println(isPalindrome3(head) + " | ");
        printLinkedList(head);
        System.out.println("=========================");

        head = new Node(1);
        head.next = new Node(2);
        head.next.next = new Node(3);
        head.next.next.next = new Node(1);
        printLinkedList(head);
        System.out.print(isPalindrome1(head) + " | ");
        System.out.print(isPalindrome2(head) + " | ");
        //System.out.println(isPalindrome3(head) + " | ");
        printLinkedList(head);
        System.out.println("=========================");

        head = new Node(1);
        head.next = new Node(2);
        head.next.next = new Node(2);
        head.next.next.next = new Node(1);
        printLinkedList(head);
        System.out.print(isPalindrome1(head) + " | ");
        System.out.print(isPalindrome2(head) + " | ");
        //System.out.println(isPalindrome3(head) + " | ");
        printLinkedList(head);
        System.out.println("=========================");

        head = new Node(1);
        head.next = new Node(2);
        head.next.next = new Node(3);
        head.next.next.next = new Node(2);
        head.next.next.next.next = new Node(1);
        printLinkedList(head);
        System.out.print(isPalindrome1(head) + " | ");
        System.out.print(isPalindrome2(head) + " | ");
        //System.out.println(isPalindrome3(head) + " | ");
        printLinkedList(head);
        System.out.println("=========================");

    }
}
