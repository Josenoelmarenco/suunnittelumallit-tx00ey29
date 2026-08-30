/**
 * Common superclass for EVERY UI element.
 *
 * Requirement 6 asks: setText() is common to all elements, so WHERE should it live?
 * Answer: here, in the shared ancestor. The 'text' state and setText() are pulled UP
 * so Button, TextField and Checkbox (and their A/B variants) inherit them once.
 * Each concrete subclass only implements how it draws itself in display().
 */
public abstract class UIElement {
    protected String text;               // shared state, visible to all subclasses

    public UIElement(String text) {
        this.text = text;
    }

    /** Change the content. It becomes visible the next time display() is called. */
    public void setText(String text) {
        this.text = text;
    }

    /** Each concrete element decides how to render itself in ASCII. */
    public abstract void display();
}
