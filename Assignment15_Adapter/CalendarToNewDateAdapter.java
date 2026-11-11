import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Adapter: implements {@link NewDateInterface} and translates every call into
 * operations on a {@link java.util.Calendar} (the Adaptee), which it keeps internally.
 *
 * Translations done here so the client never has to know them:
 * - Calendar months are 0-based (January = 0); the interface uses 1-12.
 * - Calendar works with field constants (Calendar.DAY_OF_MONTH, ...); the interface uses plain methods.
 * - Calendar silently rolls invalid values over (e.g. 31 February becomes 2 or 3 March);
 *   the adapter rejects invalid values instead.
 */
public final class CalendarToNewDateAdapter implements NewDateInterface {

    private final Calendar calendar;   // the Adaptee

    /** Creates an adapter set to today's date. */
    public CalendarToNewDateAdapter() {
        calendar = new GregorianCalendar();
        Calendar now = Calendar.getInstance();
        int day = now.get(Calendar.DAY_OF_MONTH);
        int month = now.get(Calendar.MONTH);
        int year = now.get(Calendar.YEAR);
        calendar.clear();                // drop the time of day: only the date matters
        calendar.set(year, month, day);
    }

    /** Creates an adapter set to the given date (month 1-12). */
    public CalendarToNewDateAdapter(int day, int month, int year) {
        calendar = new GregorianCalendar();
        calendar.clear();
        validateMonth(month);
        calendar.set(year, month - 1, 1);
        validateDay(day);
        calendar.set(Calendar.DAY_OF_MONTH, day);
    }

    @Override
    public void setDay(int day) {
        validateDay(day);
        calendar.set(Calendar.DAY_OF_MONTH, day);
    }

    /**
     * Changes the month. If the current day does not exist in the new month
     * (31 January -> February), the day becomes the last day of that month.
     */
    @Override
    public void setMonth(int month) {
        validateMonth(month);
        int day = getDay();
        calendar.set(Calendar.DAY_OF_MONTH, 1);       // avoid roll-over while switching months
        calendar.set(Calendar.MONTH, month - 1);      // interface 1-12 -> Calendar 0-11
        keepDayWithinMonth(day);
    }

    /**
     * Changes the year. 29 February in a non-leap year becomes 28 February.
     */
    @Override
    public void setYear(int year) {
        int day = getDay();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.YEAR, year);
        keepDayWithinMonth(day);
    }

    @Override
    public int getDay() {
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    @Override
    public int getMonth() {
        return calendar.get(Calendar.MONTH) + 1;      // Calendar 0-11 -> interface 1-12
    }

    @Override
    public int getYear() {
        return calendar.get(Calendar.YEAR);
    }

    @Override
    public void advanceDays(int days) {
        calendar.add(Calendar.DAY_OF_MONTH, days);    // Calendar handles month/year changes and leap years
    }

    /** Finnish date format, e.g. 29.02.2024. */
    @Override
    public String toString() {
        return String.format("%02d.%02d.%04d", getDay(), getMonth(), getYear());
    }

    private void keepDayWithinMonth(int day) {
        int lastDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        calendar.set(Calendar.DAY_OF_MONTH, Math.min(day, lastDay));
    }

    private void validateMonth(int month) {
        if (month < 1 || month > 12) {
            throw new IllegalArgumentException("Month must be 1-12, got " + month);
        }
    }

    private void validateDay(int day) {
        int lastDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        if (day < 1 || day > lastDay) {
            throw new IllegalArgumentException(
                    "Day must be 1-" + lastDay + " for month " + getMonth() + "/" + getYear() + ", got " + day);
        }
    }
}
