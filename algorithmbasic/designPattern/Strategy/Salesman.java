package algorithmbasic.designPattern.Strategy;

public class Salesman {
    //聚合对象
    FestivalActivity festivalActivity;

    public Salesman(FestivalActivity festivalActivity) {
        this.festivalActivity = festivalActivity;
    }

    public void showinfo() {
        festivalActivity.show();
    }
}
