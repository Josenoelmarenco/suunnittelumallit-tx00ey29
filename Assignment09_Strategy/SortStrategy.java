/**
 * Strategy (Strategy design pattern).
 *
 * Declares the common interface shared by every sorting algorithm. The Context
 * uses this interface and never depends on a concrete algorithm, so algorithms
 * can be swapped at runtime.
 */
public interface SortStrategy {

    /**
     * Sorts the given array in ascending order and returns it.
     * Implementations may sort in place; the Context always passes a copy so
     * the caller's original data is preserved.
     */
    int[] sort(int[] data);

    /** Human-readable name of the algorithm (used in the results table). */
    String getName();
}
