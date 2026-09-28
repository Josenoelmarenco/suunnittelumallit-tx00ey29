/**
 * Small value object that carries the outcome of one sort run:
 * which algorithm ran, how long it took, and the sorted array.
 */
public class SortResult {

    private final String strategyName;
    private final long elapsedNanos;
    private final int[] sorted;

    public SortResult(String strategyName, long elapsedNanos, int[] sorted) {
        this.strategyName = strategyName;
        this.elapsedNanos = elapsedNanos;
        this.sorted = sorted;
    }

    public String getStrategyName() {
        return strategyName;
    }

    public long getElapsedNanos() {
        return elapsedNanos;
    }

    public double getElapsedMillis() {
        return elapsedNanos / 1_000_000.0;
    }

    public int[] getSorted() {
        return sorted;
    }
}
