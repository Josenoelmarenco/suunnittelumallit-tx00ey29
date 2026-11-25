package chat;

import javafx.application.Application;

/**
 * Launcher. Starting the JavaFX application from a class that does NOT extend
 * Application lets it run from the IDE on the classpath, without a module-info.
 */
public class Main {
    public static void main(String[] args) {
        Application.launch(ChatApp.class, args);
    }
}
