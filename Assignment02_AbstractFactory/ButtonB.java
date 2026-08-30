/** Style B button: framed with asterisks. */
public class ButtonB extends Button {
    public ButtonB(String text) { super(text); }

    @Override
    public void display() {
        String content = "* " + text + " *";
        String border  = "*".repeat(content.length());
        System.out.println(border);
        System.out.println(content);
        System.out.println(border);
    }
}
