package algorithmbasic.basicsets.class36;

import java.util.Map;

public class AVL {
    //AVL树 -- 平衡的搜索二叉树 -- 对树的平衡有极高的要求

    //注意：是必须可以比较的 -- 根据key的大小来判断到底是放在节点的左侧还是右侧 --
    //从而出现了大小位置的放置 -- 从而实现搜索的功能 -- 从而实现区间范围的查询
    public static class AvlNode<K extends Comparable<K>, V> {
        public K key;
        public V value;
        public AvlNode l;
        public AvlNode r;
        //平衡因子 -- 以当前节点为头节点的树的高度
        public Integer h;

        public AvlNode(K key, V value) {
            this.key = key;
            this.value = value;
            this.h = 0;
        }
    }

    public static class AvlTreeMap<K extends Comparable<K>, V> {
        AvlNode<K, V> root;

        //注意：1：搜索二叉树不接受重复的key 2：之所以有返回值是因为有可能换头
        //思路：采用递归的方法 --> 递：在往下递的过程中一定会将新的节点放在叶子节点。 归: 归的过程是精髓，在一个节点一个节点的往回归的过程中
        //不断的更新节点的h，然后判断树是否平衡，如果不平衡就进行调平
        public AvlNode<K, V> add(AvlNode<K, V> cur, K key, V value) {
            if (cur == null) {
                return new AvlNode(key, value);
            }
            if (cur.key.compareTo(key) < 0) {
                cur.l = add(cur.l, key, value);
            } else {
                cur.r = add(cur.r, key, value);
            }
            cur.h = Math.max((cur.l == null ? 0 : cur.l.h), (cur.r == null ? 0 : cur.r.h)) + 1;
            //maintain()函数会自动的判断当前的树是否平衡，若不平衡就调整平衡。
            return maintain(cur);
        }

        //删除一个节点
        //在cur这棵树上，删掉key所代表的节点
        //返回cur这棵树的新头部
        //每次返回都要前调整树的结构
        public AvlNode<K, V> delete(AvlNode<K, V> cur, K key) {
            if (cur.key.compareTo(key) < 0) {
                cur.r = delete(cur.r, key);
            } else if (cur.key.compareTo(key) > 0) {
                cur.l = delete(cur.l, key);
            } else {
                //找到要删除的节点了
                if (cur.l == null && cur.r == null) {
                    //return null;
                    cur = null;
                } else if (cur.l != null && cur.r == null) {
                    //return cur.l; 不要在这里直接进行返回,每一次的返回都应该是调整好当前树的高度以后返回
                    cur = cur.l;
                } else if (cur.r != null && cur.l == null) {
                    //return cur.r;
                    cur = cur.r;
                } else {
                    //剩下的情况是左边不是null 右边也不是null
                    AvlNode<K, V> des = cur.l;
                    while (des.l != null) {
                        des = des.l;
                    }
                    cur.r = delete(cur.r, des.key);
                    des.l = cur.l;
                    des.r = cur.r;
                    //用des将cur替代
                    cur = des;
                }
            }
            cur.h = Math.max((cur.l == null ? 0 : cur.l.h), (cur.r == null ? 0 : cur.r.h)) + 1;
            return maintain(cur);
        }

        //调整平衡的方法
        //maintain()函数会自动的判断当前的树是否平衡，若不平衡就调整平衡。
        public AvlNode<K, V> maintain(AvlNode<K, V> cur) {
            if (cur == null) {
                return null;
            }
            int leftHeight = cur.l != null ? cur.l.h : 0;
            int rightHeight = cur.r != null ? cur.r.h : 0;
            if (Math.abs(leftHeight - rightHeight) > 1) {
                if (leftHeight > rightHeight) {
                    int leftleftHeight = cur.l != null && cur.l.l != null ? cur.l.l.h : 0;
                    int leftrightHeight = cur.l != null && cur.l.r != null ? cur.l.r.h : 0;
                    //leftleftHeight > leftrightHeight -- LL
                    //leftleftHeight == leftrightHeight -- LL + LR并存
                    //leftleftHeight < leftrightHeight -- LR
                    if (leftleftHeight >= leftrightHeight) {
                        cur = rightRotate(cur);//用cur来接住当前的头
                    } else {
                        cur.l = leftRotate(cur.l); //用cur.l把头接住
                        cur = rightRotate(cur); //用cur把头接住
                    }
                } else {
                    int rightleftHeight = cur.r != null && cur.r.l != null ? cur.r.l.h : 0;
                    int rightrightHeight = cur.r != null && cur.r.r != null ? cur.r.r.h : 0;
                    if (rightleftHeight >= rightrightHeight) {
                        cur = leftRotate(cur); //用cur来接住当前的头
                    } else {
                        cur.r = rightRotate(cur.r); //用cur.r把头接住
                        cur = leftRotate(cur); //用cur把头接住
                    }

                }
            }
            return cur;
        }

        //左旋 -- LL型 | LL+LR型 --> 统统左旋
        public AvlNode<K, V> rightRotate(AvlNode<K, V> cur) {
            AvlNode left = cur.l;
            cur.l = left.r;
            left.r = cur;
            cur.h = Math.max((cur.l != null ? cur.l.h : 0), (cur.r != null ? cur.r.h : 0));
            left.h = Math.max((left.l != null ? left.l.h : 0), (left.r != null ? left.r.h : 0));
            return left;
        }

        //右旋 -- RR型 | RR+RL型 --> 统统右旋
        public AvlNode<K, V> leftRotate(AvlNode<K, V> cur) {
            AvlNode right = cur.r;
            cur.r = right.l;
            right.l = cur;
            cur.h = Math.max((cur.l != null ? cur.l.h : 0), (cur.r != null ? cur.r.h : 0));
            right.h = Math.max((right.l != null ? right.l.h : 0), (right.r != null ? right.r.h : 0));
            return right;
        }
    }
}
