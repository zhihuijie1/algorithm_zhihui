package algorithmbasic.basicsets.class37;

import java.util.ArrayList;

public class SkipList {
    //跳表的节点
    public static class SkipListNode<K extends Comparable<K>, V> {
        public K key;
        public V value;
        public ArrayList<SkipListNode<K, V>> nextNodes;

        public SkipListNode(K key, V value) {
            this.key = key;
            this.value = value;
            nextNodes = new ArrayList<SkipListNode<K, V>>();
        }
    }

    //大跳表
    public static class SkipListMap<K extends Comparable<K>, V> {
        public static final double PROBABILITY = 0.5;
        public SkipListNode<K, V> head;
        public int maxLevel;
        public int size;

        public SkipListMap() {
            //head可以看成是最左侧的一个平台
            this.head = new SkipListNode<>(null, null);
            head.nextNodes.add(null);//0层
            this.size = 0;
            this.maxLevel = 0;
        }

        //添加一个节点
        public void put(K key, V value) {
            if (key == null) {
                return;
            }
            //首先判断大跳表中是否已经有这个key了，有的话就直接更新value即可
            //方法：找到最底层小于key的最右节点，拿到其下一节点与key作比较。
            SkipListNode<K, V> less = mostRightLessNodeInTree(key);
            //less.nextNodes.get(0) --
            SkipListNode<K, V> find = less.nextNodes.get(0);
            if (find.key.compareTo(key) == 0) {
                find.value = value;
            } else {
                //方法：随机生成一个层数，然后由上往下一一插入节点
                int newNodeLevel = 0;
                while (Math.random() < PROBABILITY) {
                    newNodeLevel++;
                }
                //可能随机生成的这个层数比当前跳表的最大层高还要高，那就调整head的层数，以及最大高度
                while (newNodeLevel > maxLevel) {
                    maxLevel++;
                    head.nextNodes.add(null);
                }
                SkipListNode<K, V> newNode = new SkipListNode<>(key, value);
                for (int i = 0; i < newNodeLevel; i++) {
                    newNode.nextNodes.add(null);
                }
                int level = maxLevel;
                SkipListNode<K, V> pre = head;
                while (level >= 0) {
                    //pre --> 当前层级小于待插入key的前继
                    pre = mostRightLessNodeInLevel(pre, key, level);
                    //pre.nextNodes.get(level) --> 得到pre的后继
                    //将newNode插入到后继与前继之间
                    if (level <= newNodeLevel) {
                        newNode.nextNodes.set(level, pre.nextNodes.get(level));
                        pre.nextNodes.set(level, newNode);
                    }
                    level--;
                }
                size++;
            }
        }

        //删除一个节点
        public void remove(K key) {
            //大跳表中是否有当前的key
            if (!containsKey(key)) {
                return;
            }
            size--;
            //从最上层开始，找到key删除，下一层继续，直到最底层
            int level = maxLevel;
            SkipListNode<K, V> pre = head;
            while (level >= 0) {
                //pre->前驱
                pre = mostRightLessNodeInLevel(pre, key, level);
                //now->当前节点
                SkipListNode<K, V> next = pre.nextNodes.get(level);
                //next.nextNodes.get(level)的后继
                if (next != null && next.key.compareTo(key) == 0) {
                    pre.nextNodes.set(level, next.nextNodes.get(level));
                }
                //在level层只有一个节点了，就是默认节点head --> 这一层没有东西了 直接删除这一层
                if (level != 0 && pre == head && pre.nextNodes.get(level) == null) {
                    head.nextNodes.remove(level);
                    maxLevel--;
                }
                level--;
            }
        }

        //从最高层开始，一路找下去，
        //最终，找到第0层的<key的最右的节点
        public SkipListNode<K, V> mostRightLessNodeInTree(K key) {
            if (key == null) {
                return null;
            }
            int level = maxLevel;
            SkipListNode<K, V> cur = head;
            while (level >= 0) {
                cur = mostRightLessNodeInLevel(cur, key, level);
                level--;
            }
            return cur;
        }

        // 在level层里，如何往右移动
        // 现在来到的节点是cur，来到了cur的level层，在level层上，找到<key最后一个节点并返回
        public SkipListNode<K, V> mostRightLessNodeInLevel(SkipListNode<K, V> cur, K key, int level) {
            if (key == null) {
                return null;
            }
            SkipListNode<K, V> pre = null;
            cur = cur.nextNodes.get(level);
            while (cur.key.compareTo(key) < 0) {
                pre = cur;
                cur = cur.nextNodes.get(level);
            }
            return pre;
        }

        public Boolean containsKey(K key) {
            if (key == null) {
                return false;
            }
            SkipListNode<K, V> less = mostRightLessNodeInTree(key);
            SkipListNode<K, V> find = less.nextNodes.get(0);
            return find != null && find.key.compareTo(key) == 0;
        }
    }
}
