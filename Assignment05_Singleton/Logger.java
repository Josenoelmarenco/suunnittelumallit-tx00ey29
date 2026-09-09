import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * A Logger implemented with the Singleton design pattern.
 *
 * There is exactly ONE Logger in the whole application, obtained via
 * {@link #getInstance()}. It writes each message on its own line to a file,
 * can switch to a different file at runtime, and closes its resources safely.
 */
public class Logger {

    private static Logger instance;                       // the single instance
    private static final String DEFAULT_FILE = "log.txt";
    private static final DateTimeFormatter TS =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private String fileName;
    private PrintWriter writer;

    /** Private constructor: no one else can create a Logger. Uses a default file name. */
    private Logger() {
        this.fileName = DEFAULT_FILE;
        openWriter(this.fileName);
    }

    /** Global access point. Lazily creates the single instance (thread-safe). */
    public static synchronized Logger getInstance() {
        if (instance == null) {
            instance = new Logger();
        }
        return instance;
    }

    /** Opens the file in append mode with auto-flush; handles I/O errors gracefully. */
    private void openWriter(String name) {
        try {
            writer = new PrintWriter(new FileWriter(name, true), true);
        } catch (IOException e) {
            System.err.println("Logger: could not open file '" + name + "': " + e.getMessage());
            writer = null;
        }
    }

    /** Switches the active log file: closes the current one and opens the new one. */
    public synchronized void setFileName(String newFileName) {
        close();
        this.fileName = newFileName;
        openWriter(newFileName);
    }

    /** Appends a message on a NEW line, timestamped. */
    public synchronized void write(String message) {
        if (writer != null) {
            writer.println("[" + LocalDateTime.now().format(TS) + "] " + message);
        } else {
            System.err.println("Logger: no open file, message lost: " + message);
        }
    }

    /** Closes the logger and its file resources. */
    public synchronized void close() {
        if (writer != null) {
            writer.close();
            writer = null;
        }
    }

    public String getFileName() { return fileName; }
}
