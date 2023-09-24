package algorithmbasic.basicsets.class11;

public class Test {
    // 内部类
    public static class Node {
        int value;
        Node next;

        public Node(int data) {
            this.value = data;
        }
    }

    // mian方法
    public static void main(String[] args) {
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
        System.out.println(getLoopNode1(head1));
        System.out.println(getLoopNode2(head1));
        System.out.println(getLoopNode1(head2));
        System.out.println(getLoopNode2(head2));
        // 1->2->3->4->5->6->7->4...
        head1 = new Node(1);
        head1.next = new Node(2);
        head1.next.next = new Node(3);
        head1.next.next.next = new Node(4);
        head1.next.next.next.next = new Node(5);
        head1.next.next.next.next.next = new Node(6);
        head1.next.next.next.next.next.next = new Node(7);
        head1.next.next.next.next.next.next = head1.next.next.next; // 7->4

        // 0->9->8->2...
        head2 = new Node(0);
        head2.next = new Node(9);
        head2.next.next = new Node(8);
        head2.next.next.next = head1.next; // 8->2
        System.out.println(getLoopNode1(head1));
        System.out.println(getLoopNode2(head1));
        System.out.println(getLoopNode1(head2));
        System.out.println(getLoopNode2(head2));

        // 0->9->8->6->4->5->6..
        head2 = new Node(0);
        head2.next = new Node(9);
        head2.next.next = new Node(8);
        head2.next.next.next = head1.next.next.next.next.next; // 8->6
        System.out.println(getLoopNode1(head1));
        System.out.println(getLoopNode2(head1));
        System.out.println(getLoopNode1(head2));
        System.out.println(getLoopNode2(head2));
    }

    public static Node getLoopNode1(Node head) {
        if (head == null || head.next == null || head.next.next == null) {
            return null;
        }
        // n1 慢  n2 快
        Node slow = head.next; // n1 -> slow
        Node fast = head.next.next; // n2 -> fast
        while (slow != fast) {
            if (fast.next == null || fast.next.next == null) {
                return null;
            }
            fast = fast.next.next;
            slow = slow.next;
        }
        // slow fast  相遇
        fast = head; // n2 -> walk again from head
        while (slow != fast) {
            slow = slow.next;
            fast = fast.next;
        }
        return slow;
    }

    public static Node getLoopNode2(Node head) {
        if(head == null || head.next == null || head.next.next == null) {
            return null;
        }
        Node slow = head;
        Node fast = head;
        while(fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if(fast == slow) {
                fast = head;
                while(fast != slow) {
                    fast = fast.next;
                    slow = slow.next;
                }
                return fast;
            }
        }
        return null;
    }
}
