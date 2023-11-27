package algorithmbasic.designPattern.simpleFactory;

public class Audi extends Car {

    public Audi() {
        setCarName();
        setEngine();
    }

    @Override
    public void setCarName() {
        this.carName = "Audi";
    }

    @Override
    public void setEngine() {
        this.engine = "w12";
    }
}
