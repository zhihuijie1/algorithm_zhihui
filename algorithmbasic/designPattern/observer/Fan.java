package algorithmbasic.designPattern.observer;

public interface Fan {
    //收到开播通知
    String getStartMessage(String message);

    //收到闭播通知
    String getEndMessage(String message);
    //关注功能
    void follow(TickToker tickToker);
}
