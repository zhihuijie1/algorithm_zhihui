package algorithmbasic.designPattern.singleton;

/**
 * 懒汉模式
 */
public class LazySingleton {
    private static volatile LazySingleton instance;

    //将构造函数变为私有的 -> 防止在外部类直接new对象
    private LazySingleton() {
    }

    public static LazySingleton getInstance() {
        if (instance == null) {
            synchronized (LazySingleton.class) {
                if (instance == null) {
                    instance = new LazySingleton();
                }
            }
        }
        return instance;
    }
}
