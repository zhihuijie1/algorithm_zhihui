package algorithmbasic.designPattern.abstrcFactory;

public class Car {
    private String carName;
    private Integer carPrice;

    public Car() {
    }

    public Car(String phoneName, Integer phonePrice) {
        this.carName = phoneName;
        this.carPrice = phonePrice;
    }

    public String getCarName() {
        return carName;
    }

    public Integer getCarPrice() {
        return carPrice;
    }
}
