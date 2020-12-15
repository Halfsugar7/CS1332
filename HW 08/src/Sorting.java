import java.util.Comparator;
import java.util.Random;
import java.util.LinkedList;

/**
 * Your implementation of various sorting algorithms.
 *
 * @author Ruokun Niu
 * @version 1.0
 * @userid rniu8
 * @GTID 903487882
 *
 * Collaborators: N/A
 *
 * Resources: https://stackoverflow.com/questions/20202889/how-can-i
 *              -create-an-array-of-linked-lists-in-java\
 */

public class Sorting {

    /**
     * Implement insertion sort.
     *
     * It should be:
     * in-place
     * stable
     * adaptive
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n)
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void insertionSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("Either the array or the "
                    + "comparator is null. The algorithm failed to execute");
        }
        for (int i = 1; i < arr.length; i++) {
            int j = i;  //j is the inner loop index
            while (j != 0 && (comparator.compare(arr[j], arr[j - 1]) < 0)) {
                T temp = arr[j];
                arr[j] = arr[j - 1];
                arr[j - 1] = temp;
                j--;
            }
        }
    }

    /**
     * Implement cocktail sort.
     *
     * It should be:
     * in-place
     * stable
     * adaptive
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n)
     *
     * NOTE: See pdf for last swapped optimization for cocktail sort. You
     * MUST implement cocktail sort with this optimization
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void cocktailSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("Either the array or the "
                    + "comparator is null. The algorithm failed to execute");
        }
        boolean swapsMade = true;
        int startIndex = 0;
        int endIndex = arr.length - 1;
        while (swapsMade) {
            swapsMade = false;
            int end = endIndex;
            for (int i = startIndex; i < end; i++) {
                if (comparator.compare(arr[i], arr[i + 1]) > 0) {
                    T temp = arr[i];
                    arr[i] = arr[i + 1];
                    arr[i + 1] = temp;
                    swapsMade = true;
                    endIndex = i;
                }
            }
            if (swapsMade) {
                int start = startIndex;
                swapsMade = false;
                for (int j = endIndex; j > start; j--) {
                    if (comparator.compare(arr[j - 1], arr[j]) > 0) {
                        T temp2 = arr[j - 1];
                        arr[j - 1] = arr[j];
                        arr[j] = temp2;
                        swapsMade = true;
                        startIndex = j;
                    }
                }
            }
        }
    }

    /**
     * Implement merge sort.
     *
     * It should be:
     * out-of-place
     * stable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(n log n)
     *
     * And a best case running time of:
     * O(n log n)
     *
     * You can create more arrays to run merge sort, but at the end, everything
     * should be merged back into the original T[] which was passed in.
     *
     * When splitting the array, if there is an odd number of elements, put the
     * extra data on the right side.
     *
     * Hint: If two data are equal when merging, think about which subarray
     * you should pull from first
     *
     * @param <T>        data type to sort
     * @param arr        the array to be sorted
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void mergeSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("Either the array or the "
                    + "comparator is null. The algorithm failed to execute");
        }
        if (arr.length == 1 || arr.length == 0) {
            return;
        }
        int length = arr.length;
        int midIndex = length / 2;
        T[] left = (T[]) new Object[midIndex];
        for (int i = 0; i < midIndex; i++) {
            left[i] = arr[i];
        }
        int c = 0;
        if (length % 2 == 0) {
            c = midIndex;
        } else {
            c = midIndex + 1;
        }
        T[] right = (T[]) new Object[c];
        for (int i = midIndex; i < length; i++) {
            right[i - midIndex] = arr[i];
        } //both left and right subarrays have been initialized
        mergeSort(left, comparator);
        mergeSort(right, comparator);
        int i = 0;
        int j = 0;
        while (i < left.length && j < right.length) {
            if (comparator.compare(left[i], right[j]) <= 0) {
                arr[i + j] = left[i];
                i++;
            } else {
                arr[i + j] = right[j];
                j++;
            }
        }
        while (i < left.length) {
            arr[i + j] = left[i];
            i++;
        }
        while (j < right.length) {
            arr[i + j] = right[j];
            j++;
        }
    }

    /**
     * Implement LSD (least significant digit) radix sort.
     *
     * Make sure you code the algorithm as you have been taught it in class.
     * There are several versions of this algorithm and you may not get full
     * credit if you do not implement the one we have taught you!
     *
     * Remember you CANNOT convert the ints to strings at any point in your
     * code! Doing so may result in a 0 for the implementation.
     *
     * It should be:
     * out-of-place
     * stable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(kn)
     *
     * And a best case running time of:
     * O(kn)
     *
     * You are allowed to make an initial O(n) passthrough of the array to
     * determine the number of iterations you need.
     *
     * At no point should you find yourself needing a way to exponentiate a
     * number; any such method would be non-O(1). Think about how how you can
     * get each power of BASE naturally and efficiently as the algorithm
     * progresses through each digit.
     *
     * Refer to the PDF for more information on LSD Radix Sort.
     *
     * You may use ArrayList or LinkedList if you wish, but it may only be
     * used inside radix sort and any radix sort helpers. Do NOT use these
     * classes with other sorts. However, be sure the List implementation you
     * choose allows for stability while being as efficient as possible.
     *
     * Do NOT use anything from the Math class except Math.abs().
     *
     * @param arr the array to be sorted
     * @throws java.lang.IllegalArgumentException if the array is null
     */
    public static void lsdRadixSort(int[] arr) {
        if (arr == null) {
            throw new IllegalArgumentException("The array"
                    + " is null. The algorithm failed to execute");
        }
        LinkedList<Integer>[] buckets = new LinkedList[19];
        for (int a = 0; a < buckets.length; a++) {
            buckets[a] = new LinkedList<Integer>();
        }
        //lets determine the longest number
        int largest = 0;
        boolean overflow = false;
        for (Integer num : arr) {
            if (num == Integer.MIN_VALUE || num == Integer.MAX_VALUE) {
                overflow = true;
            }
            num = Math.abs(num);
            if (num > largest) {
                largest = num;
            }
        }
        int digitCount = 0;
        while (largest != 0) {
            largest = largest / 10;
            digitCount++;
        }
        int currentDigit = 0;
        int index = 0;
        int temp = 0;
        int factor = 1;
        for (int j = 0; j < digitCount; j++) {
            for (int i = 0; i < arr.length; i++) {
                currentDigit = (arr[i] / (int) (Math.pow(10, j))) % 10;
                buckets[currentDigit + 9].add(arr[i]);
            }
            index = 0;
            for (LinkedList<Integer> bucket: buckets) {
                while (!(bucket.isEmpty())) {  //while bucket is not empty
                    temp = bucket.removeFirst();
                    arr[index] = temp;
                    index++;
                }
            }
        }
        if (overflow && arr.length > 1) {
            int temp2 = arr[0];
            arr[0] = arr[1];
            arr[1] = temp2;
        }
    }

