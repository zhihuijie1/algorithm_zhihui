package algorithmbasic.basicsets.class9;

// 前缀树
public class Trie {
    // 节点内部类
    public static class Node {
        int pass; // 经过该节点的次数
        int end; // 以该节点结尾的次数
        Node[] nexts; // 存放该节点通往下一节点的路径

        public Node() {
            this.pass = 0;
            this.end = 0;
            /*
            当字母都是小写字母：
            0 -> a
            1 -> b
            2 -> c
            .
            .
            25 -> z
            当nexts[i] == null -> 没有这条路
             */
            nexts = new Node[26];
        }
    }

    // 属性：
    private static Node root; // 头节点

    // 构造器：
    public Trie() {
        root = new Node(); // 先建好头节点
    }

    // 方法：
    // 插入字符串方法
    public static void insert(String word) {
        if (word == null) {
            return;
        }
        char[] str = word.toCharArray(); // "hello" -> ['h','e','l','l','o']
        Node cur = root; // cur:一指针指向当前操作的节点。
        cur.pass++;
        for (int i = 0; i < str.length; i++) {
            int path = str[i] - 'a'; // 当前字符走那条路。
            if (cur.nexts[path] == null) {
                cur.nexts[path] = new Node();
            }
            cur = cur.nexts[path];
            cur.pass++;
        }
        cur.end++;
    }

    // 寻找这个单词出现过几次
    public static int search(String word) {
        if (word == null) {
            return 0;
        }
        char[] str = word.toCharArray(); // "hello" -> ['h','e','l','l','o']
        Node cur = root;
        for (int i = 0; i < str.length; i++) {
            int path = str[i] - 'a'; // 当前字符走那条路。
            if (cur.nexts[path] == null) {
                return 0; // 走着走着没路了说明一定没有这个字符串。“如果有这个字符串一定会走完for循环”。
            }
            cur = cur.nexts[path];
        }
        return cur.end;
    }

    // 求所有加入的字符串中有多少以pre这个字符作为前缀的。
    public static int prefixNumber(String pre) {
        if (pre == null) {
            return 0;
        }
        char[] str = pre.toCharArray();
        Node cur = root;
        for (int i = 0; i < str.length; i++) {
            int path = str[i] - 'a';
            if (cur.nexts[path] == null) {
                return 0;
            }
            cur = cur.nexts[path];
        }
        return cur.pass;
    }

    // 删除一个字符串。
    public static void remove(String word) {
        if (search(word) == 0) {
            return;
        }
        char[] str = word.toCharArray();
        Node cur = root;
        cur.pass--;
        for (int i = 0; i < str.length; i++) {
            int path = str[i] - 'a';
            if (--cur.nexts[path].pass == 0) {
                cur.nexts[path] = null; // 防止内存泄漏(防止浪费大量内存)
                return;
            }
            cur = cur.nexts[path];
        }
        cur.end--;
    }
}
