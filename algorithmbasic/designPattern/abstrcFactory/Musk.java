package algorithmbasic.designPattern.abstrcFactory;

public class Musk {
    private String muskName;
    private Integer muskPrice;

    public Musk() {
    }

    public Musk(String phoneName, Integer phonePrice) {
        this.muskName = phoneName;
        this.muskPrice = phonePrice;
    }

    public String getMuskName() {
        return muskName;
    }

    public Integer getMuskPrice() {
        return muskPrice;
    }
}