    /**
     * Implement quick sort.
     *
     * Use the provided random object to select your pivots. For example if you
     * need a pivot between a (inclusive) and b (exclusive) where b > a, use
     * the following code:
     *
     * int pivotIndex = rand.nextInt(b - a) + a;
     *
     * If your recursion uses an inclusive b instead of an exclusive one,
     * the formula changes by adding 1 to the nextInt() call:
     *
     * int pivotIndex = rand.nextInt(b - a + 1) + a;
     *
     * It should be:
     * in-place
     * unstable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n log n)
     *
     * Make sure you code the algorithm as you have been taught it in class.
     * There are several versions of this algorithm and you may not receive
     * credit if you do not use the one we have taught you!
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @param rand       the Random object used to select pivots
     * @throws java.lang.IllegalArgumentException if the array or comparator or
     *                                            rand is null
     */
    public static <T> void quickSort(T[] arr, Comparator<T> comparator,
                                     Random rand) {
        if (arr == null || comparator == null || rand == null) {
            throw new IllegalArgumentException("Either the array, the"
                    + " comparator"
                    + " or the randomizer is null; "
                    + "the algorithm failed to execute");
        }
        int end = arr.length - 1;
        int start = 0;
        quickSortH(arr, comparator, rand, start, end);
    }

    /**
     * This is a helper method for inplace quicksort that sorts an array
     * recursively.
     * @param arr  The current array to be sorted
     * @param comparator An imported comparator for comparing two elements
     * @param rand an imported randomizer for calculating the pivot index
     * @param start  The start of the array
     * @param end The end of the array
     * @param <T> data type to be sort
     */
    private static <T> void quickSortH(T[] arr, Comparator<T> comparator,
                                Random rand, int start, int end) {
        if (end - start < 1) {
            return;
        }
        int pivotIndex = rand.nextInt(end - start + 1) + start;
        //random pivot index is determined
        T pivotVal = arr[pivotIndex];
        arr[pivotIndex] = arr[start];
        arr[start] = pivotVal;
        int i = start + 1;
        int j = end;
        while (i <= j) {
            while (i <= j && (comparator.compare(arr[i], pivotVal) <= 0)) {
                i++;
            }
            while (i <= j && (comparator.compare(arr[j], pivotVal) >= 0)) {
                j--;
            }
            if (i <= j) {
                T temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
                i++;
                j--;
            }
        }
        T alpha = arr[start];
        arr[start] = arr[j];
        arr[j] = alpha;
        quickSortH(arr, comparator, rand, start, j - 1);
        quickSortH(arr, comparator, rand, j + 1, end);
    }
}
