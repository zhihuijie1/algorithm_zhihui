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
                if (cur.nexts[index] != null) {
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
         **/
        public static void build() {
            //广度优先遍历的神奇
            Queue<Node> queue = new LinkedList<>();
            root.fail = null;
            queue.add(root);
            while (!queue.isEmpty()) {
                Node cur = queue.poll();
                for (int i = 0; i < 26; i++) {
                    if (cur.nexts[i] != null) {
                        Node cfail = cur.fail;
                        //处理当前节点的fail指针
                        //处理方法 - 父亲节点的fail指针指向的的节点有无对应的路径
                        //- 如果没有继续沿着fail指针找，直到回到root的fail指向null
                        cur.nexts[i].fail = root;
                        while (cfail != null) {
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
            int index = 0;
            Node cur = root;
            Node follow = null;
            int res = 0;

            for (int i = 0; i < str.length; i++) {
                index = str[i] - 'a';
                //当前节点是非根节点 与此同时 当前节点没有匹配成功
                while (cur.nexts[index] == null && cur != root) {
                    cur = cur.fail;
                }
                //当前节点匹配成功 || 当前节点是根节点
                //如果说当前节点匹配成功的话 --> 直接进入下一个节点 --> 然后转一圈 --> 转一圈的目的是 -> 寻找尽量多的匹配串
                cur = cur.nexts[index] != null ? cur.nexts[index] : root;
                follow = cur;
                while (follow != root) { //转了一圈回到了root说明该统计的都统计完了，或者说压根没有匹配的
                    if (follow.end == -1) {
                        break;
                    }
                    if (follow.end != 0) {
                        res += follow.end;
                        follow.end = -1;
                    }
                    follow = follow.fail;
                }
            }
            return res;
        }
    }
}
