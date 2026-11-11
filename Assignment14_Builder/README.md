# Assignment 14 — Computer builder (Builder pattern)

A console program that assembles different computer configurations (gaming,
office and fully custom) step by step. The same construction process produces
different products depending on which builder is used. This is the **Builder**
design pattern.

## The pattern in this project

| Role (GoF / course slides) | Class | Responsibility |
|----------------------------|-------|----------------|
| **Product** | `Computer` | The final configuration: processor, RAM (GB), hard drive, graphics card, operating system and accessories. |
| **Builder** | `ComputerBuilder` | Interface with one step per component: `buildProcessor`, `buildRAM`, `buildHardDrive`, `buildGraphicsCard`, `buildOperatingSystem`, `buildAccessories`, plus `reset` and `getComputer`. |
| **Concrete Builder** | `GamingComputerBuilder`, `OfficeComputerBuilder`, `CustomComputerBuilder` | Each decides *which* part goes into each step. |
| **Director** | `ComputerDirector` | Decides *which* steps run and in *what order* (`constructComputer`, `constructBareboneComputer`). |
| **Client** | `Main`, `ConfiguratorUI` | Pick a builder, hand it to the director, print the result. |

The director never names a concrete part, and the builders never decide the
order of the steps. Changing the builder changes the computer; changing the
director's method changes the recipe. Neither change touches the other class.

```java
public Computer constructComputer() {
    builder.reset();
    builder.buildProcessor();
    builder.buildRAM();
    builder.buildHardDrive();
    builder.buildGraphicsCard();
    builder.buildOperatingSystem();
    builder.buildAccessories();
    return builder.getComputer();
}
```

`getComputer()` returns the finished computer and resets the builder, so one
builder instance can be reused for the next computer without sharing state with
the previous one.

## Additional challenges

- **Component grades.** `GamingComputerBuilder` and `OfficeComputerBuilder`
  take a `Grade` (`BUDGET`, `STANDARD`, `PREMIUM`) that selects the tier of each
  part. The type of computer and the quality of its parts vary independently.
- **Accessories.** `buildAccessories()` adds optional extras such as a keyboard,
  a mouse or a monitor; premium builds get more of them.
- **Second recipe.** `constructBareboneComputer()` uses the same builders but
  skips the operating system and the accessories. `Computer` starts every
  component as `(not installed)`, so a skipped step still prints cleanly.
- **User interface.** `ConfiguratorUI` is a console configurator. The user
  selects each component from a numbered menu (invalid input is asked again),
  the choices go into a `CustomComputerBuilder`, and the same `ComputerDirector`
  assembles and displays the final configuration.

## Run

```bash
javac *.java
java Main            # demonstration: builders, grades and recipes
java ConfiguratorUI  # interactive configurator
```

## Example output (`java Main`, excerpt)

```
1) Same director, different builders

=== Gaming Computer (STANDARD) ===
  Processor:        AMD Ryzen 7 7800X3D
  RAM:              32 GB
  Hard drive:       2 TB NVMe SSD
  Graphics card:    NVIDIA GeForce RTX 5070 Ti
  Operating system: Windows 11 Home
  Accessories:      RGB mechanical keyboard, Gaming mouse

=== Office Computer (STANDARD) ===
  Processor:        Intel Core i5-14500
  RAM:              16 GB
  Hard drive:       512 GB SSD
  Graphics card:    Integrated Intel UHD Graphics
  Operating system: Windows 11 Pro
  Accessories:      Wireless keyboard and mouse, 24" Full HD monitor, HD webcam
```
