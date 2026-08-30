/** Style B text field: label followed by a dotted fill. */
public class TextFieldB extends TextField {
    public TextFieldB(String text) { super(text); }

    @Override
    public void display() {
        int fill = Math.max(12 - text.length(), 3);
        System.out.println(text + ":".repeat(fill));
    }
}
