package algorithmbasic.basicsets.class19;
/*
给定一个整型数组arr，代表数值不同的纸牌排成一条线
玩家A和玩家B依次拿走每张纸牌
规定玩家A先拿，玩家B后拿
但是每个玩家每次只能拿走最左或最右的纸牌
玩家A和玩家B都绝顶聪明
请返回最后获胜者的分数
 */

public class CardsInLine {

    public static int win1(int[] arr) {

        int f = f1(arr, 0, arr.length - 1);

        int l = g1(arr, 0, arr.length - 1);

        return Math.max(f, l);
    }

    //返回先手在arr[L......R]范围上的最优分数。
    public static int f1(int[] arr, int L, int R) {

        if (L == R) {
            return arr[L];
        }

        int p1 = arr[L] + g1(arr, L + 1, R);

        int p2 = arr[R] + g1(arr, L, R - 1);

        return Math.max(p1, p2);
    }

    private static int g1(int[] arr, int L, int R) {

        if (L == R) {
            return 0;
        }

        int p1 = f1(arr, L + 1, R);

        int p2 = f1(arr, L, R - 1);

        return Math.min(p1, p2);
    }


    // 方法二：记忆化搜索。
    private static int win2(int[] arr) {

        int N = arr.length;

        int[][] fmap = new int[N][N];

        int[][] gmap = new int[N][N];

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                fmap[i][j] = -1;
                gmap[i][j] = -1;
            }
        }

        int f = f2(arr, 0, arr.length - 1, fmap, gmap);

        int l = g2(arr, 0, arr.length - 1, fmap, gmap);

        return Math.max(f, l);
    }

    public static int f2(int[] arr, int L, int R, int[][] fmap, int[][] gmap) {

        if (fmap[L][R] != -1) {
            return fmap[L][R];
        }

        int ans = -1;

        if (L == R) {

            ans = arr[L];
        } else {

            int p1 = arr[L] + g2(arr, L + 1, R, fmap, gmap);

            int p2 = arr[R] + g2(arr, L, R - 1, fmap, gmap);

            ans = Math.max(p1, p2);
        }

        fmap[L][R] = ans;
        return ans;
    }

    private static int g2(int[] arr, int L, int R, int[][] fmap, int[][] gmap) {

        if (gmap[L][R] != -1) {
            return gmap[L][R];
        }
        int ans = -1;

        if (L == R) {
            ans = 0;
        } else {
            int p1 = f2(arr, L + 1, R, fmap, gmap);

            int p2 = f2(arr, L, R - 1, fmap, gmap);

            ans = Math.min(p1, p2);
        }

        gmap[L][R] = ans;

        return ans;
    }


    private static int win3(int[] arr) {

        int N = arr.length;

        int[][] fmap = new int[N][N];

        int[][] gmap = new int[N][N];

        //根据f1中if (L == R) {return arr[L]; } 以及g1中的第一句话。
        for (int col = 0; col < N; col++) {
            fmap[col][col] = arr[col];
            gmap[col][col] = 0;
        }

        for (int start = 1; start < N; start++) {

            //斜着填
            int row = 0;
            int col = start;

            while (col < N) {
                fmap[row][col] = Math.max(arr[row] + gmap[row][col - 1], arr[row] + gmap[row + 1][col]);
                gmap[row][col] = Math.min(fmap[row][col - 1], fmap[row + 1][col]);
                row++;
                col++;
            }
        }
        return Math.max(fmap[0][N - 1], gmap[0][N - 1]);
    }

    public static void main(String[] args) {

        int[] arr = {10, 20, 50, 80, 60, 30, 20};
        System.out.println(win1(arr));

        System.out.println(win2(arr));

        System.out.println(win3(arr));
    }
}