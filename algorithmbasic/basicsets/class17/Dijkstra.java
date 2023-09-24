package algorithmbasic.basicsets.class17;

import java.util.HashMap;


public class Dijkstra {
    //从head出发，所有head能到达的节点，生成到达每个节点的最小路径记录并返回
    public static HashMap<Node, Integer> dijkstra(Node head, int size) {
        //result记录最终要返回的结果。
        HashMap<Node, Integer> result = new HashMap<>();
        //创建好我的加强堆
        HeapGreater heapGreater = new HeapGreater(size);
        //遍历head节点的直接连接节点。
        for (Edge edge : head.edges) {
            heapGreater.addOrUpdateOrIgnore(edge.to, edge.weight);
        }
        while (!heapGreater.isEmpty()) {
            Record record = heapGreater.poll();
            result.put(record.node, record.distance);
            for (Edge edge : record.node.edges) {
                heapGreater.addOrUpdateOrIgnore(edge.to, edge.weight + record.distance);
            }
        }
        return result;
    }

    /**
     * Record内部类
     * 之所以创建这个内部类是因为：我想将节点与权重当作一个整体，通过加强堆的排序之后弹出的是一个整体，这样方便
     * 当然使用hashmap也可以。
     */

    public static class Record {
        public Node node;
        public int distance;

        public Record(Node node, int distance) {
            this.node = node;
            this.distance = distance;
        }
    }

    public static class HeapGreater {
        /**
         * 属性
         */
        Node[] nodes;
        //记录node所对应的位置。
        //如果这个node被弹出了，heapIndexMap中这个节点所对应的位置就定为 -1.原因是：便于我们判断这个节点来过。
        //之所以必须判断这个节点来过是因为，如果后面的某个节点的直接连接节点还是这个节点，如果没有这个判断，这个节点还会再一次的进入堆中
        //导致最终返回的map中正确的值被覆盖。
        HashMap<Node, Integer> heapIndexMap;
        //记录剩余节点的a到Node所对应的最小距离
        HashMap<Node, Integer> distanceMap;
        int usedSize;

        /**
         * 构造器
         */
        public HeapGreater(int size) {
            nodes = new Node[size];
            heapIndexMap = new HashMap<>();
            distanceMap = new HashMap<>();
            usedSize = 0;
        }

        /**
         * addOrUpdateOrIgnore方法
         * 传进来的是一个节点，以及a到该节点的距离。
         * 对于这个节点只会出现三种操作，要么这个节点已经被处理过了，那就不用管直接跳过，
         * 要么这个节点从来没有进来过，那就放入堆中，
         * 要么这个节点的a到该节点的距离比堆中记录的要小，那就更新堆中的记录。
         */
        public void addOrUpdateOrIgnore(Node node, int weight) {
            //传进来的节点要不就放入加强堆中，要不就更新加强堆中的值，要么就被忽略。
            //如果这个节点在堆中，看看a到该节点的距离比堆中记录的要小，那就更新堆中的记录。
            if (inHeap(node)) {
                distanceMap.put(node, Math.min(weight, distanceMap.get(node)));
                //因为堆中的记录只会变的更小，所以只会向上调整。
                insertHeapify(heapIndexMap.get(node));
            }
            //这个节点从来没有进来过。
            //那就加入这个节点。
            if (!isEntered(node)) {
                nodes[usedSize] = node;
                distanceMap.put(node, weight);
                heapIndexMap.put(node, usedSize);
                insertHeapify(usedSize++);
            }
        }

        /**
         * 向上调整
         */
        private void insertHeapify(int index) {
            int father = (index - 1) / 2;
            while (distanceMap.get(nodes[father]) < distanceMap.get(nodes[index])) {
                swap(nodes, father, index);
                index = father;
                father = (index - 1) / 2;
            }
        }

        public void swap(Node[] nodes, int fatehr, int index) {
            heapIndexMap.put(nodes[fatehr], index);
            heapIndexMap.put(nodes[index], fatehr);
            Node temp = nodes[fatehr];
            nodes[fatehr] = nodes[index];
            nodes[index] = temp;
        }

        /**
         * 向下调整
         */
        private void heapify(int index, int size) {
            int lc = index * 2 + 1;
            while (lc < size) {
                int smallChild = lc + 1 < size ? (distanceMap.get(nodes[lc]) < distanceMap.get(nodes[lc + 1]) ? lc : lc + 1) : (lc);
                if (distanceMap.get(index) > distanceMap.get(smallChild)) {
                    swap(nodes, index, smallChild);
                    index = smallChild;
                    lc = index * 2 + 1;
                } else {
                    break;
                }
            }
        }

        /**
         * 判断当前节点是否进来过。
         */
        private boolean isEntered(Node node) {
            return heapIndexMap.containsKey(node);
        }

        /**
         * 判断当前节点是否在堆上
         */
        private boolean inHeap(Node node) {
            return isEntered(node) && heapIndexMap.get(node) != -1;
        }

        /**
         * poll()方法
         */
        public Record poll() {
            Record record = new Record(nodes[0], distanceMap.get(nodes[0]));
            distanceMap.remove(nodes[0]);
            heapIndexMap.put(nodes[0], -1);
            swap(nodes, 0, usedSize - 1);
            heapify(0, --usedSize);
            return record;
        }

        /**
         * isEmpty()方法
         */
        public boolean isEmpty() {
            return this.usedSize == 0;
        }
    }
}