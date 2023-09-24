package algorithmbasic.basicsets.class9;

public class Test {
    public static void main(String[] args) {
        System.out.println(getUrBits(19,1));
    }

    // 求个位十位百位所对应的数字
    public static int getUrBits(int num, int bit) {
        // double devide = Math.pow(num, bit) * 10;
        // return num % (int) devide;
        return ((num / ((int) Math.pow(10, bit - 1))) % 10);
    }
}
