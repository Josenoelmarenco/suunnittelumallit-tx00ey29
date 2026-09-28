# Assignment 09 — Sorting algorithm benchmark (Strategy pattern)

A console application that implements **four sorting algorithms** as
interchangeable **strategies** and compares their empirical performance on a
small (~30) and a large (~100,000) dataset of random integers.

## The Strategy pattern in this project

| Role (GoF / course slides) | Class | Responsibility |
|----------------------------|-------|----------------|
| **Strategy** | `SortStrategy` | Interface declaring `sort(int[])` — the common interface for all algorithms. |
| **Concrete Strategy** | `BubbleSortStrategy`, `InsertionSortStrategy`, `QuickSortStrategy`, `MergeSortStrategy` | Each implements one sorting algorithm. |
| **Context** | `SortContext` | Holds a `SortStrategy`, delegates the sorting to it, and can swap it at runtime via `setStrategy()`. Also times the sort. |
| **Client** | `Main` | Builds the datasets and, through the context, runs each algorithm and prints the timings. |

The client selects an algorithm by calling `context.setStrategy(...)`. The
context never knows which concrete algorithm it holds, and the algorithm can be
changed at runtime — exactly the intent of the Strategy pattern: *encapsulate a
family of interchangeable algorithms behind one interface so the client can
pick among them freely.* This is also what lets the benchmark loop reuse a
single `SortContext` for all four algorithms.

## Algorithms and attribution

All algorithms are hand-written (no built-in sort). Each was studied and then
adapted to the Strategy structure. Sources are cited in each file's header:

| Algorithm | Complexity | Source |
|-----------|-----------|--------|
| Bubble Sort | O(n²) | [GeeksforGeeks — Bubble Sort](https://www.geeksforgeeks.org/bubble-sort-algorithm/) |
| Insertion Sort | O(n²) | [GeeksforGeeks — Insertion Sort](https://www.geeksforgeeks.org/insertion-sort-algorithm/) |
| Quick Sort | O(n log n) avg | [GeeksforGeeks — QuickSort](https://www.geeksforgeeks.org/quick-sort-algorithm/) |
| Merge Sort | O(n log n) | [GeeksforGeeks — Merge Sort](https://www.geeksforgeeks.org/merge-sort/) |

> **Constraint respected:** the application uses no built-in sorting method
> (`java.util.Arrays.sort()` or any other). `array.clone()` is used only to copy
> a dataset before timing — copying is not sorting.

## Sample results

Each run generates fresh random data, so exact numbers vary, but the pattern is
always the same — the O(n²) algorithms are orders of magnitude slower on the
large dataset:

```
---- LARGE dataset (100000 elements) ----
Strategy                          Time (ms)   Sorted?
-----------------------------------------------------------------
Bubble Sort    (O(n^2))           20400.327   yes
Insertion Sort (O(n^2))            1969.921   yes
Quick Sort     (O(n log n))          16.635   yes
Merge Sort     (O(n log n))          27.923   yes
```

On 100,000 elements Quick Sort finished ~1,200× faster than Bubble Sort. On the
30-element dataset all four finish in well under a millisecond, so the choice of
algorithm barely matters — the difference only appears at scale.

## How to compile and run

```bash
javac *.java
java Main
```

> Note: Bubble Sort and Insertion Sort on 100,000 elements are O(n²) and take
> several seconds to tens of seconds — that slowness is exactly the point of the
> comparison. Dataset sizes are constants at the top of `Main.java`
> (`SMALL_SIZE`, `LARGE_SIZE`) if you want to change them.

## Files

- `SortStrategy.java` — the Strategy interface.
- `BubbleSortStrategy.java`, `InsertionSortStrategy.java`, `QuickSortStrategy.java`, `MergeSortStrategy.java` — the four concrete strategies.
- `SortContext.java` — the Context (delegates + times the sort).
- `SortResult.java` — small value object (algorithm name, elapsed time, sorted array).
- `Main.java` — the client: builds datasets and runs the benchmark.
