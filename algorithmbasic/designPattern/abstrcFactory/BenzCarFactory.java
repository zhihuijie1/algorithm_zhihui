package algorithmbasic.designPattern.abstrcFactory;

public class BenzCarFactory extends CarFactory {
    Car benz;

    @Override
    public Car createCar() {
        benz = new Car("benz", 890000);
        return benz;
    }
}
