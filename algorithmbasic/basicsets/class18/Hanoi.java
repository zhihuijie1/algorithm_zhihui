package algorithmbasic.basicsets.class18;

public class Hanoi {
    /**
     * 方法一
     */
    public static void hanoi1(int n) {
        leftToRight(n);
    }

    public static void leftToRight(int n) {
        if (n == 1) {
            System.out.println("move" + 1 + " from left to right");
            return;
        }
        leftToMid(n - 1);
        System.out.println("move" + n + " from left to right");
        midToRight(n - 1);
    }

    public static void leftToMid(int n) {
        if (n == 1) {
            System.out.println("move" + 1 + " from left to mid");
            return;
        }
        leftToRight(n - 1);
        System.out.println("move" + n + " from left to mid");
        rightToMid(n - 1);
    }

    private static void rightToMid(int n) {
        if (n == 1) {
            System.out.println("move" + 1 + " from right to mid");
            return;
        }
        rightToLeft(n - 1);
        System.out.println("move" + n + " from right to mid");
        leftToMid(n - 1);
    }

    private static void rightToLeft(int n) {
        if (n == 1) {
            System.out.println("move" + 1 + " from right to left");
            return;
        }
        rightToMid(n - 1);
        System.out.println("move" + n + " from right to left");
        midToLeft(n - 1);
    }

    public static void midToRight(int n) {
        if (n == 1) {
            System.out.println("move" + 1 + " from mid to right");
            return;
        }
        midToLeft(n - 1);
        System.out.println("move" + n + " from mid to right");
        leftToMid(n - 1);
    }

    public static void midToLeft(int n) {
        if (n == 1) {
            System.out.println("move" + 1 + " from mid to left");
            return;
        }
        midToRight(n - 1);
        System.out.println("move" + n + " from mid to left");
        rightToMid(n - 1);
    }

    /**
     * 方法二
     */
    public static void Hanoi2(int n) {
        process(n, "left", "right", "mid");
    }

    public static void process(int n, String from, String to, String wait) {
        if (n == 1) {
            System.out.println("move   " + 1 + "   from   " + from + "   to   " + to);
            return;
        }
        process(n - 1, from, wait, to);
        System.out.println("move   " + n + "   from   " + from + "   to   " + to);
        process(n - 1, wait, to, from);
    }


    public static void main(String[] args) {
        Hanoi.Hanoi2(3);
    }
}
