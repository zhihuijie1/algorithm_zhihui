package algorithmbasic.designPattern.adapter;


public class ChToEngPlayAdapter extends ChToEngTranslator implements Player {

    Player player;
    Translator translator;

    public ChToEngPlayAdapter(Player target) {
        this.player = target;
        this.translator = new ChToEngTranslator();
    }


    @Override
    public String play() {
        String str = player.play();
        System.out.println("播放器播放：" + str);
        String strs = translator.translate(str);
        System.out.println("翻译器翻译：" + strs);
        return str;
    }
}
