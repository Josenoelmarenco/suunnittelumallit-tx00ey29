package refactoring_guru.bridge.example;

import refactoring_guru.bridge.example.devices.Radio;
import refactoring_guru.bridge.example.devices.SmartTv;
import refactoring_guru.bridge.example.devices.Tv;
import refactoring_guru.bridge.example.remotes.AdvancedRemote;
import refactoring_guru.bridge.example.remotes.BasicRemote;
import refactoring_guru.bridge.example.remotes.SmartRemote;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

/**
 * Automated checks for the extension, without any test library: each check prints
 * PASS or FAIL, and the program exits with status 1 if any check fails.
 * The remotes' console messages are hidden while a check runs.
 */
public class ExtensionTest {

    private static int passed = 0;
    private static int failed = 0;
    private static final PrintStream CONSOLE = System.out;

    public static void main(String[] args) {
        // SmartTv on its own
        SmartTv tv = new SmartTv();
        check("SmartTv starts disabled, 30% volume, channel 1, offline, Live TV",
                !tv.isEnabled() && tv.getVolume() == 30 && tv.getChannel() == 1
                        && !tv.isConnected() && tv.getActiveApp().equals("Live TV"));
        tv.setVolume(150);
        check("SmartTv clamps volume above 100 to 100", tv.getVolume() == 100);
        tv.setVolume(-5);
        check("SmartTv clamps volume below 0 to 0", tv.getVolume() == 0);
        tv.setChannel(0);
        check("SmartTv keeps the channel at 1 or above", tv.getChannel() == 1);
        check("SmartTv refuses to launch an app while disabled",
                throwsIllegalState(() -> new SmartTv().launchApp("YouTube")));
        SmartTv offline = new SmartTv();
        offline.enable();
        check("SmartTv refuses to open a page while offline",
                throwsIllegalState(() -> offline.openUrl("https://example.com")));

        // SmartRemote with SmartTv
        SmartTv smartTv = new SmartTv();
        SmartRemote remote = new SmartRemote(smartTv);
        boolean opened = quietly(() -> remote.browseInternet("https://example.com"));
        check("browseInternet switches on, connects and opens the page",
                opened && smartTv.isEnabled() && smartTv.isConnected()
                        && smartTv.getActiveApp().equals("Browser")
                        && "https://example.com".equals(smartTv.getCurrentUrl()));
        quietly(() -> remote.voiceControl("volume 45"));
        check("\"volume 45\" sets the volume to 45", smartTv.getVolume() == 45);
        quietly(() -> remote.voiceControl("volume 150"));
        check("\"volume 150\" is clamped to 100 by the device", smartTv.getVolume() == 100);
        quietly(() -> remote.voiceControl("mute"));
        check("\"mute\" sets the volume to 0", smartTv.getVolume() == 0);
        quietly(() -> remote.voiceControl("louder"));
        check("\"louder\" raises the volume by 10", smartTv.getVolume() == 10);
        quietly(() -> remote.voiceControl("channel 7"));
        check("\"channel 7\" sets the channel and returns to Live TV",
                smartTv.getChannel() == 7 && smartTv.getActiveApp().equals("Live TV"));
        quietly(() -> remote.voiceControl("  VOLUME   UP  "));
        check("commands ignore case and extra spaces", smartTv.getVolume() == 20);
        quietly(() -> remote.voiceControl("open Netflix"));
        check("\"open Netflix\" launches the app", smartTv.getActiveApp().equals("Netflix"));
        quietly(() -> remote.voiceControl("turn on"));
        check("\"turn on\" on a device that is already on keeps it on", smartTv.isEnabled());
        quietly(() -> remote.voiceControl("turn off"));
        check("\"turn off\" switches the device off", !smartTv.isEnabled());
        check("an unknown command returns false",
                !quietly(() -> remote.voiceControl("make coffee")));

        // SmartRemote with the original devices
        Tv oldTv = new Tv();
        SmartRemote tvRemote = new SmartRemote(oldTv);
        quietly(() -> tvRemote.voiceControl("turn on"));
        quietly(() -> tvRemote.voiceControl("channel 5"));
        check("SmartRemote controls the original Tv", oldTv.isEnabled() && oldTv.getChannel() == 5);
        check("SmartRemote cannot open an app on the original Tv",
                !quietly(() -> tvRemote.voiceControl("open Netflix")));
        Radio radio = new Radio();
        SmartRemote radioRemote = new SmartRemote(radio);
        check("SmartRemote cannot browse on the original Radio",
                !quietly(() -> radioRemote.browseInternet("https://example.com")) && !radio.isEnabled());
        quietly(() -> radioRemote.voiceControl("volume up"));
        check("SmartRemote raises the Radio volume to 40", radio.getVolume() == 40);

        // Original remotes with the new device
        SmartTv third = new SmartTv();
        AdvancedRemote advanced = new AdvancedRemote(third);
        quietly(() -> { advanced.power(); advanced.mute(); return true; });
        check("original AdvancedRemote switches on and mutes the SmartTv",
                third.isEnabled() && third.getVolume() == 0);
        SmartTv fourth = new SmartTv();
        BasicRemote basic = new BasicRemote(fourth);
        quietly(() -> { basic.volumeDown(); basic.channelUp(); return true; });
        check("original BasicRemote changes the SmartTv volume and channel",
                fourth.getVolume() == 20 && fourth.getChannel() == 2);

        CONSOLE.println();
        CONSOLE.println(passed + " passed, " + failed + " failed");
        if (failed > 0) {
            System.exit(1);
        }
    }

    private static void check(String description, boolean condition) {
        if (condition) {
            passed++;
            CONSOLE.println("PASS  " + description);
        } else {
            failed++;
            CONSOLE.println("FAIL  " + description);
        }
    }

    /** Runs an action with the console output hidden and returns its result. */
    private static boolean quietly(java.util.function.BooleanSupplier action) {
        System.setOut(new PrintStream(new ByteArrayOutputStream()));
        try {
            return action.getAsBoolean();
        } finally {
            System.setOut(CONSOLE);
        }
    }

    private static boolean throwsIllegalState(Runnable action) {
        try {
            action.run();
            return false;
        } catch (IllegalStateException e) {
            return true;
        }
    }
}
