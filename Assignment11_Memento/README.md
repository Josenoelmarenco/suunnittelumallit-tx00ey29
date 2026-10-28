# Assignment 11 — Improved history functionality (Memento pattern)

An extension of the course's Memento example (`memento.guistate`): a JavaFX
window with three colored rectangles and a checkbox whose state is saved as
mementos. The original example only supported undo with `Ctrl+Z`.

**Both tasks of the assignment are implemented:**

1. **Redo functionality** — `Ctrl+Y` re-applies the last undone action.
2. **History window** — a second window lists every saved state with its
   timestamp; clicking an entry restores the model to that state.

## The pattern in this project

| Role (GoF / course slides) | Class | Responsibility |
|----------------------------|-------|----------------|
| **Originator** | `Model` | Owns the state. Creates mementos (`createMemento`) and restores itself from them (`restoreState`). The only class that can read the state inside a memento. |
| **Memento** | `Memento` | The snapshot: the three options, the checkbox flag and the time it was taken. |
| **Narrow interface** | `IMemento` | What everyone else sees: metadata only (`getSavedAt`, `getDescription`), no access to the state. |
| **Caretaker** | `Controller` | Keeps the mementos in the undo and redo lists. Stores and moves them, never looks inside. |
| **Caretaker (view)** | `HistoryWindow` | Lists the mementos by their metadata and asks the controller to restore one. |
| **Client** | `Gui` | Captures the key combinations and the button, and redraws after a restore. |

The two-interface idea is the point of the pattern: `Model` uses the **wide**
interface (the `Memento` class), while `Controller` and `HistoryWindow` only
ever use the **narrow** one (`IMemento`). The model's state travels across the
application without its encapsulation being broken.

## Task 1 — Redo

A second memento list was added next to the existing history:

```java
private final ObservableList<IMemento> history = FXCollections.observableArrayList();
private final ObservableList<IMemento> redoList = FXCollections.observableArrayList();
```

`undo()` and `redo()` are mirror images of each other — the same method with
the two lists swapped:

- `undo()` pushes the **current** state onto the redo list, pops the last state
  off the history and restores it.
- `redo()` pushes the **current** state onto the history, pops the last state
  off the redo list and restores it.

The redo branch is discarded in `saveToHistory()`:

```java
private void saveToHistory() {
    IMemento currentState = model.createMemento();
    history.add(currentState);
    redoList.clear();   // a new change invalidates the redo branch
}
```

That method is the single place every user change passes through
(`setOption` and `setIsSelected` both call it), and neither `undo()` nor
`redo()` calls it — so the redo list is cleared exactly when it should be, and
never by accident.

`Gui` handles the new key combination next to the existing one:

```java
} else if (event.isControlDown() && event.getCode() == KeyCode.Y) {
    // Ctrl-Y: redo
    System.out.println("Redo key combination pressed");
    controller.redo();
}
```

## Task 2 — History window

**Metadata on the narrow interface.** `IMemento` was empty in the original
example; it now declares what the history window needs:

```java
public interface IMemento {
    LocalDateTime getSavedAt();
    String getDescription();
}
```

`Memento` records `LocalDateTime.now()` in its constructor and builds a
one-line summary such as `19:42:07   boxes: 1-2-0   checkbox: ON`. The
description is written from the model's own data (option numbers), not from UI
colors, so the memento stays independent of the view.

**The window.** `HistoryWindow` is a new `Stage` holding a `ListView<IMemento>`:

- The history in `Controller` is an `ObservableList`, and the `ListView` is
  bound to it with `setItems(...)`, so the window refreshes itself whenever a
  state is added or removed. No manual refresh call anywhere.
- A `setCellFactory` renders each row with `getDescription()` instead of the
  default `toString()`.
- Clicking a row calls `Controller.restoreFromHistory(selected)`.

**Jumping to a state.** Restoring an arbitrary state is the same as pressing
`Ctrl+Z` several times, so everything saved after the selected state is moved
to the redo list, newest first, keeping `Ctrl+Y` able to walk forward again:

```java
public void restoreFromHistory(IMemento memento) {
    int index = history.indexOf(memento);
    if (index < 0) {
        return;
    }
    redoList.add(model.createMemento());
    for (int i = history.size() - 1; i > index; i--) {
        redoList.add(history.remove(i));
    }
    history.remove(index);
    model.restoreState(memento);
    gui.updateGui();
}
```

When the selected entry is the last one, the loop does not run and the method
behaves exactly like `undo()`.

## Design choices

- **Existing behaviour unchanged.** `Ctrl+Z` does what it always did; the only
  addition is one line that makes the current state redoable.
- **`Model`, `ColorBox` and `Main` were not modified at all.** The change is
  confined to the caretaker, the memento, its interface and the view.
- **The redo list is cleared in one place**, not at each call site, so it
  cannot be forgotten when new actions are added later.
- **`ObservableList` instead of `ArrayList`** for the history, so the view is
  driven by the data rather than by explicit refresh calls.
- **The history window is a caretaker too**, and is written against `IMemento`
  only — it never casts to `Memento`.

## Console output

The program keeps the original example's logging. A session where the user
clicks box 1 once, box 2 twice, ticks the checkbox, then presses `Ctrl+Z` and
`Ctrl+Y` prints:

```
Memento created
optionNumber: 1 choice: 1
Memento created
optionNumber: 2 choice: 1
Memento created
optionNumber: 2 choice: 2
Memento created
isSelected: true
Undo key combination pressed
Memento found in history
Memento created
options: 1 2 0
isSelected: false
State restored
Redo key combination pressed
Memento found in redo list
Memento created
options: 1 2 0
isSelected: true
State restored
```

The `Memento created` line printed during undo and redo is the **current**
state being saved onto the opposite list before the restore happens.

## How to compile and run

This assignment extends the teacher's JavaFX example, so unlike the earlier
assignments it keeps the original `memento.guistate` package and needs the
JavaFX dependencies. It is a self-contained Maven project:

```bash
mvn compile
mvn exec:java -Dexec.mainClass=memento.guistate.Main
```

Or open this folder in IntelliJ IDEA (it is detected as a Maven project) and
run `Main`.

On macOS the shortcuts use the **Control** key, not Command, because the
original example checks `event.isControlDown()`.

## Files

Modified from the teacher's example:

- `IMemento.java` — the narrow interface; now declares the metadata methods.
- `Memento.java` — stores the timestamp and implements the metadata methods.
- `Controller.java` — redo list, `redo()`, `restoreFromHistory()`, observable history.
- `Gui.java` — `Ctrl+Y` shortcut and the button that opens the history window.

New:

- `HistoryWindow.java` — the second window with the `ListView` of saved states.

Unchanged from the teacher's example:

- `Model.java`, `ColorBox.java`, `Main.java`
