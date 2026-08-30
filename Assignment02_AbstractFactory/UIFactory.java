/**
 * Abstract Factory: declares one creation method per product type.
 * A concrete factory produces a whole FAMILY of elements in ONE consistent style.
 */
public abstract class UIFactory {
    public abstract Button    createButton(String text);
    public abstract TextField createTextField(String text);
    public abstract Checkbox  createCheckbox(String text);
}
