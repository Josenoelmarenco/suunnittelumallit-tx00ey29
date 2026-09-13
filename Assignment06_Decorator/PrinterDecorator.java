public abstract class PrinterDecorator implements Printer {
    protected final Printer wrapped;      // the printer being decorated

    protected PrinterDecorator(Printer wrapped) {
        this.wrapped = wrapped;
    }
}