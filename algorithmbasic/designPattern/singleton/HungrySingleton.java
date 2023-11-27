package algorithmbasic.designPattern.singleton;

public class HungrySingleton {

    //饿汉模式
    private static volatile HungrySingleton instance = new HungrySingleton();

    private HungrySingleton() {
    }

    public static HungrySingleton getInstance() {
        return instance;
    }
}
