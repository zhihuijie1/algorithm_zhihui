package algorithmbasic.questionsets.class2;

public class CrossLinkedList {
    // Node class for the vertical list
    private static class Node {
        int value;
        Node next;
        Node prev;
        HorizontalList horList;

        Node(int value) {
            this.value = value;
            this.next = null;
            this.prev = null;
            this.horList = null;
        }
    }

    // HorizontalList class for the horizontal lists
    private static class HorizontalList {
        Node head;
        Node tail;
        int count;

        HorizontalList() {
            this.head = null;
            this.tail = null;
            this.count = 0;
        }

        void insert(int value) {
            Node newNode = new Node(value);
            if (head == null) {
                head = tail = newNode;
            } else {
                tail.next = newNode;
                newNode.prev = tail;
                tail = newNode;
            }
            count++;
        }

        // Implement other methods for HorizontalList if needed
    }

    private Node vHead;
    private Node vTail;
    private int vCount;

    public CrossLinkedList() {
        this.vHead = null;
        this.vTail = null;
        this.vCount = 0;
    }

    // Method to print the entire structure
    public void printStructure() {
        Node current = vHead;
        while (current != null) {
            System.out.print(current.value + " -> ");
            if (current.horList != null) {
                Node hCurrent = current.horList.head;
                while (hCurrent != null) {
                    System.out.print(hCurrent.value + " <-> ");
                    hCurrent = hCurrent.next;
                }
            }
            System.out.println("NULL");
            current = current.next;
        }
    }

    // Method to insert a node in the vertical list
    public void insertVertical(int value) {
        Node newNode = new Node(value);
        if (vHead == null) {
            vHead = vTail = newNode;
        } else {
            vTail.next = newNode;
            newNode.prev = vTail;
            vTail = newNode;
        }
        vCount++;

        // Check if we need to create a horizontal list at this node
        if (Math.log(vCount) / Math.log(2) % 1 == 0) {
            newNode.horList = new HorizontalList();
        }
    }

    // Method to insert into a horizontal list
    public void insertHorizontal(int verticalPos, int value) {
        if (verticalPos > vCount) {
            System.out.println("Vertical position out of bounds.");
            return;
        }

        Node current = vHead;
        for (int i = 1; i < verticalPos; i++) {
            current = current.next;
        }

        if (current.horList == null) {
            System.out.println("No horizontal list at this vertical position.");
            return;
        }

        current.horList.insert(value);
    }

    // Method to calculate the maximum length of the horizontal lists
    public int maxHorizontalLength() {
        int maxLength = 0;
        Node current = vHead;
        while (current != null) {
            if (current.horList != null) {
                maxLength = Math.max(maxLength, current.horList.count);
            }
            current = current.next;
        }
        return maxLength;
    }

    // Implement other methods like delete, update, and move if needed
}
