/**
 * Subject (observable) of the Observer pattern.
 * Keeps a list of observers and notifies them when its state changes.
 */
public interface Subject {
    void registerObserver(Observer o);
    void removeObserver(Observer o);
    void notifyObservers();
}
