import java.util.Arrays;

public class MergeSortDemo {

    static int[] mergeSort(int[] arr) {
        if (arr.length <= 1)
            return arr;

        int mid = arr.length / 2;

        int[] left = mergeSort(Arrays.copyOfRange(arr, 0, mid));
        int[] right = mergeSort(Arrays.copyOfRange(arr, mid, arr.length));

        return merge(left, right);
    }

    /** Merge two sorted arrays into one. Uses O(n) auxiliary space. */
    static int[] merge(int[] left, int[] right) {

        int[] out = new int[left.length + right.length];

        int i = 0, j = 0, k = 0;

        // TODO 1 completed
        while (i < left.length && j < right.length) {
            if (left[i] <= right[j]) {
                out[k++] = left[i++];
            } else {
                out[k++] = right[j++];
            }
        }

        // TODO 2 completed
        while (i < left.length) {
            out[k++] = left[i++];
        }

        // TODO 3 completed
        while (j < right.length) {
            out[k++] = right[j++];
        }

        return out;
    }

    public static void main(String[] args) {

        int[] arr = {7, 3, 11, 5, 14, 2, 9, 6, 12, 1, 8, 4};

        System.out.println("Original Array:");
        System.out.println(Arrays.toString(arr));

        int[] sorted = mergeSort(arr);

        System.out.println("\nSorted Array:");
        System.out.println(Arrays.toString(sorted));
    }
}