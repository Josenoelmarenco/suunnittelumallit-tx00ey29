/**
 * Target: the date interface the client code wants to use.
 *
 * Months are 1-12 (January = 1), as people write them. Days and years are the
 * normal calendar values.
 */
public interface NewDateInterface {

    void setDay(int day);

    void setMonth(int month);

    void setYear(int year);

    int getDay();

    int getMonth();

    int getYear();

    /** Moves the date forward by the given number of days (a negative value moves it back). */
    void advanceDays(int days);
}
