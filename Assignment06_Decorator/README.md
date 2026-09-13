# Decorator — Customizable Printer

Solution for the *Decorator* design-pattern assignment.

A `Printer` prints a message; decorators wrap a printer and add behavior,
delegating to the wrapped one. They can be stacked in any order.

| Role | Class |
|---|---|
| Component (interface) | `Printer` — `print(String)` |
| Concrete component | `BasicPrinter` — prints to the console |
| Base decorator (abstract) | `PrinterDecorator` — holds the wrapped `Printer` |
| Concrete decorators | `EncryptedPrinter` (Caesar cipher, shift 3), `XMLPrinter` (`<message>…</message>`) |
| Client | `Main` |

## Build & run
    javac *.java
    java Main

## Example output
    Hello World!
    <message>Khoor Zruog!</message>

`Khoor Zruog!` is `Hello World!` encrypted with a Caesar cipher (shift +3);
decrypt by shifting each letter back by 3.