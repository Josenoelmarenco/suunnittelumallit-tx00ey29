/**
 * Concrete Strategy: Bubble Sort.
 *
 * Source / attribution:
 *   Adapted from GeeksforGeeks - "Bubble Sort Algorithm"
 *   https://www.geeksforgeeks.org/bubble-sort-algorithm/
 *
 * How it works: repeatedly steps through the array, comparing adjacent pairs
 * and swapping them if they are out of order. After each pass the largest
 * remaining element "bubbles up" to its final position. The `swapped` flag
 * lets it stop early if the array becomes sorted before all passes run.
 *
 * Time complexity: O(n^2) - very slow on large inputs.
 */
public class BubbleSortStrategy implements SortStrategy {

    @Override
    public int[] sort(int[] data) {
        int n = data.length;
        for (int i = 0; i < n - 1; i++) {
            boolean swapped = false;
            for (int j = 0; j < n - 1 - i; j++) {
                if (data[j] > data[j + 1]) {
                    int temp = data[j];
                    data[j] = data[j + 1];
                    data[j + 1] = temp;
                    swapped = true;
                }
            }
            if (!swapped) {
                break;   // no swaps in this pass => already sorted
            }
        }
        return data;
    }

    @Override
    public String getName() {
        return "Bubble Sort    (O(n^2))";
    }
}
