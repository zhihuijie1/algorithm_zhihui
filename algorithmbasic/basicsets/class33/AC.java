package algorithmbasic.basicsets.class33;

import java.util.LinkedList;
import java.util.Queue;

public class AC {
    /** AC自动机 -> 给我一个大文章，我可以迅速的找到给定关键词的次数 **/


    /**
     * 思路
     * 1: 根据给出的关键词创建前缀树
     * 2: 广度优先遍历构建fail指针
     * 3：遍历大文章来计算关键词是否出现
     */

    public static class Node {
        public Node[] nexts;
        Node fail;
        int end;//多少个字符串以当前节点为结尾

        public Node() {
            nexts = new Node[26];
            fail = null;
            end = 0;
        }
    }

    public static class ACAutomation {
        //  搭建正确的容器
        //1 根据关键词创建前缀树
        //2 广度优先遍历连接每一个Node节点的fail指针

        //  遍历大文章来计算有多少关键字出现

        public static Node root;//根节点
        /**
         * 1:根据给出的关键字创建前缀树
         **/
        public static void insert(String s) {
            char[] str = s.toCharArray();
            Node cur = root;
            for (int i = 0; i < str.length; i++) {
                int index = str[i] - 'a';
                //有当前的路径，就走当前的路径
                if(cur.nexts [index] != null) {
                    cur = cur.nexts[index];
                    continue;
                }
                //没有当前的路径，就创建当前的路径
                cur.nexts[index] = new Node();
                cur = cur.nexts[index];
            }
            //以当前字母结尾的关键字字数加1
            cur.end++;
        }
        /**
         * 2: 广度优先遍历构建fail指针
         * **/
        public static void build() {
            //广度优先遍历的神奇
            Queue<Node> queue = new LinkedList<>();
            root.fail = null;
            queue.add(root);
            while(queue.isEmpty()) {
                Node cur = queue.poll();
                for (int i = 0; i < 26; i++) {
                    if(cur.nexts[i] != null) {

                    }
                }
            }
        }
    }
}
