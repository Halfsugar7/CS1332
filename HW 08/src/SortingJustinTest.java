import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import org.junit.Before;
import org.junit.Test;

import java.util.Comparator;
import java.util.Random;

/**
 * We made it through data structures!
 *
 * (I included tests on arr.length=0 because I think any sorting algorithm should be able to handle
 * that case; however, I'm not sure if we are required to handle length=0 or not.)
 *
 * @author Justin Hinckley
 * @version 1.0
 */
public class SortingJustinTest {
    private static final int TIMEOUT = 200;
    private Integer[] len0Unsorted;
    private Integer[] len0Sorted;
    private Integer[] len1Unsorted;
    private Integer[] len1Sorted;
    private Comparator<Integer> comparator = Comparator.comparingInt(a -> a);

    @Before
    public void setUp() {
        len0Unsorted = new Integer[0];
        len0Sorted = new Integer[0];
        len1Unsorted = new Integer[]{Integer.MAX_VALUE};
        len1Sorted = new Integer[]{Integer.MAX_VALUE};
    }





    @Test(timeout = TIMEOUT)
    public void testInsertLen01() {
        Sorting.insertionSort(len0Unsorted, comparator);
        assertArrayEquals(len0Sorted, len0Unsorted);

        Sorting.insertionSort(len1Unsorted, comparator);
        assertArrayEquals(len1Sorted, len1Unsorted);
    }

    // test stable for insertion
    @Test(timeout = TIMEOUT)
    public void testInsertStable() {
        Integer[] sorted = new Integer[5];
        Integer sixA = new Integer(6);
        Integer sixB = new Integer(6);
        sorted[0] = 8;
        sorted[1] = 1;
        sorted[2] = 2;
        sorted[3] = sixA;
        sorted[4] = sixB;
        Sorting.insertionSort(sorted, comparator);

        assertEquals(Integer.valueOf(1), sorted[0]);
        assertEquals(Integer.valueOf(2), sorted[1]);
        assertSame(sixA, sorted[2]);
        assertSame(sixB, sorted[3]);
        assertEquals(Integer.valueOf(8), sorted[4]);
    }




    @Test(timeout = TIMEOUT)
    public void testCocktailLen01() {
        Sorting.cocktailSort(len0Unsorted, comparator);
        assertArrayEquals(len0Sorted, len0Unsorted);

        Sorting.cocktailSort(len1Unsorted, comparator);
        assertArrayEquals(len1Sorted, len1Unsorted);
    }

    // test stable for cocktail shaker
    @Test(timeout = TIMEOUT)
    public void testCocktailStable() {
        Integer[] sorted = new Integer[2];
        Integer sixA = new Integer(6);
        Integer sixB = new Integer(6);
        sorted[0] = sixA;
        sorted[1] = sixB;
        Sorting.cocktailSort(sorted, comparator);

        assertSame(sixA, sorted[0]);
        assertSame(sixB, sorted[1]);

        sorted = new Integer[5];
        sixA = new Integer(6);
        sixB = new Integer(6);
        sorted[0] = 10;
        sorted[1] = sixA;
        sorted[2] = sixB;
        sorted[3] = 7;
        sorted[4] = 8;
        Sorting.cocktailSort(sorted, comparator);

        assertSame(sixA, sorted[0]);
        assertSame(sixB, sorted[1]);
        assertEquals(Integer.valueOf(7), sorted[2]);
        assertEquals(Integer.valueOf(8), sorted[3]);
        assertEquals(Integer.valueOf(10), sorted[4]);
    }



    @Test(timeout = TIMEOUT)
    public void testMergeLen01() {
        Sorting.mergeSort(len0Unsorted, comparator);
        assertArrayEquals(len0Sorted, len0Unsorted);

        Sorting.mergeSort(len1Unsorted, comparator);
        assertArrayEquals(len1Sorted, len1Unsorted);
    }

    // odd and even length arrays
    @Test(timeout = TIMEOUT)
    public void testMergeOdd() {
        Integer[] sorted = new Integer[7];
        sorted[0] = -1;
        sorted[1] = Integer.MAX_VALUE;
        sorted[2] = 0;
        sorted[3] = 21;
        sorted[4] = 7;
        sorted[5] = Integer.MIN_VALUE;
        sorted[6] = 5;
        Sorting.mergeSort(sorted, comparator);

        assertEquals(Integer.valueOf(Integer.MIN_VALUE), sorted[0]);
        assertEquals(Integer.valueOf(-1), sorted[1]);
        assertEquals(Integer.valueOf(0), sorted[2]);
        assertEquals(Integer.valueOf(5), sorted[3]);
        assertEquals(Integer.valueOf(7), sorted[4]);
        assertEquals(Integer.valueOf(21), sorted[5]);
        assertEquals(Integer.valueOf(Integer.MAX_VALUE), sorted[6]);
    }

