package algorithmbasic.basicsets.class8;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

public class CoverMax {
    // 方法一
    public static int maxCover1(int[][] lines) {
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        // 找出所有线段的最小起始位置
        // 找出所有线段的最大终止位置
        for (int i = 0; i < lines.length; i++) {
            min = Math.min(min, lines[i][0]);
            max = Math.max(max, lines[i][1]);
        }
        // 线段的最小起始位置min与最大终止位置max已找到。
        // 在[min , max]范围上寻找每一个0.5隔断会穿过多少线段数，找出最大的即答案
        int cover = 0;
        for (double i = min + 0.5; i < max; i += 1) {
            int count = 0;
            for (int j = 0; j < lines.length; j++) {
                if (i > lines[j][0] && i < lines[j][1]) {
                    count++;
                }
            }
            cover = Math.max(count, cover);
        }
        return cover;
    }

    // 方法二
    // 1:找出所有线段的最小起始位置min和最大终止位置max
    // 2:根据线段的起始位置对线段进行排序。
    // 3:问题转化：求某一区间线段重合的最大条数 --> 因为重合最多的区间其起始位置与终止位置一定是某个线段的起始位置与终止位置 -->
    //           -> 求每个线段起始位置被穿过的次数。
    public static int maxCover2(int[][] lines) {
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        // 找出所有线段的最小起始位置
        // 找出所有线段的最大终止位置
        Line[] line = new Line[lines.length];
        for (int i = 0; i < lines.length; i++) {
            line[i].start = lines[i][0];
            line[i].end = lines[i][1];
        }
        // 根据线段的起始位置对所有的线段进行排序
        Arrays.sort(line, new MyCompoter());
        // 默认是小根堆
        PriorityQueue<Integer> heap = new PriorityQueue<>();
        int cover = Integer.MIN_VALUE;
        for (int i = 0; i < line.length; i++) {
            while (!heap.isEmpty() && line[i].start >= heap.peek()) {
                heap.poll();
            }
            heap.add(line[i].end);
            cover = Math.max(cover, heap.size());
        }
        return cover;
    }

    public static class MyCompoter implements Comparator<Line> {
        @Override
        public int compare(Line o1, Line o2) {
            return o1.start - o2.start;
        }
    }

    public static class Line {
        int start;
        int end;

        public Line(int start, int end) {
            this.start = start;
            this.end = end;
        }
    }
}
