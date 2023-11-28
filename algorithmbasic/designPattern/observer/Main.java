package algorithmbasic.designPattern.observer;

public class Main {
    public static void main(String[] args) {
        Fan xiaobai = new XiaoBai();
        Fan xiaohei = new XiaoHei();
        TickToker sanzhiyang = new SanZhiYangTickToker();
        //小白与小黑都关注了三只羊
        xiaobai.follow(sanzhiyang);
        xiaohei.follow(sanzhiyang);
        //三只羊发通知
        sanzhiyang.sendStart();
        System.out.println("-------------------");
        sanzhiyang.sendEnd();
    }
}
