package algorithmbasic.designPattern.observer;

import java.util.LinkedList;

public class SanZhiYangTickToker implements TickToker {
    //粉丝列表
    LinkedList<Fan> fanList = new LinkedList<>();

    @Override
    public void sendStart() {
        System.out.println("晚上八点准时开播");
        for (Fan f : fanList) {
            f.getStartMessage("三只羊网络晚上八点准时开播");
        }
    }

    @Override
    public void sendEnd() {
        System.out.println("晚上十二点准时下播");
        for (Fan f : fanList) {
            f.getEndMessage("三只羊网络晚上十二点准时下播");
        }
    }

    @Override
    public void addFans(Fan fan) {
        this.fanList.add(fan);
    }
}
