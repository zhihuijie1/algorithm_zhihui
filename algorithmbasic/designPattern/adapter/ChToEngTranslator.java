package algorithmbasic.designPattern.adapter;

public class ChToEngTranslator implements Translator {
    @Override
    public String translate(String context) {
        if(context.equals("你好")) {
            return "Hello!";
        }
        if(context.equals("草泥马")) {
            return "fuck your mother!";
        }
        return "......";
    }
}
