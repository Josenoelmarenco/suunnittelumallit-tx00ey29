package memento.guistate;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Second window listing the saved states.
 *
 * It is a caretaker as well: it only uses the IMemento metadata interface
 * and never reads or writes the state inside the memento.
 */
public class HistoryWindow {

    private final Stage stage = new Stage();
    private final ListView<IMemento> listView = new ListView<>();

    public HistoryWindow(Controller controller) {

        // Bound to the controller's observable list, so the view updates itself
        // whenever a state is added or removed.
        listView.setItems(controller.getHistory());

        // Show each memento's metadata instead of its toString().
        listView.setCellFactory(list -> new ListCell<IMemento>() {
            @Override
            protected void updateItem(IMemento memento, boolean empty) {
                super.updateItem(memento, empty);
                if (empty || memento == null) {
                    setText(null);
                } else {
                    setText(memento.getDescription());
                }
            }
        });

        listView.setOnMouseClicked(event -> {
            IMemento selected = listView.getSelectionModel().getSelectedItem();
            if (selected != null) {
                System.out.println("State selected in history window");
                controller.restoreFromHistory(selected);
            }
        });

        Label help = new Label("Click a saved state to restore it.");
        help.setPadding(new Insets(10, 10, 10, 10));

        VBox root = new VBox(help, listView);
        stage.setScene(new Scene(root, 340, 320));
        stage.setTitle("State history");
    }

    public void show() {
        stage.show();
        stage.toFront();
    }
}
