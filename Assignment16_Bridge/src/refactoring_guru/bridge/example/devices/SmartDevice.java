package refactoring_guru.bridge.example.devices;

/**
 * Extension of the implementation hierarchy: a device that, besides the basic
 * {@link Device} primitives, can go online, run apps and open web pages.
 *
 * It extends Device instead of changing it, so Radio and Tv stay untouched and
 * every existing remote keeps working with smart devices.
 */
public interface SmartDevice extends Device {

    boolean isConnected();

    void connectToInternet();

    String getActiveApp();

    /** Starts an app on the device. The device must be enabled. */
    void launchApp(String appName);

    String getCurrentUrl();

    /** Loads a web page. The device must be enabled and connected. */
    void openUrl(String url);
}
