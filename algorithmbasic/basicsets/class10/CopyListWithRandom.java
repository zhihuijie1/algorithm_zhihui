package algorithmbasic.basicsets.class10;

import java.util.HashMap;

/*
一种特殊的单链表节点类描述如下
class Node {
int value;
Node next;
Node rand;
Node(int val) { value = val; }
}
rand指针是单链表节点结构中新增的指针，rand可能指向链表中的任意一个节点，也可能指向null
给定一个由Node节点类型组成的无环单链表的头节点head，请实现一个函数完成这个链表的复制
返回复制的新链表的头节点，要求时间复杂度O(N)，额外空间复杂度O(1)
 */
public class CopyListWithRandom {
    // 内部类
    public static class Node {
        public int val;
        public Node next;
        public Node random;

        public Node(int val) {
            this.val = val;
            this.next = null;
            this.random = null;
        }
    }

    // 方法一：使用容器的方法
    public static Node copyRandomList1(Node head) {
        // 存放老节点与新节点的容器，并且做到通过 old Node --> new Node
        HashMap<Node, Node> map = new HashMap<>();
        // 遍历链表第一边，目的：装入老节点与新节点。
        Node cur = head;
        while (cur != null) {
            map.put(cur, new Node(cur.val));
            cur = cur.next;
        }
        // 遍历链表第二遍，对新节点进行链接。
        cur = head;
        while (cur != null) {
            // cur:老节点
            // newNode:新节点
            // newNode.next -> cur.next的克隆节点
            // newNode.rand -> cur.rand的克隆节点
            Node newNode = map.get(cur);
            newNode.next = map.get(cur.next);
            newNode.random = map.get(cur.random);
            cur = cur.next;
        }
        return map.get(head);
    }
    // O(n)

    /*
    ---------------------------------------------------------------------------------------------------------
    */

    /*
    方法二：不使用容器的方法
    错误写法: 不可以边写random边分离,因为会导致后面的的random找不到正确的random。
     */
    public static Node copyRandomList2(Node head) {
        if (head == null) {
            return null;
        }
        // 遍历链表第一遍:将克隆节点夹在新节点之间
        Node cur = head;
        while (cur != null) {
            Node newNode = new Node(cur.val);
            newNode.next = cur.next;
            cur.next = newNode;
            cur = cur.next.next;
        }
        // 遍历第二遍:将克隆节点进行连接
        cur = head;
        Node res = head.next;
        while (cur != null) {
            // cur: 老节点
            // newNode: 克隆节点
            Node newNode = cur.next;
            Node curNext = newNode.next; // 先保存一下新节点的下一个老节点，方便老节点的回归。
            newNode.next = curNext == null ? null : curNext.next;
            newNode.random = cur.random == null ? null : cur.random.next;
            cur.next = curNext;
        }
        return res;
    }

    /*
    ---------------------------------------------------------------------------------------------------------
    */

    // 正确的写法：
    public static Node copyRandomList3(Node head) {
        if (head == null) {
            return null;
        }
        // 遍历链表第一遍:将克隆节点夹在新节点之间
        Node cur = head;
        while (cur != null) {
            Node newNode = new Node(cur.val);
            newNode.next = cur.next;
            cur.next = newNode;
            cur = newNode.next;
        }
        // 遍历第二遍调整新节点的random
        cur = head;
        while (cur != null) {
            Node newNode = cur.next;
            newNode.random = cur.random == null ? null : cur.random.next;
            cur = newNode.next;
        }
        // 遍历第三遍，新链表与旧链表分离
        cur = head;
        Node res = head.next;
        while (cur != null) {
            Node newNode = cur.next;
            Node nextNode = cur.next.next;
            cur.next = nextNode;
            newNode.next = nextNode == null ? null : nextNode.next;
            cur = nextNode;
        }
        return res;
    }
    // O(n)
}
