package algorithmbasic.basicsets.class32;

public class TEST {
    public static void main(String[] args) {
        int[][] p = {{1,2},{1,3},{1,4}};
        for (int i = 0; i < p.length; i++) {
            for (int j = 0; j < p[i].length; j++) {
                System.out.print(p[i][j] + " ");
            }
            System.out.println();
        }
    }
}
