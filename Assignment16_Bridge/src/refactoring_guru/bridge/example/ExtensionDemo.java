package refactoring_guru.bridge.example;

import refactoring_guru.bridge.example.devices.Device;
import refactoring_guru.bridge.example.devices.Radio;
import refactoring_guru.bridge.example.devices.SmartTv;
import refactoring_guru.bridge.example.devices.Tv;
import refactoring_guru.bridge.example.remotes.AdvancedRemote;
import refactoring_guru.bridge.example.remotes.SmartRemote;

/**
 * Demonstrates the extension. The original Demo class is not modified.
 */
public class ExtensionDemo {

    public static void main(String[] args) {
        System.out.println("=== 1. New remote + new device: SmartRemote with SmartTv ===");
        SmartTv smartTv = new SmartTv();
        SmartRemote smartRemote = new SmartRemote(smartTv);
        smartRemote.voiceControl("Turn on");
        smartRemote.voiceControl("Volume 45");
        smartRemote.voiceControl("Channel 7");
        smartRemote.voiceControl("Open YouTube");
        smartTv.printStatus();
        smartRemote.browseInternet("https://refactoring.guru/design-patterns/bridge");
        smartRemote.voiceControl("go to https://www.metropolia.fi");
        smartTv.printStatus();
        System.out.println("Browsing history: " + smartTv.getBrowsingHistory() + "\n");

        System.out.println("=== 2. New remote + original devices: SmartRemote with Tv and Radio ===");
        for (Device device : new Device[] {new Tv(), new Radio()}) {
            SmartRemote remote = new SmartRemote(device);
            remote.voiceControl("turn on");
            remote.voiceControl("louder");
            remote.voiceControl("next channel");
            remote.voiceControl("open Netflix");
            remote.browseInternet("https://www.metropolia.fi");
            device.printStatus();
        }

        System.out.println("=== 3. Original remote + new device: AdvancedRemote with SmartTv ===");
        SmartTv secondTv = new SmartTv();
        AdvancedRemote advancedRemote = new AdvancedRemote(secondTv);
        advancedRemote.power();
        advancedRemote.volumeUp();
        advancedRemote.channelUp();
        advancedRemote.mute();
        secondTv.printStatus();

        System.out.println("=== 4. Original test routine (Demo.testDevice) with the new device ===");
        Demo.testDevice(new SmartTv());

        System.out.println("=== 5. Commands that are not understood ===");
        smartRemote.voiceControl("make coffee");
        smartRemote.voiceControl("volume loud");
    }
}
