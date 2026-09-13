public class XMLPrinter extends PrinterDecorator {

    public XMLPrinter(Printer wrapped) {
        super(wrapped);
    }

    @Override
    public void print(String message) {
        wrapped.print("<message>" + message + "</message>");
    }
}