/**
 * Context (Strategy design pattern).
 *
 * Maintains a reference to a SortStrategy and delegates the sorting to it. The
 * concrete algorithm can be replaced at runtime with setStrategy(); the context
 * itself stays the same and never knows which algorithm it is using.
 *
 * executeSort() also measures how long the sort takes. It sorts a COPY of the
 * data so the same original dataset can be reused with every strategy, and so
 * the copy (which is not a sorting operation) is kept out of the timed section.
 */
public class SortContext {

    private SortStrategy strategy;

    public SortContext(SortStrategy strategy) {
        this.strategy = strategy;
    }

    /** Change the algorithm at runtime. */
    public void setStrategy(SortStrategy strategy) {
        this.strategy = strategy;
    }

    public String getStrategyName() {
        return strategy.getName();
    }

    /** Runs the current strategy on a copy of the data and times only the sort. */
    public SortResult executeSort(int[] data) {
        int[] copy = data.clone();                 // copy is not a sort -> allowed
        long start = System.nanoTime();
        int[] sorted = strategy.sort(copy);        // delegate to the chosen algorithm
        long elapsed = System.nanoTime() - start;
        return new SortResult(strategy.getName(), elapsed, sorted);
    }
}
