package algorithmbasic.designPattern.adapter;

public class Main {
    public static void main(String[] args) {
        ChToEngPlayAdapter adapter = new ChToEngPlayAdapter(new ChineseMoviePlayer());
        adapter.play();
    }
}
