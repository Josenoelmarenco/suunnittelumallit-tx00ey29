//Concrete Component	BasicPrinter
// El objeto base real; imprime a consola
public class BasicPrinter implements Printer {
    @Override
    public void print(String message) {
        System.out.println(message);
    }
}