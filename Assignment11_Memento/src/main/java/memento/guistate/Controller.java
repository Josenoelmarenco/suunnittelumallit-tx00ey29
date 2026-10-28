package memento.guistate;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Controller {
    private Model model;
    private Gui gui;

    /** Undo stack. Observable so the history window refreshes by itself. */
    private final ObservableList<IMemento> history = FXCollections.observableArrayList();

    /** Redo stack: states that were undone and can be applied again. */
    private final ObservableList<IMemento> redoList = FXCollections.observableArrayList();

    public Controller(Gui gui) {
        this.model = new Model();
        this.gui = gui;
    }

    public void setOption(int optionNumber, int choice) {
        saveToHistory();
        model.setOption(optionNumber, choice);
    }

    public int getOption(int optionNumber) {
        return model.getOption(optionNumber);
    }

    public void setIsSelected(boolean isSelected) {
        saveToHistory();
        model.setIsSelected(isSelected);
    }

    public boolean getIsSelected() {
        return model.getIsSelected();
    }

    public void undo() {
        if (history.isEmpty()) {
            return;
        }
        System.out.println("Memento found in history");
        redoList.add(model.createMemento());                        // current state becomes redoable
        IMemento previousState = history.remove(history.size() - 1);
        model.restoreState(previousState);
        gui.updateGui();
    }

    public void redo() {
        if (redoList.isEmpty()) {
            return;
        }
        System.out.println("Memento found in redo list");
        history.add(model.createMemento());                         // current state becomes undoable
        IMemento nextState = redoList.remove(redoList.size() - 1);
        model.restoreState(nextState);
        gui.updateGui();
    }

    /** Jump directly to a state picked in the history window. */
    public void restoreFromHistory(IMemento memento) {
        int index = history.indexOf(memento);
        if (index < 0) {
            return;
        }
        // Everything saved after the selected state becomes redoable, newest
        // first, so Ctrl+Y then walks forward one step at a time.
        redoList.add(model.createMemento());
        for (int i = history.size() - 1; i > index; i--) {
            redoList.add(history.remove(i));
        }
        history.remove(index);
        model.restoreState(memento);
        gui.updateGui();
    }

    /** Read-only view used by the history window's ListView. */
    public ObservableList<IMemento> getHistory() {
        return history;
    }

    private void saveToHistory() {
        IMemento currentState = model.createMemento();
        history.add(currentState);
        redoList.clear();   // a new change invalidates the redo branch
    }
}
