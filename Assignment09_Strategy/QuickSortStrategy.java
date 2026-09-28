/**
 * Concrete Strategy: Quick Sort.
 *
 * Source / attribution:
 *   Adapted from GeeksforGeeks - "QuickSort" (Lomuto partition scheme)
 *   https://www.geeksforgeeks.org/quick-sort-algorithm/
 *
 * How it works: picks the last element as a pivot, partitions the array so that
 * smaller elements go to its left and larger to its right, then recursively
 * sorts the two partitions. Divide and conquer.
 *
 * Time complexity: O(n log n) on average (random data), O(n^2) worst case.
 */
public class QuickSortStrategy implements SortStrategy {

    @Override
    public int[] sort(int[] data) {
        quickSort(data, 0, data.length - 1);
        return data;
    }

    private void quickSort(int[] a, int low, int high) {
        if (low < high) {
            int pivotIndex = partition(a, low, high);
            quickSort(a, low, pivotIndex - 1);
            quickSort(a, pivotIndex + 1, high);
        }
    }

    private int partition(int[] a, int low, int high) {
        int pivot = a[high];         // last element as pivot (Lomuto scheme)
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (a[j] < pivot) {
                i++;
                swap(a, i, j);
            }
        }
        swap(a, i + 1, high);
        return i + 1;
    }

    private void swap(int[] a, int x, int y) {
        int temp = a[x];
        a[x] = a[y];
        a[y] = temp;
    }

    @Override
    public String getName() {
        return "Quick Sort     (O(n log n))";
    }
}
