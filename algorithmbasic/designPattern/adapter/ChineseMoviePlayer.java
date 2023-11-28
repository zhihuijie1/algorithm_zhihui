package algorithmbasic.designPattern.adapter;

public class ChineseMoviePlayer implements Player {
    @Override
    public String play() {
        return "你好";
    }
}
