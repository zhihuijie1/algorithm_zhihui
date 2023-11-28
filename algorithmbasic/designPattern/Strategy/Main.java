package algorithmbasic.designPattern.Strategy;

public class Main {
    public static void main(String[] args) {
        Salesman salesman = new Salesman(new ChongYangActivity());
        salesman.showinfo();
    }
}
