# Assignment 15 — Calendar adapter (Adapter pattern)

The client code wants a simple date interface, `NewDateInterface`, with
day / month / year getters and setters and an `advanceDays()` method. The JDK
already has a class that can do all of this, `java.util.Calendar`, but its
interface is completely different. `CalendarToNewDateAdapter` sits between the
two: it implements the interface the client expects and uses a `Calendar`
internally. This is the **Adapter** design pattern.

## The pattern in this project

| Role (GoF / course slides) | Class | Responsibility |
|----------------------------|-------|----------------|
| **Target** | `NewDateInterface` | The interface the client is written against. |
| **Adaptee** | `java.util.Calendar` | Existing class with useful behaviour but an incompatible interface. |
| **Adapter** | `CalendarToNewDateAdapter` | Implements the Target and translates each call into `Calendar` operations. |
| **Client** | `Main` | Uses only `NewDateInterface`; it never imports `Calendar`. |

This is an **object adapter**: the adapter *contains* a `Calendar` (composition)
instead of extending it. The client therefore cannot reach any of the
`Calendar` methods, and the adaptee could be replaced (for example by
`java.time.LocalDate`) without changing the client.

## What the adapter translates

| Client call | Inside the adapter |
|-------------|--------------------|
| `getMonth()` / `setMonth(m)` | `calendar.get(Calendar.MONTH) + 1` / `calendar.set(Calendar.MONTH, m - 1)` — `Calendar` months are **0-based** (January = 0), the interface uses 1–12. |
| `getDay()` / `setDay(d)` | `calendar.get/set(Calendar.DAY_OF_MONTH, …)` |
| `getYear()` / `setYear(y)` | `calendar.get/set(Calendar.YEAR, …)` |
| `advanceDays(n)` | `calendar.add(Calendar.DAY_OF_MONTH, n)` — `Calendar` handles month and year changes and leap years; a negative `n` moves back. |

On its own, `Calendar` is lenient: setting 31 February silently produces
3 March. The adapter keeps the date valid instead:

- `setDay()` and `setMonth()` reject values outside the valid range with an
  `IllegalArgumentException`, and the date stays unchanged.
- `setMonth()` and `setYear()` move a day that no longer exists to the last day
  of the month (31 January → `setMonth(2)` → 29 February 2024; then
  `setYear(2025)` → 28 February 2025). This matches how `java.time.LocalDate`
  behaves.

## Run

```bash
javac *.java
java Main
```

## Example output

The first line shows the current date, so it changes from day to day.

```
Today:                          11.11.2026

1) Set the date with the setters and advance it
  Set to 27.02.2024:               27.02.2024  (day=27, month=2, year=2024)
  +1 day:                          28.02.2024  (day=28, month=2, year=2024)
  +1 day (2024 is a leap year):    29.02.2024  (day=29, month=2, year=2024)
  +1 day (into March):             01.03.2024  (day=1, month=3, year=2024)
  +30 days:                        31.03.2024  (day=31, month=3, year=2024)
  +365 days:                       31.03.2025  (day=31, month=3, year=2025)
  -400 days:                       25.02.2024  (day=25, month=2, year=2024)

2) Crossing the end of a year
  Start:                           30.12.2025  (day=30, month=12, year=2025)
  +5 days:                         04.01.2026  (day=4, month=1, year=2026)

3) Changing month and year keeps the date valid
  Start:                           31.01.2024  (day=31, month=1, year=2024)
  setMonth(2):                     29.02.2024  (day=29, month=2, year=2024)
  setYear(2025), not a leap year:  28.02.2025  (day=28, month=2, year=2025)

4) Invalid values are rejected
  setDay(30) -> Day must be 1-28 for month 2/2025, got 30
  setMonth(13) -> Month must be 1-12, got 13
  Date unchanged:                  28.02.2025  (day=28, month=2, year=2025)
```
