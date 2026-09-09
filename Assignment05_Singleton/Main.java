/**
 * Demonstrates the Singleton Logger: obtain it via getInstance(), prove that two
 * references are the SAME object, write several messages, change the file at
 * runtime, write more, and finally close it.
 */
public class Main {
    public static void main(String[] args) {
        Logger logger = Logger.getInstance();
        logger.write("Application started");
        logger.write("Loading configuration");

        // Any other part of the app gets the SAME logger.
        Logger sameLogger = Logger.getInstance();
        System.out.println("Same instance? " + (logger == sameLogger));   // true
        sameLogger.write("Message from another reference (same singleton)");

        // Switch the log file at runtime.
        System.out.println("Switching log file to 'errors.log'...");
        logger.setFileName("errors.log");
        logger.write("Something went wrong here");
        logger.write("...and here too");

        logger.close();
        System.out.println("Done. Wrote to log.txt and errors.log.");
    }
}
