package algorithmbasic.designPattern.abstrcFactory;

public class Main {
    public static void main(String[] args) {
        TotalFactory factory = new ApplePhoneFactory();
        // System.out.println(factory.createCar().getCarPrice());  --> NullPointerException
        Phone mobilphone = factory.createphone();
        System.out.println("produce->" + mobilphone.getPhoneName() + "  " + "price->" + mobilphone.getPhonePrice());
        System.out.println("----------------------------------------");
        factory = new BenzCarFactory();
        Car mycar = factory.createCar();
        System.out.println("produce->" + mycar.getCarName() + "  " + "price->" + mycar.getCarPrice());
        System.out.println("----------------------------------------");
        factory = new N95MuskFactory();
        Musk mymusk = factory.createMusk();
        System.out.println("produce->" + mymusk.getMuskName() + "  " + "price->" + mymusk.getMuskPrice());
    }
}
