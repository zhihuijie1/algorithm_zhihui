package algorithmbasic.designPattern.abstrcFactory;

public class ApplePhoneFactory extends PhoneFactory {
    private Phone iphone;

    @Override
    public Phone createphone() {
        iphone = new Phone("Apple", 9999);
        return iphone;
    }
}
