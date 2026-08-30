/** Style A button: full box drawn with + - | */
public class ButtonA extends Button {
    public ButtonA(String text) { super(text); }

    @Override
    public void display() {
        String content = " " + text + " ";
        String border  = "+" + "-".repeat(content.length()) + "+";
        System.out.println(border);
        System.out.println("|" + content + "|");
        System.out.println(border);
    }
}