    @Test(timeout = TIMEOUT)
    public void testMergeEven() {
        Integer[] sorted = new Integer[6];
        sorted[0] = -1;
        sorted[1] = Integer.MAX_VALUE;
        sorted[2] = 0;
        sorted[3] = 21;
        sorted[4] = 7;
        sorted[5] = Integer.MIN_VALUE;
        Sorting.mergeSort(sorted, comparator);

        assertEquals(Integer.valueOf(Integer.MIN_VALUE), sorted[0]);
        assertEquals(Integer.valueOf(-1), sorted[1]);
        assertEquals(Integer.valueOf(0), sorted[2]);
        assertEquals(Integer.valueOf(7), sorted[3]);
        assertEquals(Integer.valueOf(21), sorted[4]);
        assertEquals(Integer.valueOf(Integer.MAX_VALUE), sorted[5]);
    }

    // test stable for merge sort, make sure to look at splits and have 6A
    // as first in a left and 6B as lowest in right.
    @Test(timeout = TIMEOUT)
    public void testMergeStable() {
        Integer[] sorted = new Integer[5];
        Integer sixA = new Integer(6);
        Integer sixB = new Integer(6);
        sorted[0] = 10;
        sorted[1] = 12;
        sorted[2] = sixA;
        sorted[3] = sixB;
        sorted[4] = 8;
        Sorting.mergeSort(sorted, comparator);

        assertSame(sixA, sorted[0]);
        assertSame(sixB, sorted[1]);
        assertEquals(Integer.valueOf(8), sorted[2]);
        assertEquals(Integer.valueOf(10), sorted[3]);
        assertEquals(Integer.valueOf(12), sorted[4]);
    }


    @Test(timeout = TIMEOUT)
    public void testQuickLen01() {
        Sorting.quickSort(len0Unsorted, comparator, new Random(111));
        assertArrayEquals(len0Sorted, len0Unsorted);

        Sorting.quickSort(len1Unsorted, comparator, new Random(111));
        assertArrayEquals(len1Sorted, len1Unsorted);
    }




    @Test(timeout = TIMEOUT)
    public void testRadixLen01() {
        int[] len0rad = new int[0];
        Sorting.lsdRadixSort(len0rad);
        assertArrayEquals(new int[0], len0rad);

        int[] len1rad = new int[]{Integer.MAX_VALUE};
        Sorting.lsdRadixSort(len1rad);
        assertArrayEquals(new int[]{Integer.MAX_VALUE}, len1rad);
    }

    // Have a MIN_INT with a bunch of small 2 digit ints, pos and neg,
    // and make sure some are less than -48, then MIN_INT should be first in final arr
    @Test(timeout = TIMEOUT)
    public void testRadixOverflow() {
        int[] sorted = new int[6];
        sorted[0] = 44;
        sorted[1] = 99;
        sorted[2] = Integer.MIN_VALUE;
        sorted[3] = -63;
        sorted[4] = 8;
        sorted[5] = -47;
        Sorting.lsdRadixSort(sorted);

        assertEquals(Integer.MIN_VALUE, sorted[0]);
        assertEquals(-63, sorted[1]);
        assertEquals(-47, sorted[2]);
        assertEquals(8, sorted[3]);
        assertEquals(44, sorted[4]);
        assertEquals(99, sorted[5]);
    }

    // a test with all different lengths of ints, pos and neg, including 0, duplicates.
    @Test(timeout = TIMEOUT)
    public void testRadixDiverse() {
        int[] sorted = new int[8];
        sorted[0] = 14;
        sorted[1] = 1;
        sorted[2] = 0;
        sorted[3] = -71;
        sorted[4] = 345;
        sorted[5] = -1;
        sorted[6] = -9834;
        sorted[7] = -71;
        Sorting.lsdRadixSort(sorted);

        assertEquals(-9834, sorted[0]);
        assertEquals(-71, sorted[1]);
        assertEquals(-71, sorted[2]);
        assertEquals(-1, sorted[3]);
        assertEquals(0, sorted[4]);
        assertEquals(1, sorted[5]);
        assertEquals(14, sorted[6]);
        assertEquals(345, sorted[7]);
    }

    // test stable (can't do because input is int[] not objects)



    // ===Exception Tests===

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testInsertArrNull() {
        Sorting.insertionSort(null, comparator);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testInsertCompNull() {
        Sorting.insertionSort(new Integer[]{1, 2, 3}, null);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testCocktailArrNull() {
        Sorting.cocktailSort(null, comparator);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testCocktailCompNull() {
        Sorting.cocktailSort(new Integer[]{1, 2, 3}, null);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testMergeArrNull() {
        Sorting.mergeSort(null, comparator);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testMergeCompNull() {
        Sorting.mergeSort(new Integer[]{1, 2, 3}, null);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testLSDArrNull() {
        Sorting.lsdRadixSort(null);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testQuickArrNull() {
        Sorting.quickSort(null, comparator, new Random(111));
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testQuickCompNull() {
        Sorting.quickSort(new Integer[]{1, 2, 3}, null, new Random(111));
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testQuickRandNull() {
        Sorting.quickSort(new Integer[]{1, 2, 3}, comparator, null);
    }
}