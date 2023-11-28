package algorithmbasic.designPattern.command;

public class OrderCommand implements Command {
    private Chef chef;
    String things;
    public OrderCommand(Chef chef, String things) {
        this.chef = chef;
        this.things = things;
    }
    @Override
    public void execute() {
        chef.doing(things);
    }
}
