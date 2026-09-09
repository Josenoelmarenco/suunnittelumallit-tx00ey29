/**
 * Observer of the Observer pattern.
 * Every display/observer that wants to react to temperature changes implements this.
 */
public interface Observer {
    /** Called by the subject whenever the temperature changes. */
    void update(int temperature);
}
