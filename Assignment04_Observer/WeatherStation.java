import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Concrete Subject: a weather station that runs in its OWN thread, periodically
 * changes the temperature by +/- 1 degree and notifies every registered observer.
 *
 * Observer pattern: the station knows its observers only through the {@link Observer}
 * interface, so new kinds of display can be added without changing this class.
 */
public class WeatherStation implements Subject, Runnable {

    private static final int MIN_TEMP = -30;   // the station cannot go below this
    private static final int MAX_TEMP =  40;   // ...nor above this

    private final List<Observer> observers = new ArrayList<>();
    private final Random random = new Random();
    private volatile int temperature;

    /** Requirement 4: the constructor sets an initial RANDOM temperature. */
    public WeatherStation() {
        temperature = MIN_TEMP + random.nextInt(MAX_TEMP - MIN_TEMP + 1);
    }

    @Override
    public synchronized void registerObserver(Observer o) { observers.add(o); }

    @Override
    public synchronized void removeObserver(Observer o) { observers.remove(o); }

    @Override
    public synchronized void notifyObservers() {
        for (Observer o : observers) {
            o.update(temperature);
        }
    }

    public int getTemperature() { return temperature; }

    /**
     * Requirement 5: an eternal loop that waits a random 1-5 s, changes the
     * temperature by +/- 1 degree (clamped to [MIN_TEMP, MAX_TEMP]) and notifies.
     */
    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(1000 + random.nextInt(4000));   // 1000-4999 ms
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
            int delta = random.nextBoolean() ? 1 : -1;
            temperature = Math.max(MIN_TEMP, Math.min(MAX_TEMP, temperature + delta));
            notifyObservers();
        }
    }
}
