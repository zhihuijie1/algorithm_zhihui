package algorithmbasic.designPattern.simpleFactory;

public class SimpleFactory {
    //我是汽车工厂
    public static Car createCar(String carName) {
        if (carName.equals("Benz")) {
            return new Benz();
        } else if (carName.equals("Audi")) {
            return new Audi();
        }
        return null;
    }
}
