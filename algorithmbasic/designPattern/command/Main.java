package algorithmbasic.designPattern.command;

public class Main {
    public static void main(String[] args) {
        Waiter waiter = new Waiter();
        Chef chef = new Chef();

        Command command1 = new OrderCommand(chef,"炒粉");
        Command command2 = new OrderCommand(chef,"兰州拉面");

        waiter.getCommand(command1);
        waiter.getCommand(command2);

        waiter.orderUp();
    }
}


/**
 * 在软件开发系统中，“方法的请求者”与“方法的实现者”之间经常存在紧密的耦合关系，这不利于软件功能的扩展与维护。
 * 例如，想对方法进行“撤销、重做、记录”等处理都很不方便，因此“如何将方法的请求者与实现者解耦？”变得很重要，命令模式就能很好地解决这个问题。
 *
 * 在方法的请求者与实现者之间加上命令这一个桥梁。
 *
 * 在现实生活中，命令模式的例子也很多。比如看电视时，我们只需要轻轻一按遥控器就能完成频道的切换，
 * 这就是命令模式，将换台请求和换台处理完全解耦了。电视机遥控器（命令发送者）通过按钮（具体命令）来遥控电视机（命令接收者）。
 */
