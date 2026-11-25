package chat;

import java.util.List;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * The JavaFX window of one chat client (the user interface).
 *
 * It only displays things and passes the user's actions to its controller;
 * it has no chat logic and knows nothing about the other clients.
 */
public class ChatWindow implements ChatView {

    private final ChatController controller;
    private final Stage stage = new Stage();
    private final TextArea messageArea = new TextArea();
    private final ComboBox<String> recipientBox = new ComboBox<>();
    private final TextField messageField = new TextField();
    private final Button sendButton = new Button("Send");
    private final Label noticeLabel = new Label();

    public ChatWindow(ChatController controller) {
        this.controller = controller;

        messageArea.setEditable(false);           // history only, the user cannot type here
        messageArea.setWrapText(true);
        recipientBox.setPromptText("Recipient");
        messageField.setPromptText("Type a message");

        sendButton.setOnAction(event -> send());
        messageField.setOnAction(event -> send()); // pressing Enter also sends

        HBox inputRow = new HBox(8, new Label("To:"), recipientBox, messageField, sendButton);
        inputRow.setAlignment(Pos.CENTER_LEFT);
        HBox.setHgrow(messageField, Priority.ALWAYS);

        VBox root = new VBox(8, messageArea, inputRow, noticeLabel);
        root.setPadding(new Insets(10));
        VBox.setVgrow(messageArea, Priority.ALWAYS);

        stage.setScene(new Scene(root, 460, 320));
        stage.setTitle("Chat - " + controller.getUsername());
    }

    /** Opens the window at the given screen position. */
    public void show(double x, double y) {
        stage.setX(x);
        stage.setY(y);
        stage.show();
    }

    private void send() {
        controller.sendMessage(recipientBox.getValue(), messageField.getText());
    }

    // --- ChatView: called by the controller ------------------------------------

    @Override
    public void showMessage(String line) {
        messageArea.appendText(line + "\n");
    }

    @Override
    public void showNotice(String notice) {
        noticeLabel.setText(notice);
    }

    @Override
    public void setRecipients(List<String> usernames) {
        String selected = recipientBox.getValue();
        recipientBox.getItems().setAll(usernames);
        if (selected != null && usernames.contains(selected)) {
            recipientBox.setValue(selected);       // keep the current choice if it still exists
        }
    }

    @Override
    public void clearMessageInput() {
        messageField.clear();
    }
}
