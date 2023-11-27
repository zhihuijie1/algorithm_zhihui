package algorithmbasic.designPattern.abstrcFactory;

public class N95MuskFactory extends MuskFactory {
    Musk N95;

    @Override
    public Musk createMusk() {
        N95 = new Musk("N95", 15);
        return N95;
    }
}
