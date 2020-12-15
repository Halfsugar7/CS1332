import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

/**
 * @author Charlie Jenkins cjenkins72@gatech.edu
 * @version 1.0
 */
public class SortingCharlieTest {
    private static final String dDuplicate = "".concat("D"); //Allocate different memory
    private static final String dDuplicate2 = "".concat("D"); //Allocate different memory

    private static final SortingStudentTest.TeachingAssistant[] arrayWithDuplicatesAnswer = {
        new SortingStudentTest.TeachingAssistant("A"),
        new SortingStudentTest.TeachingAssistant("B"),
        new SortingStudentTest.TeachingAssistant("C"),
        new SortingStudentTest.TeachingAssistant(dDuplicate),
        new SortingStudentTest.TeachingAssistant(dDuplicate2)
    };

    @Rule
    public Timeout globalTimeout = new Timeout(200, TimeUnit.MILLISECONDS);

    private SortingStudentTest.ComparatorPlus<SortingStudentTest.TeachingAssistant> comp;

    @Before
    public void setup() {
        comp = SortingStudentTest.TeachingAssistant.getNameComparator();
    }

    @Test(expected = IllegalArgumentException.class)
    public void nullArrayInsertionSort() {
        Sorting.insertionSort(null, comp);
    }

    @Test(expected = IllegalArgumentException.class)
    public void nullComparatorInsertionSort() {
        Sorting.insertionSort(new SortingStudentTest.TeachingAssistant[]{}, null);
    }

    @Test
    public void insertionSortDuplicates() {
        SortingStudentTest.TeachingAssistant[] curr = getArrayWithDuplicates();
        Sorting.insertionSort(curr, comp);
        assertTrue(comp.getCount() <= 7);
        checkDuplicatesSame(curr);
    }

    @Test
    public void insertionNoSwaps() {
        Sorting.insertionSort(getArrayWithNoSwaps(), comp);
        assertTrue(comp.getCount() <= 5);
        assertArrayEquals(arrayWithDuplicatesAnswer, getArrayWithNoSwaps());
    }

    @Test
    public void insertionOneElement() {
        SortingStudentTest.TeachingAssistant[] curr = new SortingStudentTest.TeachingAssistant[]{
            new SortingStudentTest.TeachingAssistant("A")};
        Sorting.insertionSort(curr, comp);
        assertEquals(0, comp.getCount());
    }

    @Test
    public void insertionNoElements() {
        SortingStudentTest.TeachingAssistant[] curr = new SortingStudentTest.TeachingAssistant[]{};
        Sorting.insertionSort(curr, comp);
        assertEquals(0, comp.getCount());
    }

    @Test(expected = IllegalArgumentException.class)
    public void nullArrayCocktailSort() {
        Sorting.cocktailSort(null, comp);
    }

    @Test(expected = IllegalArgumentException.class)
    public void nullComparatorCocktailSort() {
        Sorting.cocktailSort(new SortingStudentTest.TeachingAssistant[]{}, null);
    }

    @Test
    public void cocktailSortDuplicates() {
        SortingStudentTest.TeachingAssistant[] curr = getArrayWithDuplicates();
        Sorting.cocktailSort(curr, comp);
        assertTrue(comp.getCount() <= 20);
        checkDuplicatesSame(curr);
    }

    @Test
    public void cocktailSortWithoutDuplicates() {
        SortingStudentTest.TeachingAssistant[] curr = getArrayWithoutDuplicates();
        Sorting.cocktailSort(curr, comp);
        assertTrue(comp.getCount() <= 13);
        assertArrayEquals(getArrayWithoutDuplicatesAnswer(), curr);
    }

    @Test
    public void cocktailNoSwaps() {
        Sorting.cocktailSort(getArrayWithNoSwaps(), comp);
        assertTrue(comp.getCount() <= 5);
        assertArrayEquals(arrayWithDuplicatesAnswer, getArrayWithNoSwaps());
    }

    @Test
    public void cocktailOneElement() {
        SortingStudentTest.TeachingAssistant[] curr = new SortingStudentTest.TeachingAssistant[]{
            new SortingStudentTest.TeachingAssistant("A")};
        Sorting.cocktailSort(curr, comp);
        assertEquals(0, comp.getCount());
    }

    @Test
    public void cocktailNoElements() {
        SortingStudentTest.TeachingAssistant[] curr = new SortingStudentTest.TeachingAssistant[]{};
        Sorting.cocktailSort(curr, comp);
        assertEquals(0, comp.getCount());
    }

    @Test(expected = IllegalArgumentException.class)
    public void nullArrayMergeSort() {
        Sorting.mergeSort(null, comp);
    }

    @Test(expected = IllegalArgumentException.class)
    public void nullComparatorMergeSort() {
        Sorting.mergeSort(new SortingStudentTest.TeachingAssistant[]{}, null);
    }

