/**
 * Concrete Strategy: Merge Sort.
 *
 * Source / attribution:
 *   Adapted from GeeksforGeeks - "Merge Sort - Data Structure and Algorithms"
 *   https://www.geeksforgeeks.org/merge-sort/
 *
 * How it works: recursively splits the array in half until each piece has one
 * element, then merges the pieces back together in order. Stable divide and
 * conquer; guaranteed O(n log n) but uses extra memory for the temporary
 * left/right arrays during each merge.
 *
 * Time complexity: O(n log n) in all cases.
 */
public class MergeSortStrategy implements SortStrategy {

    @Override
    public int[] sort(int[] data) {
        if (data.length > 1) {
            mergeSort(data, 0, data.length - 1);
        }
        return data;
    }

    private void mergeSort(int[] a, int left, int right) {
        if (left < right) {
            int mid = left + (right - left) / 2;
            mergeSort(a, left, mid);
            mergeSort(a, mid + 1, right);
            merge(a, left, mid, right);
        }
    }

    private void merge(int[] a, int left, int mid, int right) {
        int n1 = mid - left + 1;
        int n2 = right - mid;

        int[] leftPart = new int[n1];
        int[] rightPart = new int[n2];
        for (int i = 0; i < n1; i++) {
            leftPart[i] = a[left + i];
        }
        for (int j = 0; j < n2; j++) {
            rightPart[j] = a[mid + 1 + j];
        }

        int i = 0, j = 0, k = left;
        while (i < n1 && j < n2) {
            if (leftPart[i] <= rightPart[j]) {
                a[k++] = leftPart[i++];
            } else {
                a[k++] = rightPart[j++];
            }
        }
        while (i < n1) {
            a[k++] = leftPart[i++];
        }
        while (j < n2) {
            a[k++] = rightPart[j++];
        }
    }

    @Override
    public String getName() {
        return "Merge Sort     (O(n log n))";
    }
}
