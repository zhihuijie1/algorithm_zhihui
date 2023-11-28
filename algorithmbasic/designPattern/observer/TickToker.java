package algorithmbasic.designPattern.observer;

public interface TickToker {
    //开播
    void sendStart();

    //闭播
    void sendEnd();
    //新增粉丝
    void addFans(Fan fan);
}
