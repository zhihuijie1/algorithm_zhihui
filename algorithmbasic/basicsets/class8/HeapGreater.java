package algorithmbasic.basicsets.class8;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

// 加强堆
public class HeapGreater<T> {
    private ArrayList<T> heap;
    private int heapSize;
    private HashMap<T, Integer> indexMap;
    private Comparator<? super T> comp;

    public HeapGreater(Comparator<? super T> comp) {
        this.heap = new ArrayList<>();
        indexMap = new HashMap<>();
        this.heapSize = 0;
        this.comp = comp;
    }

    public boolean isEmpty() {
        return this.heapSize == 0;
    }

    public int size() {
        return this.heapSize;
    }

    public boolean contains(T obj) {
        return this.indexMap.containsKey(obj);
    }

    public T peek() {
        return this.heap.get(0);
    }

    // logN
    public void push(T obj) {
        heap.add(obj);
        indexMap.put(obj, heapSize);
        heapInsert(heapSize++);
    }

    // logN
    public void heapInsert(int index) {
        int father = (index - 1) / 2;
        while (this.comp.compare(heap.get(index), heap.get(father)) < 0) {
            // 注意交换的时候，ArrayList中的K V需要交换，HashMap中的K V也需要交换。
            swap(father, index);
            index = father;
            father = (index - 1) / 2;
        }
    }

    // logN
    public T pop() {
        T ans = heap.get(0);
        swap(0, --heapSize);
        indexMap.remove(ans);
        // 注意：其实Arraylist里面已经有一个heapsize这样的尾指针，这里我们自己定义的heapsize只是为了方便我们找到尾节点下标。
        // 真正意义上删除一个节点还是需要Arraylist里面的尾指针进行操作的。
        heap.remove(heapSize); // 真正的将尾节点删除。
        heapFiy(0);
        return ans;
    }

    // logN
    public void heapFiy(int index) {
        int l = index * 2 + 1;
        while (l < heapSize) {
            int minSonIndex = l + 1 < heapSize ? (this.comp.compare(heap.get(l), heap.get(l + 1)) < 0 ? l : l + 1) : (l);
            if(this.comp.compare(heap.get(minSonIndex), heap.get(index)) < 0) {
                swap(minSonIndex, index);
                index = minSonIndex;
                l = index * 2 + 1;
            }else {
                break;
            }
        }
    }

    // logN
    public void remove(T obj) {
        // 得到节点的位置index
        int index = indexMap.get(obj);
        // 得到末尾的数 value
        T replace = heap.get(--heapSize);
        // 将hashmap中obj值以及heap中末尾的数直接删掉
        indexMap.remove(obj);
        // 真正的删除尾节点。
        heap.remove(heapSize);
        // 如果末尾节点值与obj是同一个,不需要一下操作
        if (obj != replace) {
            // 将结果塞回去
            heap.set(index, replace);
            indexMap.put(replace, index);
            resign(replace);
        }
    }

    // 重构造
    // 只会执行其中的一个，logN
    public void resign(T obj) {
        heapInsert(indexMap.get(obj));
        heapFiy(indexMap.get(obj));
    }

    // 请返回堆上的所有元素
    public List<T> getAllElements() {
        List<T> ans = new ArrayList<>();
        for (T c : heap) {
            ans.add(c);
        }
        return ans;
    }

    public void swap(int i, int j) {
        T value1 = heap.get(i);
        T value2 = heap.get(j);
        heap.set(i, value2);
        heap.set(j, value1);
        // HashMap新的值覆盖旧的值
        indexMap.put(value1, j);
        indexMap.put(value2, i);
    }
}
