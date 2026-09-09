/** Concrete Observer: raises an alert only when a threshold is crossed. */
public class TemperatureAlert implements Observer {
    private final int threshold;
    public TemperatureAlert(int threshold) { this.threshold = threshold; }

    @Override
    public void update(int temperature) {
        if (temperature >= threshold) {
            System.out.println("[ALERT] Heat warning! Temperature reached " + temperature + " C (>= " + threshold + ").");
        } else {
            System.out.println("[Alert sensor] " + temperature + " C - within safe range.");
        }
    }
}
