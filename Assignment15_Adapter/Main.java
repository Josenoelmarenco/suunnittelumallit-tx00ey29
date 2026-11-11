/**
 * Client: works only with NewDateInterface. It never imports or touches
 * java.util.Calendar; the adapter does that behind the interface.
 */
public class Main {
    public static void main(String[] args) {
        NewDateInterface date = new CalendarToNewDateAdapter();
        System.out.println("Today:                          " + date);

        System.out.println("\n1) Set the date with the setters and advance it");
        date.setYear(2024);
        date.setMonth(2);
        date.setDay(27);
        print("Set to 27.02.2024:", date);
        date.advanceDays(1);
        print("+1 day:", date);
        date.advanceDays(1);
        print("+1 day (2024 is a leap year):", date);
        date.advanceDays(1);
        print("+1 day (into March):", date);
        date.advanceDays(30);
        print("+30 days:", date);
        date.advanceDays(365);
        print("+365 days:", date);
        date.advanceDays(-400);
        print("-400 days:", date);

        System.out.println("\n2) Crossing the end of a year");
        NewDateInterface newYear = new CalendarToNewDateAdapter(30, 12, 2025);
        print("Start:", newYear);
        newYear.advanceDays(5);
        print("+5 days:", newYear);

        System.out.println("\n3) Changing month and year keeps the date valid");
        NewDateInterface endOfMonth = new CalendarToNewDateAdapter(31, 1, 2024);
        print("Start:", endOfMonth);
        endOfMonth.setMonth(2);
        print("setMonth(2):", endOfMonth);
        endOfMonth.setYear(2025);
        print("setYear(2025), not a leap year:", endOfMonth);

        System.out.println("\n4) Invalid values are rejected");
        try {
            endOfMonth.setDay(30);
        } catch (IllegalArgumentException e) {
            System.out.println("  setDay(30) -> " + e.getMessage());
        }
        try {
            endOfMonth.setMonth(13);
        } catch (IllegalArgumentException e) {
            System.out.println("  setMonth(13) -> " + e.getMessage());
        }
        print("Date unchanged:", endOfMonth);
    }

    private static void print(String label, NewDateInterface date) {
        System.out.printf("  %-32s %02d.%02d.%04d  (day=%d, month=%d, year=%d)%n",
                label, date.getDay(), date.getMonth(), date.getYear(),
                date.getDay(), date.getMonth(), date.getYear());
    }
}
