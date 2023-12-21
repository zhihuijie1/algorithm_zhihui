package algorithmbasic.questionsets.class1;

/**
 * 要求构建一个数据结构，竖向是一个双向链表，在竖向链表的第2,4,8,16，以此类推构建横向双向链表
 * 1.提供整个结构的打印函数
 * 2.提供所有链表的插入、删除、修改和移动操作的函数
 * 3.可以求出横向的最大长度
 * 4.提供黑框指令界面
 */
public class VerticalList {
    //---------------------------------- 竖向链表的节点 ----------------------------------
    public static class VerticalNode {
        VerticalNode pre;
        VerticalNode next;
        int value;
        HorizontalList horizontalList;
        int Hindex; // 竖向Node锁定横向Node的位置
        int Vindex; // 竖向链表的第几个Node

        public VerticalNode(int value) {
            this.value = value;
        }
    }

    //---------------------------------- 横向链表的节点----------------------------------
    public static class HorizontalNode {
        HorizontalNode pre;
        HorizontalNode next;
        int value;

        public HorizontalNode(int value) {
            this.value = value;
        }
    }

    //---------------------------------- 横向链表----------------------------------
    public static class HorizontalList {
        HorizontalNode head;
        HorizontalNode tail;
        int length;
        int gg; // 水平位置的哪个值对应在竖向链表上

