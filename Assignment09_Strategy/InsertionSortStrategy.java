/**
 * Concrete Strategy: Insertion Sort.
 *
 * Source / attribution:
 *   Adapted from GeeksforGeeks - "Insertion Sort Algorithm"
 *   https://www.geeksforgeeks.org/insertion-sort-algorithm/
 *
 * How it works: builds the sorted array one element at a time. For each element
 * (the "key"), it shifts every larger element in the already-sorted left part
 * one position to the right, then drops the key into the gap.
 *
 * Time complexity: O(n^2) worst/average - slow on large inputs, but fast on
 * small or nearly-sorted arrays.
 */
public class InsertionSortStrategy implements SortStrategy {

    @Override
    public int[] sort(int[] data) {
        for (int i = 1; i < data.length; i++) {
            int key = data[i];
            int j = i - 1;
            while (j >= 0 && data[j] > key) {
                data[j + 1] = data[j];   // shift larger element to the right
                j--;
            }
            data[j + 1] = key;           // insert the key into its place
        }
        return data;
    }

    @Override
    public String getName() {
        return "Insertion Sort (O(n^2))";
    }
}
