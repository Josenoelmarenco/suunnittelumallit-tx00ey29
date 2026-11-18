package refactoring_guru.bridge.example.remotes;

import refactoring_guru.bridge.example.devices.Device;
import refactoring_guru.bridge.example.devices.SmartDevice;

import java.util.Locale;

/**
 * New Refined Abstraction: a remote with voice control and internet browsing.
 *
 * It works with ANY Device. Basic commands (power, volume, channel, mute) use the
 * primitives every Device has. Smart commands (open an app, browse) need a
 * SmartDevice; on other devices the remote reports that the feature is not supported.
 */
public class SmartRemote extends AdvancedRemote {

    public SmartRemote(Device device) {
        super(device);
    }

    /**
     * High-level operation built from several device primitives: switch the device
     * on if needed, connect it, and load the page.
     *
     * @return true if the page was opened
     */
    public boolean browseInternet(String url) {
        System.out.println("Remote: browse " + url);
        if (!(device instanceof SmartDevice smartDevice)) {
            System.out.println("Remote: this device cannot browse the internet");
            return false;
        }
        if (!smartDevice.isEnabled()) {
            smartDevice.enable();
        }
        if (!smartDevice.isConnected()) {
            smartDevice.connectToInternet();
        }
        smartDevice.openUrl(url);
        System.out.println("Remote: page opened");
        return true;
    }

    /**
     * Interprets a spoken command. Supported commands (case-insensitive):
     * "turn on", "turn off", "volume up" / "louder", "volume down" / "quieter",
     * "volume N", "mute", "channel up" / "next channel", "channel down" /
     * "previous channel", "channel N", "open APP", "browse URL" / "go to URL".
     *
     * @return true if the command was understood and carried out
     */
    public boolean voiceControl(String command) {
        System.out.println("Remote: voice command \"" + command + "\"");
        String text = command.trim().toLowerCase(Locale.ROOT).replaceAll("\\s+", " ");

        switch (text) {
            case "turn on", "power on" -> {
                setPower(true);
                return true;
            }
            case "turn off", "power off" -> {
                setPower(false);
                return true;
            }
            case "volume up", "louder" -> {
                volumeUp();
                return true;
            }
            case "volume down", "quieter" -> {
                volumeDown();
                return true;
            }
            case "mute" -> {
                mute();
                return true;
            }
            case "channel up", "next channel" -> {
                channelUp();
                return true;
            }
            case "channel down", "previous channel" -> {
                channelDown();
                return true;
            }
            default -> {
                return handleCommandWithArgument(text, command.trim());
            }
        }
    }

    private boolean handleCommandWithArgument(String text, String original) {
        if (text.startsWith("volume ")) {
            Integer value = parseNumber(text.substring("volume ".length()));
            if (value != null) {
                device.setVolume(value);
                System.out.println("Remote: volume set to " + device.getVolume() + "%");
                return true;
            }
        } else if (text.startsWith("channel ")) {
            Integer value = parseNumber(text.substring("channel ".length()));
            if (value != null) {
                device.setChannel(value);
                System.out.println("Remote: channel set to " + device.getChannel());
                return true;
            }
        } else if (text.startsWith("open ")) {
            return openApp(original.substring("open ".length()).trim());
        } else if (text.startsWith("browse ")) {
            return browseInternet(original.substring("browse ".length()).trim());
        } else if (text.startsWith("go to ")) {
            return browseInternet(original.substring("go to ".length()).trim());
        }
        System.out.println("Remote: sorry, I did not understand \"" + original + "\"");
        return false;
    }

    private boolean openApp(String appName) {
        if (!(device instanceof SmartDevice smartDevice)) {
            System.out.println("Remote: this device cannot run apps");
            return false;
        }
        if (!smartDevice.isEnabled()) {
            smartDevice.enable();
        }
        smartDevice.launchApp(appName);
        System.out.println("Remote: " + appName + " is running");
        return true;
    }

    /** Unlike the power() toggle, a spoken "turn on" never switches the device off. */
    private void setPower(boolean on) {
        if (device.isEnabled() != on) {
            power();
        }
    }

    private static Integer parseNumber(String text) {
        try {
            return Integer.parseInt(text.trim());
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
