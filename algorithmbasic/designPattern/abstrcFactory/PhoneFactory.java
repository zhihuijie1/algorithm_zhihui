package algorithmbasic.designPattern.abstrcFactory;

public abstract class PhoneFactory implements TotalFactory {
    @Override
    public Car createCar() {
        return null;
    }

    @Override
    public Musk createMusk() {
        return null;
    }
}
