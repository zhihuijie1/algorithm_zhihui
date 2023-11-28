package algorithmbasic.designPattern.Strategy;

public class SpringActivity implements FestivalActivity {
    @Override
    public void show() {
        System.out.println("春节期间，买一送一");
    }
}
