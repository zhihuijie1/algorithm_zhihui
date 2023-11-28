package algorithmbasic.designPattern.command;

import java.util.LinkedList;

public class Waiter {
    //命令队列
    LinkedList<Command> list = new LinkedList<>();

    public void getCommand(Command command) {
        list.add(command);
    }

    //发起命令
    public void orderUp() {
        for (Command c : list) {
            c.execute();
        }
    }
}