        //尾插
        public void insertHorizontalListTail(int value) {
            HorizontalNode hNode = new HorizontalNode(value);
            if (head == null && tail == null) {
                head = hNode;
                tail = hNode;
                this.gg = 0;
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
        public int move(String direction, int step) {
            if (direction.equals("left")) {
                int index = gg + step;
                if (index >= this.length) {
                    return -2; // step太大
                }
                gg += step;
            } else if (direction.equals("right")) {
                int index = gg - step;
                if (index < 0) {
                    return -2; // step太大
                }
                gg -= step;
            } else {
                return -1; // 方向输入错误
            }
            return 1;
        }
    }

    //---------------------------------- 纵向链表操作 ----------------------------------
    VerticalNode head = null;
    VerticalNode tail = null;

    //尾插
    public void insertVerticalListTail(int value) {
        if (head == null && tail == null) {
            VerticalNode vNode = new VerticalNode(value);
            head = vNode;
            tail = vNode;
            vNode.Vindex = 1;
        } else {
            VerticalNode vNode = new VerticalNode(value);
            vNode.pre = tail;
            tail.next = vNode;
            vNode.Vindex = tail.Vindex + 1; // 位置很重要
            tail = vNode;
            if (vNode.Vindex > 0 && (Math.log(vNode.Vindex) / Math.log(2)) % 1 == 0) {
                vNode.horizontalList = new HorizontalList();
                vNode.horizontalList.insertHorizontalListTail(value);

            }
        }
    }

    //尾删
    public void deleteVerticalListTail() {
        if (head == null && tail == null) {
            return;
        } else if (head == tail) {
            head.Vindex = 0;
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
            return -2; // index位置不合法
        }
        VerticalNode cur = head;
        while (cur != null && index > 1) {
            cur = cur.next;
            index--;
        }
        if (cur == null) {
            return -1; // index太大
        }
        cur.value = value;
        return 1; // 修改完成
    }

    //打印所有链表
    public void printAllList() {
        VerticalNode cur = head;
        while (cur != null) {
            if (cur.horizontalList != null) {
                int index = cur.horizontalList.gg;
                HorizontalNode ccur = cur.horizontalList.head;
                int count = 0;
                while (ccur != null) {
                    if (ccur == cur.horizontalList.head && ccur == cur.horizontalList.tail && count == index) {
                        System.out.print("[" + ccur.value + "#" + "]" + "<->");
                    } else if (ccur == cur.horizontalList.head) {
                        if (count == index) {
                            System.out.print("[" + ccur.value + "#" + "<->");
                        } else {
                            System.out.print("[" + ccur.value + "<->");
                        }
                    } else if (ccur == cur.horizontalList.tail) {
                        if (count == index) {
                            System.out.print(ccur.value + "#" + "]" + "<->");
                        } else {
                            System.out.print(ccur.value + "]" + "<->");
                        }
                    } else {
                        if (count == index) {
                            System.out.print(ccur.value + "#" + "<->");
                        } else {
                            System.out.print(ccur.value + "<->");
                        }

                    }
                    count++;
                    ccur = ccur.next;
                }
            } else {
                if (cur == tail) {
                    System.out.print(cur.value);
                } else {
                    System.out.print(cur.value + "<->");
                }
            }
            cur = cur.next;
        }
    }

    // 水平插入
    public void insertShuiPing(int vIndex, int value) {
        VerticalNode vcur = this.head;
        while (vcur != null && vIndex > 1) {
            vcur = vcur.next;
            vIndex--;
        }
        if (vcur == null) {
            return; // index太大
        }
        if (vcur.horizontalList == null) {
            return; // 没有水平链表
        }
        vcur.horizontalList.insertHorizontalListTail(value);
    }

    // 水平删除
    public void deleteShuiPing(int vIndex) {
        VerticalNode vcur = this.head;
        while (vcur != null && vIndex > 1) {
            vcur = vcur.next;
            vIndex--;
        }
        if (vcur == null) {
            return; // index太大
        }
        if (vcur.horizontalList == null) {
            return; // 没有水平链表
        }
        vcur.horizontalList.deleteHorizontalListTail();
    }

    // 水平修改
    public void updateShuiPing(int vIndex, int HrIndex, int value) {
        VerticalNode vcur = this.head;
        while (vcur != null && vIndex > 1) {
            vcur = vcur.next;
            vIndex--;
        }
        if (vcur == null) {
            return; // index太大
        }
        if (vcur.horizontalList == null) {
            return; // 没有水平链表
        }
        vcur.horizontalList.updateHorizontalList(HrIndex, value);
    }

    //水平移动
    public void moveShuiPing(int index, String direction, int step) {
        VerticalNode vcur = head;
        while (vcur != null && index > 1) {
            vcur = vcur.next;
            index--;
        }
        if (vcur == null) {
            System.out.println("index太大");
            return;
        }
        if (vcur.horizontalList == null) {
            System.out.println("没有水平链表");
            return;
        }
        if (step > vcur.horizontalList.length) {
            System.out.println("步数太大");
            return;
        }
        int r = vcur.horizontalList.move(direction, step);
        if (r == 1) {
            HorizontalNode hCur = vcur.horizontalList.head;
            int c = vcur.horizontalList.gg;
            while (hCur != null && c > 0) {
                hCur = hCur.next;
                c--;
            }
            vcur.value = hCur.value;
            System.out.println("移动成功");
            return;
        }
        if (r == -1) {
            System.out.println("方向输入错误");
            return;
        }
        if (r == -2) {
            System.out.println("step太大");
            return;
        }
    }

    //横向链表的长度
    public int ShuipingLength(int vIndex) {
        VerticalNode vcur = this.head;
        while (vcur != null && vIndex > 1) {
            vcur = vcur.next;
            vIndex--;
        }
        if (vcur.horizontalList == null) {
            System.out.println("没有水平链表");
            return -1;
        }
        System.out.println("水平链表的长度是：" + vcur.horizontalList.length);
        return vcur.horizontalList.length;
    }

    //---------------------------------- 测试 ----------------------------------
    public static void main(String[] args) {
        VerticalList verticalList = new VerticalList();
        HorizontalList horizontalList = new HorizontalList();
        //竖向尾插
        verticalList.insertVerticalListTail(100);
        verticalList.insertVerticalListTail(200);
        verticalList.insertVerticalListTail(300);
        verticalList.insertVerticalListTail(400);
        verticalList.insertVerticalListTail(500);
        verticalList.insertVerticalListTail(600);
        verticalList.insertVerticalListTail(700);
        verticalList.insertVerticalListTail(800);
        verticalList.insertVerticalListTail(900);
        verticalList.insertVerticalListTail(1000);
        verticalList.insertVerticalListTail(1100);
        //横向尾插
        verticalList.insertShuiPing(2, 1);
        verticalList.insertShuiPing(2, 2);
        verticalList.insertShuiPing(2, 3);
        verticalList.insertShuiPing(2, 4);
        verticalList.insertShuiPing(2, 5);
        verticalList.insertShuiPing(2, 6);
        verticalList.insertShuiPing(2, 7);
        verticalList.insertShuiPing(2, 8);
        verticalList.insertShuiPing(2, 9);
        verticalList.insertShuiPing(2, 10);

        verticalList.insertShuiPing(3, 1);
        verticalList.insertShuiPing(3, 2);
        verticalList.insertShuiPing(3, 3);

        verticalList.insertShuiPing(8, 1);
        verticalList.insertShuiPing(8, 2);
        verticalList.insertShuiPing(8, 3);
        verticalList.insertShuiPing(8, 4);
        verticalList.insertShuiPing(8, 5);
        verticalList.insertShuiPing(8, 6);
        verticalList.insertShuiPing(8, 7);
        verticalList.insertShuiPing(8, 8);
        verticalList.insertShuiPing(8, 9);
        verticalList.insertShuiPing(8, 10);
        System.out.println("初始链表打印");
        verticalList.printAllList();
        System.out.println();
        System.out.println("水平移动,第2行水平链表向左移动5步");
        verticalList.moveShuiPing(2, "left", 5);
        verticalList.printAllList();
        System.out.println();
        System.out.println("水平移动,第8行水平链表向右移动3步");
        verticalList.moveShuiPing(8, "right", 3);
        verticalList.printAllList();
        System.out.println();
        System.out.println("水平修改,第8行水平链表的第5个元素改为9999");
        verticalList.updateShuiPing(8, 5, 9999);
        verticalList.printAllList();
        System.out.println();
        System.out.println("竖向修改，竖向链表的第10个元素改为88888");
        verticalList.updateVerticalList(10, 88888);
        verticalList.printAllList();
        System.out.println();
        System.out.println("竖向删除");
        verticalList.deleteVerticalListTail();
        verticalList.printAllList();
        System.out.println();
        System.out.println("横向删除");
        verticalList.deleteShuiPing(2);
        verticalList.printAllList();
        System.out.println();
        System.out.println("第2个水平链表的长度");
        verticalList.ShuipingLength(2);
    }
}
