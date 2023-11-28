package algorithmbasic.designPattern.observer;

import java.util.LinkedList;

public class XiaoHei implements Fan {
    //关注的主播列表
    LinkedList<TickToker> list = new LinkedList<>();

    @Override
    public String getStartMessage(String message) {
        String str = "小黑已收到" + "-->" + message;
        System.out.println(str);
        return str;
    }

    @Override
    public String getEndMessage(String message) {
        String str = "小黑已收到" + "-->" + message;
        System.out.println(str);
        return str;
    }

    @Override
    public void follow(TickToker tickToker) {
        //当前小黑的关注列表中添加这个主播
        list.add(tickToker);
        //这个主播的粉丝列表中也添加这个粉丝
        tickToker.addFans(this);
    }
}
