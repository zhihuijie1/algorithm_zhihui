package algorithmbasic.basicsets.class11;

/*
给定两个可能有环也可能无环的单链表，头节点head1和head2
请实现一个函数，如果两个链表相交，请返回相交的第一个节点。如果不相交返回null
要求如果两个链表长度之和为N，时间复杂度请达到O(N)，额外空间复杂度请达到O(1)
 */
public class FindFirstIntersectNode {
    // 内部类
    public static class Node {
        int value;
        Node next;

        public Node(int data) {
            this.value = data;
        }
    }

    // 方法
    public static Node getIntersectNode(Node head1, Node head2) {
        // 1:判断两个链表是否有环。
        Node loop1 = getLoopNode(head1);
        Node loop2 = getLoopNode(head2);
        if (loop1 == null && loop2 == null) { //两个都无环
            Node res = noLoop(head1, head2);
            if (res != null) { // 相交
                return res;
            } else {
                return null;
            }
        } else if (loop1 != null && loop2 != null) { //两个都有环
            if (loop1 == loop2) {
                return getSameloopNode(head1, head2, loop1);
            } else { // 入环节点不同
                return bothLoop(head1, loop1, head2, loop2);
            }
        } else { //一个有环一个无环
            return null;
        }
    }

    // 判断两个链表是否有环无环。 有环 -> 返回入环节点node。无环 -> 返回null。
    public static Node getLoopNode(Node head) {
        if (head == null || head.next == null || head.next.next == null) {
            return null;
        }
        Node slow = head;
        Node fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (fast == slow) {
                fast = head;
                while (fast != slow) {
                    fast = fast.next;
                    slow = slow.next;
                }
                return fast;
            }
        }
        return null;
    }

    // 判断两个无环链表是否相交。 相交 -> 返回相交节点。不相交 -> 返回null.
    public static Node noLoop(Node head1, Node head2) {
        if (head1 == null || head2 == null) {
            return null;
        }
        // 计算两个链表的长度
        Node cur1 = head1;
        Node cur2 = head2;
        int n = 0;
        while (cur1 != null) {
            cur1 = cur1.next;
            n++;
        }
        // 计算出第一个链表的长度：n。
        while (cur2 != null) {
            cur2 = cur2.next;
            n--;
        }
        if (cur1 != cur2) {
            return null;
        }
        cur1 = n > 0 ? head1 : head2; // cur1指向长链表。
        cur2 = cur1 == head1 ? head2 : head1; // cur2指向短链表。
        n = Math.abs(n); // n是长链表长度 - 短链表长度。
        while (n > 0) {
            cur1 = cur1.next;
            n--;
        }
        // cur1与cur2此时长度相等。
        while (cur1 != cur2) {
            cur1 = cur1.next;
            cur2 = cur2.next;
        }
        return cur1;
    }

    // 两个有环链表，并且入环节点相等。返回相交节点。
    public static Node getSameloopNode(Node head1, Node head2, Node loop) {
        Node cur1 = head1;
        Node cur2 = head2;
        int n = 0;
        while (cur1 != loop) {
            n++;
            cur1 = cur1.next;
        }
        while (cur2 != loop) {
            n--;
            cur2 = cur2.next;
        }
        cur1 = n > 0 ? head1 : head2; // cur1指向长链表。
        cur2 = cur1 == head1 ? head2 : head1; //cur2指向短链表。
        n = Math.abs(n);
        while (n > 0) {
            cur1 = cur1.next;
            n--;
        }
        while (cur1 != cur2) {
            cur1 = cur1.next;
            cur2 = cur2.next;
        }
        return cur1;
    }

    // 两个有环链表，并且入环节点不相等。返回相交节点。
    public static Node bothLoop(Node head1, Node loop1, Node head2, Node loop2) {
        Node cur = loop1.next;
        while (cur != loop1) {
            if (cur == loop2) {
                return loop1;
            }
            cur = cur.next;
        }
        return null;
    }
//--------------------------------------------------------------对数器。
    public static void main(String[] args) {
        // 1->2->3->4->5->6->7->null
        Node head1 = new Node(1);
        head1.next = new Node(2);
        head1.next.next = new Node(3);
        head1.next.next.next = new Node(4);
        head1.next.next.next.next = new Node(5);
        head1.next.next.next.next.next = new Node(6);
        head1.next.next.next.next.next.next = new Node(7);

        // 0->9->8->6->7->null
        Node head2 = new Node(0);
        head2.next = new Node(9);
        head2.next.next = new Node(8);
        head2.next.next.next = head1.next.next.next.next.next; // 8->6
        System.out.println(getIntersectNode(head1, head2).value);

        // 1->2->3->4->5->6->7->4...
        head1 = new Node(1);
        head1.next = new Node(2);
        head1.next.next = new Node(3);
        head1.next.next.next = new Node(4);
        head1.next.next.next.next = new Node(5);
        head1.next.next.next.next.next = new Node(6);
        head1.next.next.next.next.next.next = new Node(7);
        head1.next.next.next.next.next.next.next = head1.next.next.next; // 7->4

        // 0->9->8->2...
        head2 = new Node(0);
        head2.next = new Node(9);
        head2.next.next = new Node(8);
        head2.next.next.next = head1.next; // 8->2
        int t = getIntersectNode(head1, head2).value;
        System.out.println(t);

        // 0->9->8->6->4->5->6..
        head2 = new Node(0);
        head2.next = new Node(9);
        head2.next.next = new Node(8);
        head2.next.next.next = head1.next.next.next.next.next; // 8->6
        System.out.println(getIntersectNode(head1, head2).value);

    }
}
