package algorithmbasic.designPattern.simpleFactory;

public class Main {
    public static void main(String[] args) {
        Car car = SimpleFactory.createCar("Audi");
        System.out.println(car.carName + "  " + car.engine);
    }
}
