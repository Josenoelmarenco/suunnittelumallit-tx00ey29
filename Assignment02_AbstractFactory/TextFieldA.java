/** Style A text field: brackets with an underscore fill. */
public class TextFieldA extends TextField {
    public TextFieldA(String text) { super(text); }

    @Override
    public void display() {
        int fill = Math.max(12 - text.length(), 3);
        System.out.println("[ " + text + "_".repeat(fill) + " ]");
    }
}
