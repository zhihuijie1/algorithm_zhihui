package algorithmbasic.questionsets.class1;

/**
 * 要求构建一个数据结构，竖向是一个双向链表，在竖向链表的第2,4,8,16，以此类推构建横向双向链表
 * 1.提供整个结构的打印函数
 * 2.提供所有链表的插入、删除、修改和移动操作的函数
 * 3.可以求出横向的最大长度
 * 4.提供黑框指令界面
 */
public class SpecialLinkedList {
    //竖向链表的节点
    public static class VerticalNode {
        VerticalNode pre;
        VerticalNode next;
        int value;
        HorizontalList horizontalList;
        int Hindex; // 竖向Node锁定横向Node的位置

        public VerticalNode(int value) {
            this.value = value;
        }
    }

    //横向链表的节点
    public static class HorizontalNode {
        HorizontalNode pre;
        HorizontalNode next;
        HorizontalNode head;
        HorizontalNode tail;
        int value;

        public HorizontalNode(int value) {
            this.value = value;
        }
    }

    //横向链表
    public static class HorizontalList {
        HorizontalNode head;
        HorizontalNode tail;
        int length;

        //尾插
        public void insertHorizontalListTail(int value) {
            HorizontalNode hNode = new HorizontalNode(value);
            if (head == null && tail == null) {
                head = hNode;
                tail = hNode;
            } else {
                hNode.pre = tail;
                tail.next = hNode;
                tail = hNode;
            }
            length++;
        }

        //尾删
        public void deleteHorizontalListTail() {
            if (head == null && tail == null) {
                return;
            } else if (head == tail) {
                head = null;
                tail = null;
                length--;
            } else {
                HorizontalNode preTail = tail.pre;
                tail.pre = null;
                preTail.next = null;
                tail = preTail;
                length--;
            }
        }

        //修改
        public int updateHorizontalList(int index, int value) {
            if (index < 0) {
                return -2;
            }
            HorizontalNode cur = head;
            while (cur != null && index > 1) {
                cur = cur.next;
                index--;
            }
            if (cur == null) {
                return -1;
            }
            cur.value = value;
            return 1;
        }

        //移动
        public int move(VerticalList verticalList, int index, String direction, int step) {
            VerticalNode vcur = verticalList.head;
            while (vcur != null && index > 1) {
                vcur = vcur.next;
                index--;
            }
            if (vcur == null) {
                return -3; // index太大
            }
            if (vcur.horizontalList == null) {
                return -4; // 没有水平链表
            }
            if (step > vcur.horizontalList.length) {
                return -5; // 说品步数太大
            }
            // ---------------------- //
            if (direction.equals("left")) {
                int HorIndex = vcur.Hindex;
                HorizontalNode hCur = vcur.horizontalList.head;
                while (hCur != null && HorIndex > 0) {
                    hCur = hCur.next;
                    HorIndex--;
                }
                while (hCur != null && step > 0) {
                    hCur = hCur.next;
                    step--;
                }
                vcur.Hindex = step + vcur.Hindex;

            } else if (direction.equals("right")) {
                int HorIndex = vcur.Hindex;
                HorizontalNode hCur = vcur.horizontalList.head;
                while (hCur != null && HorIndex > 0) {
                    hCur = hCur.next;
                    HorIndex--;
                }
                while (hCur != null && step > 0) {
                    hCur = hCur.pre;
                    step--;
                }
                vcur.Hindex = vcur.Hindex - step;
            } else {
                return -1; // 输入错误
            }
            return 0;
        }
    }

    //纵向链表
    public static class VerticalList {
        VerticalNode head = null;
        VerticalNode tail = null;

        //尾插
        public void insertVerticalListTail(int value) {
            if (head == null && tail == null) {
                VerticalNode vNode = new VerticalNode(value);
                head = vNode;
                tail = vNode;
            } else {
                VerticalNode vNode = new VerticalNode(value);
                vNode.pre = tail;
                tail.next = vNode;
                tail = vNode;
            }
        }

        //尾删
        public void deleteVerticalListTail() {
            if (head == null && tail == null) {
                return;
            } else if (head == tail) {
                head = null;
                tail = null;
            } else {
                VerticalNode preTail = tail.pre;
                tail.pre = null;
                preTail.next = null;
                tail = preTail;
            }
        }

        //修改
        public int updateVerticalList(int index, int value) {
            if (index < 0) {
                return -2;
            }
            VerticalNode cur = head;
            while (cur != null && index > 1) {
                cur = cur.next;
                index--;
            }
            if (cur == null) {
                return -1;
            }
            cur.value = value;
            return 1;
        }

        //打印所有链表
        public void printAllList() {
            VerticalNode cur = head;
            while (cur != null) {
                if (cur.horizontalList != null) {
                    HorizontalNode ccur = cur.horizontalList.head;
                    while (ccur != null) {
                        if (ccur == cur.horizontalList.tail) {
                            System.out.println(ccur.value);
                        } else if (ccur == cur.horizontalList.head) {
                            System.out.println("#Begin: " + ccur.value + "<->");
                        } else {
                            System.out.println(ccur.value + "<->");
                        }
                        ccur = ccur.next;
                    }
                    System.out.println(" #END");
                } else {
                    if (cur == tail) {
                        System.out.println(cur.value);
                    } else {
                        System.out.println(cur.value + "<<--->>");
                    }
                }
                cur = cur.next;
            }
        }
    }

    //测试
    public static void main(String[] args) {

    }
}
