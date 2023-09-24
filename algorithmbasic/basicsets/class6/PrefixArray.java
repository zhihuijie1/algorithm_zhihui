package algorithmbasic.basicsets.class6;

// 前缀和数组
public class PrefixArray {
    public static int[] prefixArray(int[] arr) {
        int[] prefix = new int[arr.length];
        int index1 = 1;
        int index2 = 0;
        prefix[index2] = arr[0];
        while(index1 < arr.length) {
            prefix[index2 + 1] = prefix[index2] + arr[index1];
            index1++;
            index2++;
        }
        return prefix;
    }
}
