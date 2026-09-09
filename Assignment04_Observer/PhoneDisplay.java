/** Concrete Observer: a phone app. Requirement 3: unique message including the temperature. */
public class PhoneDisplay implements Observer {
    private final String owner;
    public PhoneDisplay(String owner) { this.owner = owner; }

    @Override
    public void update(int temperature) {
        System.out.println("[Phone/" + owner + "] Weather app: it is now " + temperature + " C outside.");
    }
}
