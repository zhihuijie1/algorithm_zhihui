package algorithmbasic.basicsets.class37;

import java.lang.reflect.Array;
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
        //添加一个节点
        public void put(K key, V value) {

        }
        //删除一个节点
        public void remove(K key) {

        }
    }
}