    @Test
    public void mergeSortWithDuplicates() {
        SortingStudentTest.TeachingAssistant[] curr = getArrayWithDuplicates();
        Sorting.mergeSort(curr, comp);
        assertTrue(comp.getCount() <= 8);
        checkDuplicatesSame(curr);
    }

    @Test
    public void mergeSortWithoutDuplicates() {
        SortingStudentTest.TeachingAssistant[] curr = getArrayWithoutDuplicates();
        Sorting.mergeSort(curr, comp);
        assertTrue(comp.getCount() <= 15);
        assertArrayEquals(getArrayWithoutDuplicatesAnswer(), curr);
    }

    @Test
    public void mergeSortPreSorted() {
        SortingStudentTest.TeachingAssistant[] curr = getArrayWithoutDuplicatesAnswer();
        Sorting.mergeSort(curr, comp);
        assertTrue(comp.getCount() <= 13);
        assertArrayEquals(getArrayWithoutDuplicatesAnswer(), curr);
    }

    @Test
    public void lsdRadix() {
        int[] test = new int[]{123, 1244, 21, 231, 1};
        Sorting.lsdRadixSort(test);
        assertArrayEquals(new int[]{1, 21, 123, 231, 1244}, test);
    }

    @Test
    public void negativeLsdRadix() {
        int[] test = new int[]{-123, -1244, -21, -231, -1};
        Sorting.lsdRadixSort(test);
        assertArrayEquals(new int[]{-1244, -231, -123, -21, -1}, test);
    }

    @Test
    public void lsdRadixSortOnes() {
        int[] test = new int[]{123, 120, 125, 122, 120, 124, 126, 127, 129, 128};
        Sorting.lsdRadixSort(test);
        assertArrayEquals(new int[]{120, 120, 122, 123, 124, 125, 126, 127, 128, 129}, test);
    }

    @Test
    public void lsdRadixSortTens() {
        int[] test = new int[]{131, 141, 151, 111, 101, 161, 171, 191, 181, 181};
        Sorting.lsdRadixSort(test);
        assertArrayEquals(new int[]{101, 111, 131, 141, 151, 161, 171, 181, 181, 191}, test);
    }

    @Test
    public void lsdRadixSortEmpty() {
        int[] test = new int[]{};
        Sorting.lsdRadixSort(test);
        assertArrayEquals(test, new int[]{});
    }

    @Test(expected = IllegalArgumentException.class)
    public void nullArrayQuickSort() {
        Sorting.quickSort(null, comp, new Random());
    }

    @Test(expected = IllegalArgumentException.class)
    public void nullComparatorQuickSort() {
        Sorting.quickSort(new SortingStudentTest.TeachingAssistant[]{}, null, new Random());
    }

    @Test(expected = IllegalArgumentException.class)
    public void nullRandomQuickSort() {
        Sorting.quickSort(new SortingStudentTest.TeachingAssistant[]{}, comp, null);
    }

    @Test
    public void quicksort() {
        SortingStudentTest.TeachingAssistant[] curr =
            createArray("6", "8", "4", "2", "3", "7", "9", "6", "5");
        SortingStudentTest.TeachingAssistant[] solution =
            createArray("2", "3", "4", "5", "6", "6", "7", "8", "9");
        Sorting.quickSort(curr, comp, new Random(234));
        assertArrayEquals(solution, curr);
        assertEquals(19, comp.getCount());
    }

    @Test
    public void quicksortEmptyArray() {
        SortingStudentTest.TeachingAssistant[] curr =
            createArray();
        SortingStudentTest.TeachingAssistant[] solution =
            createArray();
        Sorting.quickSort(curr, comp, new Random(234));
        assertArrayEquals(solution, curr);
        assertEquals(0, comp.getCount());
    }

    private SortingStudentTest.TeachingAssistant[] getArrayWithoutDuplicates() {
        return createArray("1", "2", "6", "5", "3", "4", "7", "8", "9");
    }

    private SortingStudentTest.TeachingAssistant[] getArrayWithoutDuplicatesAnswer() {
        return createArray("1", "2", "3", "4", "5", "6", "7", "8", "9");
    }

    private void checkDuplicatesSame(SortingStudentTest.TeachingAssistant[] candidate) {
        assertArrayEquals(arrayWithDuplicatesAnswer, candidate);
        assertSame(candidate[3].getName(), dDuplicate);
        assertSame(candidate[4].getName(), dDuplicate2);
    }

    private SortingStudentTest.TeachingAssistant[] getArrayWithDuplicates() {
        return createArray(dDuplicate, "C", "B", "A", dDuplicate2);
    }

    private SortingStudentTest.TeachingAssistant[] getArrayWithNoSwaps() {
        return createArray("A", "B", "C", dDuplicate, dDuplicate2);
    }

    private SortingStudentTest.TeachingAssistant[] createArray(String... items) {
        SortingStudentTest.TeachingAssistant[] array =
            new SortingStudentTest.TeachingAssistant[items.length];
        for (int i = 0; i < items.length; i++) {
            array[i] = new SortingStudentTest.TeachingAssistant(items[i]);
        }
        return array;
    }
}
