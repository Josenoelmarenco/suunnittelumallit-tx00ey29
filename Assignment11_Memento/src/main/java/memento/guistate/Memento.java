package memento.guistate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Memento implements IMemento {

    private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm:ss");

    private int[] options;
    private boolean isSelected;

    /** Metadata: the moment this snapshot was taken. */
    private final LocalDateTime savedAt;

    public Memento(int[] options, boolean isSelected) {
        this.options = options.clone(); // Copy options array
        this.isSelected = isSelected;
        this.savedAt = LocalDateTime.now();
        System.out.println("Memento created");
    }

    public int[] getOptions() {
        return options;
    }

    public boolean isSelected() {
        return isSelected;
    }

    @Override
    public LocalDateTime getSavedAt() {
        return savedAt;
    }

    @Override
    public String getDescription() {
        return TIME_FORMAT.format(savedAt)
                + "   boxes: " + options[0] + "-" + options[1] + "-" + options[2]
                + "   checkbox: " + (isSelected ? "ON" : "OFF");
    }
}
