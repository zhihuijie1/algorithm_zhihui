package algorithmbasic.designPattern.abstrcFactory;

public class Phone {
    private String phoneName;
    private Integer phonePrice;

    public Phone() {
    }

    public Phone(String phoneName, Integer phonePrice) {
        this.phoneName = phoneName;
        this.phonePrice = phonePrice;
    }

    public String getPhoneName() {
        return phoneName;
    }

    public Integer getPhonePrice() {
        return phonePrice;
    }
}
