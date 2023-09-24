package algorithmbasic.basicsets.class7;

public class Heap {
    //大根堆
    public static class MyMaxHeap {
        // 属性
        int[] arr;
        int heapsize;
        int limit;

        // 构造器
        public MyMaxHeap(int limit) {
            arr = new int[limit];
            this.limit = limit;
            heapsize = 0;
        }

        // 方法
        public boolean isEmpty() {
            return heapsize == 0;
        }

        public boolean isFull() {
            return heapsize == limit;
        }

        // 先放到完全二叉树的尾节点，然后再向上调整
        public void push(int value) {
            if (this.isFull()) {
                throw new RuntimeException("堆满");
            }
            arr[heapsize] = value;
            heapInsert(arr,heapsize++);
        }

        // 向上调整
        private void heapInsert(int[] arr, int index) {
            int father = (index - 1) / 2;
            while (arr[father] < arr[index]) {
                swap(arr, father, index);
                index = father;
                father = (index - 1) / 2;
            }
        }

        // 先将头节点放到完全二叉树的尾部然后再将尾部弹出，然后整体进行向下调整
        public int pop() {
            if (this.isEmpty()) {
                throw new RuntimeException("堆空");
            }
            int ans = arr[0];
            swap(arr, 0, --heapsize);
            heapify(arr, 0, heapsize);
            return ans;
        }

        private static void heapify(int[] arr, int index, int heapsize) {
            int l = index * 2 + 1;
            while (l < heapsize) {
                int maxSonIndex = (l + 1) < heapsize ? (arr[l] > arr[l + 1] ? l : l + 1) : (l);
                if (arr[maxSonIndex] > arr[index]) {
                    swap(arr, maxSonIndex, index);
                    index = maxSonIndex;
                    l = index * 2 + 1;
                } else {
                    break;
                }
            }
        }

        public static void swap(int[] arr, int father, int index) {
            int temp = arr[father];
            arr[father] = arr[index];
            arr[index] = temp;
        }
    }
}