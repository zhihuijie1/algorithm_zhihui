package algorithmbasic.basicsets.class12;

// 一种看似高效其实搞笑的节点删除方式
public class DeleteNodeList {
    // 内部类
    public static class Node {
        int value;
        Node next;

        public Node(int data) {
            this.value = data;
        }
    }

    // 1: 给你一个头节点以及要删除的节点，其返回值必须是Node类型
    //    因为：如果要删除的节点是头节点，必须返回一个新头。
    public static Node deleteNode(Node head , Node note) {
        if(head == null || note == null) {
            return null;
        }
        if(head == note) {
            return head.next;
        }
        Node cur = head;
        while (cur.next != null && cur.next != note) {
            cur = cur.next;
        }
        if(cur.next == note) {
            cur.next = cur.next.next;
        }
        return head;
    }

    // 2:一种看似高效其实搞笑的节点删除方式
    //   只给你要删除的节点，然后让你删除该节点。
    // 1 2 3 4 5 6 7
    public static void deleteNode2(Node note) {
        if(note == null) {
            return;
        }
        // 最后一个节点
        if(note.next == null) {
            // 废了
            // note = null;实现不了，因为note只是一个引用的拷贝。
        }else {
            note.value = note.next.value;
            note.next = note.next.next;
        }
    }

    public static void main(String[] args) {
        Node n = new Node(12);
        deleteNode2(n);
    }
}
