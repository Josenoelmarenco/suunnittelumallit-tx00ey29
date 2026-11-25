package chat;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Builds the chat: one mediator and, for every user, a controller and a window.
 */
public class ChatApp extends Application {

    private static final String[] USERNAMES = {"Alice", "Bob", "Charlie"};

    @Override
    public void start(Stage primaryStage) {
        ChatMediator chatRoom = new ChatRoom();

        for (int i = 0; i < USERNAMES.length; i++) {
            ChatController controller = new ChatController(USERNAMES[i], chatRoom);
            ChatWindow window = new ChatWindow(controller);
            controller.setView(window);           // the view must exist before registering
            chatRoom.register(controller);
            window.show(60 + i * 480, 120);       // side by side on the screen
        }
    }
}
