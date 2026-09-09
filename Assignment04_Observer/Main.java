/**
 * Client. Requirement 6: create a weather station and several observers, start the
 * station thread, register the observers, let it run, then REMOVE one observer and
 * let it continue - the output shows the removed observer stops being notified.
 * main() never sets the temperature itself.
 */
public class Main {
    public static void main(String[] args) throws InterruptedException {
        WeatherStation station = new WeatherStation();

        Observer phone  = new PhoneDisplay("Nokia");
        Observer window = new WindowDisplay();
        Observer alert  = new TemperatureAlert(25);

        station.registerObserver(phone);
        station.registerObserver(window);
        station.registerObserver(alert);

        System.out.println("Weather station starting. Initial temperature: "
                + station.getTemperature() + " C\n");

        Thread thread = new Thread(station);
        thread.setDaemon(true);   // the loop is eternal; the JVM exits when main() returns
        thread.start();

        Thread.sleep(7000);       // let the simulation run with all 3 observers

        System.out.println("\n--- Removing the phone display (it will no longer be notified) ---\n");
        station.removeObserver(phone);

        Thread.sleep(7000);       // continue: only window + alert are notified now

        System.out.println("\n--- Simulation ended ---");
    }
}
