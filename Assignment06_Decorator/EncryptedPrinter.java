//Concrete Decorators	EncryptedPrinter, XMLPrinter
// Transforman el mensaje y delegan
public class EncryptedPrinter extends PrinterDecorator {

    private static final int SHIFT = 3;   // Caesar cipher; decrypt by shifting back 3

    public EncryptedPrinter(Printer wrapped) {
        super(wrapped);
    }

    @Override
    public void print(String message) {
        wrapped.print(encrypt(message));  // encrypt, then delegate to the wrapped printer
    }

    private String encrypt(String text) {
        StringBuilder sb = new StringBuilder();
        for (char c : text.toCharArray()) {
            if (Character.isUpperCase(c))      sb.append((char) ('A' + (c - 'A' + SHIFT) % 26));
            else if (Character.isLowerCase(c)) sb.append((char) ('a' + (c - 'a' + SHIFT) % 26));
            else                               sb.append(c);   // spaces, punctuation unchanged
        }
        return sb.toString();
    }
}