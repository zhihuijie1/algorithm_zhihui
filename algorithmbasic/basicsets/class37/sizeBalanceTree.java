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

        public SBTNode<K, V> delete(SBTNode<K, V> cur, K key, V value) {

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

            } else if (rightLeftSize > leftSzie) {// RL

            } else if (rightRightSize > leftSzie) {// RR

            }
            return cur;
        }

        public SBTNode<K, V> leftRotate(SBTNode<K, V> cur) {

        }

        public SBTNode<K, V> rightRotate(SBTNode<K, V> cur) {

        }
    }
}
