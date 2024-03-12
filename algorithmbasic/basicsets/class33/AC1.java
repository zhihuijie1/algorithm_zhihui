package algorithmbasic.basicsets.class33;

import java.util.LinkedList;
import java.util.Queue;


public class AC1 {

    public static class Node {
        public int end; // 有多少个字符串以该节点结尾
        public Node fail;
        public Node[] nexts;

        public Node() {
            end = 0;
            fail = null;
            nexts = new Node[26];
        }
    }

    public static class ACAutomation {
        //  搭建正确的容器
        //1 根据关键词创建前缀树
        //2 广度优先遍历连接每一个Node节点的fail指针

        //  遍历大文章来计算有多少关键字出现
        private Node root;

        public ACAutomation() {
            root = new Node();
        }

        //1 根据关键词创建前缀树
        // 你有多少个匹配串，就调用多少次insert
        public void insert(String s) {
            char[] str = s.toCharArray();
            Node cur = root;
            int index = 0;
            for (int i = 0; i < str.length; i++) {
                index = str[i] - 'a';
                if (cur.nexts[index] == null) {
                    Node next = new Node();
                    cur.nexts[index] = next;
                }
                cur = cur.nexts[index];
            }
            //只有以关键字最后一个字母结尾的end才会加1
            //比如abcd bcd cd 都是以 d 结尾的所以 以d结尾的end是3
            cur.end++;
        }

        //2 广度优先遍历连接每一个Node节点的fail指针
        public void build() {
            //广度优先遍历的利器->队列->先进先出
            Queue<Node> queue = new LinkedList<>();
            queue.add(root);
            Node cur = null;
            Node cfail = null;
            while (!queue.isEmpty()) {
                cur = queue.poll(); // 父；
                for (int i = 0; i < 26; i++) { // 下级所有的路
                    if (cur.nexts[i] != null) { // 该路下有子节点
                        cur.nexts[i].fail = root; // 初始时先设置一个值
                        cfail = cur.fail;
                        while (cfail != null) { // cur不是头节点
                            if (cfail.nexts[i] != null) {
                                cur.nexts[i].fail = cfail.nexts[i];
                                break;
                            }
                            cfail = cfail.fail;
                        }
                        queue.add(cur.nexts[i]);
                    }
                }
            }
        }

        //遍历大文章来计算有多少关键字出现
        public int containNum(String content) {
            char[] str = content.toCharArray();
            Node cur = root;
            Node follow = null;
            int index = 0;
            int ans = 0;
            for (int i = 0; i < str.length; i++) {
                index = str[i] - 'a';
                //如果当前路径走不通就跟着fail指针走向下一条路径
                while (cur.nexts[index] == null && cur != root) {
                    cur = cur.fail;
                }
                //1) 现在来到的路径是可以匹配的
                //2) 现在来到的路径是根节点
                cur = cur.nexts[index] != null ? cur.nexts[index] : root;
                follow = cur;
                //下面循环的目的是->
                while (follow != root) {
                    //当前节点或之前的节点没有找到完整的模式字符串，或者这个模式字符串已经被处理过了
                    if (follow.end == -1) {
                        break;
                    }
                    { // 不同的需求，在这一段{ }之间修改
                        ans += follow.end;
                        follow.end = -1;
                    } // 不同的需求，在这一段{ }之间修改
                    follow = follow.fail;
                }
            }
            return ans;
        }

    }

    public static void main(String[] args) {
        ACAutomation ac = new ACAutomation();

        ac.insert("ab");
        ac.insert("abc");
        ac.insert("bc");
        ac.build();
        System.out.println(ac.containNum("abcdefghiyghln"));
    }
}
