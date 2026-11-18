package refactoring_guru.bridge.example.devices;

import java.util.ArrayList;
import java.util.List;

/**
 * New Concrete Implementor: a smart TV. It offers the same basic primitives as
 * the original Tv (power, volume 0-100, channel) plus the SmartDevice primitives.
 */
public class SmartTv implements SmartDevice {

    private static final String LIVE_TV = "Live TV";

    private boolean on = false;
    private int volume = 30;
    private int channel = 1;
    private boolean connected = false;
    private String activeApp = LIVE_TV;
    private String currentUrl = null;
    private final List<String> browsingHistory = new ArrayList<>();

    @Override
    public boolean isEnabled() {
        return on;
    }

    @Override
    public void enable() {
        on = true;
    }

    @Override
    public void disable() {
        on = false;
    }

    @Override
    public int getVolume() {
        return volume;
    }

    @Override
    public void setVolume(int volume) {
        this.volume = Math.max(0, Math.min(100, volume));
    }

    @Override
    public int getChannel() {
        return channel;
    }

    /** Channels start at 1. Changing the channel returns the TV to live television. */
    @Override
    public void setChannel(int channel) {
        this.channel = Math.max(1, channel);
        activeApp = LIVE_TV;
        currentUrl = null;
    }

    @Override
    public boolean isConnected() {
        return connected;
    }

    @Override
    public void connectToInternet() {
        connected = true;
    }

    @Override
    public String getActiveApp() {
        return activeApp;
    }

    @Override
    public void launchApp(String appName) {
        requireEnabled("launch " + appName);
        activeApp = appName;
        currentUrl = null;
    }

    @Override
    public String getCurrentUrl() {
        return currentUrl;
    }

    @Override
    public void openUrl(String url) {
        requireEnabled("open " + url);
        if (!connected) {
            throw new IllegalStateException("SmartTV is offline: cannot open " + url);
        }
        activeApp = "Browser";
        currentUrl = url;
        browsingHistory.add(url);
    }

    public List<String> getBrowsingHistory() {
        return List.copyOf(browsingHistory);
    }

    @Override
    public void printStatus() {
        System.out.println("------------------------------------");
        System.out.println("| I'm smart TV.");
        System.out.println("| I'm " + (on ? "enabled" : "disabled"));
        System.out.println("| Current volume is " + volume + "%");
        System.out.println("| Current channel is " + channel);
        System.out.println("| Internet: " + (connected ? "connected" : "offline"));
        System.out.println("| Active app: " + activeApp);
        System.out.println("| Current page: " + (currentUrl == null ? "-" : currentUrl));
        System.out.println("------------------------------------\n");
    }

    private void requireEnabled(String action) {
        if (!on) {
            throw new IllegalStateException("SmartTV is disabled: cannot " + action);
        }
    }
}
