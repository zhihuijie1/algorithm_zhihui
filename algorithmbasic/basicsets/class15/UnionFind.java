package algorithmbasic.basicsets.class15;

import java.util.HashMap;
import java.util.List;
import java.util.Stack;

// 课上讲的并查集实现
// 请务必看补充的Code06_UnionFind
// 那是数组实现的并查集，并且有测试链接
// 可以直接通过
// 这个文件的并查集是用map实现的
// 但是笔试或者平时用的并查集一律用数组实现
// 所以Code06_UnionFind更具实战意义
// 一定要看！
public class UnionFind<V> {

    /**
     * 属性
     */
    public HashMap<V, V> fatherMap;
    //father:<v1,v2>：指的是：v1的父亲是v2,注意这里的父亲是直系父亲.
    //HashMap<5, 2>：5->2
    //HashMap<2, 4>：2->4
    //HashMap<4, 6>：4->6
    public HashMap<V, Integer> sizeMap;
    //size:<V,Integer>:size里面装的是所有集合的头部节点，以及该头部节点下集合的元素个数。
    //注意不是头部节点不可以放入size中。

    /**
     * 构造器
     */
    public UnionFind(List<V> list) {
        //创建两个map。
        fatherMap = new HashMap<>();
        sizeMap = new HashMap<>();
        //遍历一遍链表，将链表中的数据放入两个map中。
        for (V l : list) {
            //当只有一个节点的时候，这个节点的头部节点是他自己，即自己指向自己。
            fatherMap.put(l, l);
            sizeMap.put(l, 1);
        }
    }

    /**
     * findAncestor方法
     * 传入一个节点，然后从这个节点开始一直往上找，直到直到最上边为止，返回最上面的节点。
     */
    public V findAncestor(V cur) {
        //创建一个容器，顺着当前的节点cur开始往上找，将途中经过的节点都放入这个容器中。
        Stack<V> path = new Stack<>();
        //循环的条件是：当当前节点的父亲就是当前节点时，说明来到了最顶部。
        while (cur != fatherMap.get(cur)) {
            //将cur讲过的节点放入容器中。
            path.push(cur);
            //cur来到其直系父亲节点。
            cur = fatherMap.get(cur);
        }
        //这是一个优化部分。
        //我们将途径的每个节点都指向祖先节点，这样就降低了路径的长度，使复杂度更低。
        //假设从cur开始到顶部的长度是n，第一次进行向上寻找的时候复杂度是O(n),但是以后寻找的时候复杂度就会变成O(1)。
        while (!path.isEmpty()) {
            fatherMap.put(path.pop(), cur);
        }
        return cur;
    }

    /**
     * isSameSet方法
     * 传入两个值，判断这两个值是否是在一个集合里。
     */
    public boolean isSameSet(V value1, V value2) {
        return findAncestor(value1) == findAncestor(value2);
    }

    /**
     * union方法
     * 给你两个值，将两个值所在的集合进行合并。
     */
    public void union(V a, V b) {
        //father1指向的是a元素所在集合的头部节点。
        //father2指向的是b元素所在集合的头部节点。
        V father1 = findAncestor(a);
        V father2 = findAncestor(b);
        if (father1 != father2) {
            //size1是头部节点father1所在集合的大小。
            //size2是头部节点father2所在集合的大小。
            int size1 = sizeMap.get(father1);
            int size2 = sizeMap.get(father2);
            //big指向的是集合元素多的头部节点。
            //small指向的是集合元素少的头部节点。
            V big = size1 > size2 ? father1 : father2;
            V small = big == father1 ? father2 : father1;
            //small指向big
            //这里使用map实现的指针的功能。
            //因为small合并到big中，所以big这个头部节点的sizemap中元素个数要增多。
            sizeMap.put(big,size1+size2);
            //与此同时头部节点small应该指向big。
            fatherMap.put(small,big);
            //因为small指向big所以，small不再是头部节点，就将small从sizeMap中删除。
            sizeMap.remove(small);
        }
    }

    /**
     * sets方法
     * 返回的是一共有几个集合/几个头部节点。
     */
    public int sets() {
        return sizeMap.size();
    }
}
