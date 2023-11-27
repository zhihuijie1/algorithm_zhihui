package algorithmbasic.designPattern.simpleFactory;

public class Benz extends Car {

    public Benz() {
        setEngine();
        setCarName();
    }

    @Override
    public void setCarName() {
        this.carName = "benchi";
    }

    @Override
    public void setEngine() {
        this.engine = "V12";
    }
}
