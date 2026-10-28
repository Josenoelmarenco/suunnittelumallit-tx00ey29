package memento.guistate;

import java.time.LocalDateTime;

/**
 * Metadata interface of the memento (narrow interface).
 *
 * The caretaker (Controller) and the history window see ONLY this interface:
 * they can read metadata about a saved state, but they cannot read or change
 * the state itself. Only the originator (Model) casts it back to Memento.
 */
public interface IMemento {

    /** When this state was saved. */
    LocalDateTime getSavedAt();

    /** One-line summary shown in the history window. */
    String getDescription();
}
