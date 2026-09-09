# Observer — Weather Station Simulator

Solution for the *Observer* design-pattern assignment.

A `WeatherStation` runs in **its own thread**, changing the temperature by +/- 1
degree at random 1-5 s intervals, and **notifies** every registered observer on
each change, using the **Observer** pattern.

## Design

| Role in the pattern | Class |
|---|---|
| Subject (interface) | `Subject` — `registerObserver` / `removeObserver` / `notifyObservers` |
| Concrete subject    | `WeatherStation` (implements `Subject`, `Runnable`) — random start temp, eternal loop, min/max bounds |
| Observer (interface)| `Observer` — `update(int temperature)` |
| Concrete observers  | `PhoneDisplay`, `WindowDisplay`, `TemperatureAlert` (each shows a unique message) |
| Client              | `Main` |

The station knows its observers only through the `Observer` interface (loose
coupling): new displays can be added without changing `WeatherStation`.

## Requirements mapping

- Runs in its own thread + notifies after each update (req 1-2): `WeatherStation implements Runnable`, `notifyObservers()`.
- Unique message incl. temperature (req 3): the three concrete observers.
- Random initial temperature (req 4): the constructor.
- Eternal loop, +/- 1 deg, random 1-5 s, min/max bounds (req 5): `run()`, `MIN_TEMP`/`MAX_TEMP`.
- `main()` starts the thread, registers observers, then removes one and continues (req 6): `Main`.

## Build & run

```bash
javac *.java
java Main
```

## Example output

```
Weather station starting. Initial temperature: -30 C

[Phone/Nokia] Weather app: it is now -30 C outside.
[Window display] -30 C - looks freezing today.
[Alert sensor] -30 C - within safe range.

--- Removing the phone display (it will no longer be notified) ---

[Window display] -29 C - looks freezing today.
[Alert sensor] -29 C - within safe range.

--- Simulation ended ---
```
