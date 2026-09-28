import java.util.Random;

/**
 * Client (Strategy design pattern).
 *
 * Builds two random datasets (small and large), then runs every sorting
 * strategy on both through the SortContext, measuring and printing the
 * execution time of each combination. The client selects algorithms by calling
 * context.setStrategy(...) and never touches the algorithms' internals.
 *
 * Constraint respected: no built-in sorting method (java.util.Arrays.sort or
 * similar) is used anywhere - only the hand-written strategies.
 */
public class Main {

    private static final int SMALL_SIZE  = 30;
    private static final int LARGE_SIZE  = 100_000;
    private static final int VALUE_BOUND = 1_000_000;   // values in [0, VALUE_BOUND)

    public static void main(String[] args) {
        Random random = new Random();

        int[] smallData = randomArray(SMALL_SIZE, VALUE_BOUND, random);
        int[] largeData = randomArray(LARGE_SIZE, VALUE_BOUND, random);

        // All available algorithms, each as an independent Strategy object.
        SortStrategy[] strategies = {
                new BubbleSortStrategy(),
                new InsertionSortStrategy(),
                new QuickSortStrategy(),
                new MergeSortStrategy()
        };

        // One Context, reused for every algorithm (swapped at runtime).
        SortContext context = new SortContext(strategies[0]);

        System.out.println("=================================================================");
        System.out.println(" Strategy pattern - sorting algorithm benchmark");
        System.out.println("=================================================================");
        System.out.println(" Small dataset : " + SMALL_SIZE + " random integers");
        System.out.println(" Large dataset : " + LARGE_SIZE + " random integers");
        System.out.println(" Value range   : [0, " + VALUE_BOUND + ")");

        System.out.println("\n Small dataset (unsorted): " + preview(smallData, 30));

        SortResult lastSmall = runBenchmark(context, strategies, "SMALL", smallData);
        System.out.println(" Small dataset (sorted)  : " + preview(lastSmall.getSorted(), 30));

        runBenchmark(context, strategies, "LARGE", largeData);
    }

    /** Runs every strategy on one dataset and prints a results table. */
    private static SortResult runBenchmark(SortContext context, SortStrategy[] strategies,
                                           String label, int[] data) {
        System.out.println("\n---- " + label + " dataset (" + data.length + " elements) ----");
        System.out.printf("%-28s %14s   %s%n", "Strategy", "Time (ms)", "Sorted?");
        System.out.println("-----------------------------------------------------------------");

        SortResult last = null;
        for (SortStrategy strategy : strategies) {
            context.setStrategy(strategy);              // choose algorithm at runtime
            SortResult result = context.executeSort(data);
            boolean ok = isSorted(result.getSorted());  // verify correctness
            System.out.printf("%-28s %14.3f   %s%n",
                    result.getStrategyName(), result.getElapsedMillis(), ok ? "yes" : "FAILED");
            last = result;
        }
        return last;
    }

    // --- helpers ---------------------------------------------------------

    private static int[] randomArray(int size, int bound, Random random) {
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = random.nextInt(bound);
        }
        return array;
    }

    private static boolean isSorted(int[] array) {
        for (int i = 1; i < array.length; i++) {
            if (array[i - 1] > array[i]) {
                return false;
            }
        }
        return true;
    }

    private static String preview(int[] array, int count) {
        StringBuilder sb = new StringBuilder("[");
        int n = Math.min(count, array.length);
        for (int i = 0; i < n; i++) {
            sb.append(array[i]);
            if (i < n - 1) {
                sb.append(", ");
            }
        }
        if (array.length > n) {
            sb.append(", ...");
        }
        return sb.append("]").toString();
    }
}
