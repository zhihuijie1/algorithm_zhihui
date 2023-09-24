package algorithmbasic.basicsets.class21;

/**
 * 请同学们自行搜索或者想象一个象棋的棋盘，
 * 然后把整个棋盘放入第一象限，棋盘的最左下角是(0,0)位置
 * 那么整个棋盘就是横坐标上9条线、纵坐标上10条线的区域
 * 给你三个 参数 x，y，k
 * 返回“马”从(0,0)位置出发，必须走k步
 * 最后落在(x,y)上的方法数有多少种?
 */
public class HorseJump {

    /**
     * 暴力方法
     */
    public static int jump(int a, int b, int k) {
        return process(a, b, k, 0, 0);
    }

    //返回落在a,b上并且走k步的方法数
    public static int process(int a, int b, int k, int x, int y) {
        if (k == 0) {
            return (x == a && y == b) ? 1 : 0;
        }

        //9行10列
        if (x < 0 || y < 0 || x > 9 || y > 8) {
            return 0;
        }

        int p1 = process(a, b, k - 1, x + 2, y + 1);
        int p2 = process(a, b, k - 1, x + 1, y + 2);
        int p3 = process(a, b, k - 1, x + 2, y - 1);
        int p4 = process(a, b, k - 1, x + 1, y - 2);
        int p5 = process(a, b, k - 1, x - 2, y + 1);
        int p6 = process(a, b, k - 1, x - 1, y + 2);
        int p7 = process(a, b, k - 1, x - 2, y - 1);
        int p8 = process(a, b, k - 1, x - 1, y - 2);

        return p1 + p2 + p3 + p4 + p5 + p6 + p7 + p8;
    }

    /**
     * 迭代法
     */
    public static int dp(int a, int b, int k) {
        //这里需要考虑k以及k==0时的情况，所以取k的范围是k+1个
        int[][][] dp = new int[10][9][k + 1];
        //依赖关系是：上层依赖下层，最终返回最上层，所以从下向上构建
        dp[a][b][0] = 1;
        for (int plie = 1; plie <= k; plie++) {
            //这一层的每个数都依赖下一层。
            for (int x = 0; x < 10; x++) {
                for (int y = 0; y < 9; y++) {
                    int p1 = pick(dp, x + 2, y + 1, plie - 1);
                    int p2 = pick(dp, x + 1, y + 2, plie - 1);
                    int p3 = pick(dp, x + 2, y - 1, plie - 1);
                    int p4 = pick(dp, x + 1, y - 2, plie - 1);
                    int p5 = pick(dp, x - 2, y + 1, plie - 1);
                    int p6 = pick(dp, x - 1, y + 2, plie - 1);
                    int p7 = pick(dp, x - 2, y - 1, plie - 1);
                    int p8 = pick(dp, x - 1, y - 2, plie - 1);

                    dp[x][y][plie] = p1 + p2 + p3 + p4 + p5 + p6 + p7 + p8;

                }
            }
        }

        return dp[0][0][k];//注意返回的是(0,0,k)这个坐标
    }

    public static int pick(int[][][] dp, int x, int y, int pile) {
        if (x < 0 || y < 0 || x > 9 || y > 8) {
            return 0;
        } else {
            return dp[x][y][pile];
        }
    }


    public static void main(String[] args) {
        int x = 7;
        int y = 7;
        int step = 10;
        System.out.println(ways(x, y, step));
        System.out.println(dp(x, y, step));

        System.out.println(jump(x, y, step));
    }
    public static int ways(int a, int b, int step) {
        return f(0, 0, step, a, b);
    }

    public static int f(int i, int j, int step, int a, int b) {
        if (i < 0 || i > 9 || j < 0 || j > 8) {
            return 0;
        }
        if (step == 0) {
            return (i == a && j == b) ? 1 : 0;
        }
        return f(i - 2, j + 1, step - 1, a, b) + f(i - 1, j + 2, step - 1, a, b) + f(i + 1, j + 2, step - 1, a, b)
                + f(i + 2, j + 1, step - 1, a, b) + f(i + 2, j - 1, step - 1, a, b) + f(i + 1, j - 2, step - 1, a, b)
                + f(i - 1, j - 2, step - 1, a, b) + f(i - 2, j - 1, step - 1, a, b);

    }

}
