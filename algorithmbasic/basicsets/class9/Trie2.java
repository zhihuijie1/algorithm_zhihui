package algorithmbasic.basicsets.class9;

import java.util.HashMap;

public class Trie2 {
    // 内部类
    public static class Node {
        int pass;
        int end;
        HashMap<Integer, Node> map;

        public Node() {
            this.pass = 0;
            this.end = 0;
            this.map = new HashMap<>();
        }
    }

    // 属性：
    static Node root;

    // 构造器：
    public Trie2() {
        root = new Node();
    }

    // 方法：
    public static void insert(String word) {
        if (word == null) {
            return;
        }
        char[] str = word.toCharArray();
        Node cur = root;
        cur.pass++;
        for (char c : str) {
            int path = c;
            if (cur.map.get(path) == null) {
                cur.map.put(path, new Node());
            }
            cur = cur.map.get(path);
            cur.pass++;
        }
        cur.end++;
    }

    public static int search(String word) {
        if (word == null) {
            return 0;
        }
        char[] str = word.toCharArray();
        Node cur = root;
        for (char c : str) {
            int path = c;
            if (!cur.map.containsKey(path)) {
                return 0;
            }
            cur = cur.map.get(path);
        }
        return cur.end;
    }

    public static int profixNumber(String word) {
        if (word == null) {
            return 0;
        }
        char[] str = word.toCharArray();
        Node cur = root;
        for (int i = 0; i < str.length; i++) {
            int path = str[i];
            if (!cur.map.containsKey(path)) {
                return 0;
            }
            cur = cur.map.get(path);
        }
        return cur.pass;
    }

    public static void remove(String word) {
        if (search(word) == 0) {
            return;
        }
        char[] str = word.toCharArray();
        Node cur = root;
        cur.pass--;
        for (int i = 0; i < str.length; i++) {
            int path = str[i];
            if (--cur.map.get(path).pass == 0) {
                cur.map.remove(path);
                return;
            }
            cur = cur.map.get(path);
        }
        cur.end--;
    }
}
