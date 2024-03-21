package algorithmbasic.basicsets.class37;

public class sizeBalanceTree {
    public static class SBTNode<K extends Comparable<K>, V> {
        public K key;
        public V value;
        public int size;
        public SBTNode<K, V> left;
        public SBTNode<K, V> right;

        public SBTNode(K key, V value) {
            this.key = key;
            this.value = value;
            size = 0;
        }
    }

    public static class SizeBalancedTreeMap<K extends Comparable<K>, V> {
        public SBTNode<K, V> add(SBTNode<K, V> cur, K key, V value) {
            if (cur == null) {
                return new SBTNode<>(key, value);
            }
            if (cur.key.compareTo(key) < 0) {
                cur.right = add(cur.right, key, value);
            } else {
                cur.left = add(cur.left, key, value);
            }
            cur.size++;
            return mainTain(cur);
        }

        public SBTNode<K, V> delete(SBTNode<K, V> cur, K key) {
            if (cur.key.compareTo(key) < 0) {
                cur.right = delete(cur.right, key);
            } else if (cur.key.compareTo(key) > 0) {
                cur.left = delete(cur.left, key);
            } else {
                if (cur.left == null && cur.right == null) {
                    return null;
                } else if (cur.left == null) {
                    return cur.right;
                } else if (cur.right == null) {
                    return cur.left;
                } else {
                    // --------------------- begin - hardThinking---------------------
                    SBTNode<K, V> des = cur.right;
                    SBTNode<K, V> pre = null;
                    while (des.left != null) {
                        pre = des;
                        pre.size--;
                        des = des.left;
                    }
                    if (pre != null) {
                        pre.left = des.right;
                        des.right = cur.right;
                    }
                    des.left = cur.left;
                    des.size = cur.left.size + (des.right == null ? 0 : des.right.size) + 1;
                    cur = des;
                    // --------------------- end ---------------------
                }
            }
            return cur;
        }

        public SBTNode<K, V> mainTain(SBTNode<K, V> cur) {
            if (cur == null) {
                return null;
            }
            int leftSzie = cur.left == null ? 0 : cur.left.size;
            int leftLeftSize = cur.left != null && cur.left.left != null ? cur.left.left.size : 0;
            int leftRightSize = cur.left != null && cur.left.right != null ? cur.left.right.size : 0;
            int rightSzie = cur.right == null ? 0 : cur.right.size;
            int rightLeftSize = cur.right != null && cur.right.left != null ? cur.right.left.size : 0;
            int rightRightSize = cur.right != null && cur.right.right != null ? cur.right.right.size : 0;

            if (leftLeftSize > rightSzie) {// LL
                cur = rightRotate(cur);
                cur.left = mainTain(cur.left);
                cur = mainTain(cur);
            } else if (leftRightSize > rightSzie) {// LR
                cur.left = leftRotate(cur.left);
                cur = rightRotate(cur);
                cur.left = mainTain(cur.left);
                cur.right = mainTain(cur.right);
                cur = mainTain(cur);
            } else if (rightLeftSize > leftSzie) {// RL
                cur.right = rightRotate(cur.right);
                cur = leftRotate(cur);
                cur.left = mainTain(cur.left);
                cur.right = mainTain(cur.right);
                cur = mainTain(cur);
            } else if (rightRightSize > leftSzie) {// RR
                cur = leftRotate(cur);
                cur.left = mainTain(cur.left);
                cur = mainTain(cur);
            }
            return cur;
        }

        public SBTNode<K, V> leftRotate(SBTNode<K, V> cur) {
            if (cur == null) {
                return null;
            }
            SBTNode<K, V> right = cur.right;
            cur.right = right.left;
            right.left = cur;
            right.size = cur.size;//cur之前是整棵树的头节点，size是整棵树的size
            cur.size = (cur.right == null ? 0 : cur.right.size) + (cur.left == null ? 0 : cur.left.size) + 1;
            return right;
        }

        public SBTNode<K, V> rightRotate(SBTNode<K, V> cur) {
            if (cur == null) {
                return null;
            }
            SBTNode<K, V> left = cur.left;
            cur.left = left.right;
            left.right = cur;
            left.size = cur.size;//cur之前是整棵树的头节点，size是整棵树的size
            cur.size = (cur.left == null ? 0 : cur.left.size) + (cur.right == null ? 0 : cur.right.size) + 1;
            return left;
        }
    }
}