import org.junit.Before;
import org.junit.Test;

import java.util.NoSuchElementException;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

/**
 * Here are some extra tests.
 *
 * @author Charlie Jenkins cjenkins72@gatech.edu
 * @version 1.0
 */
public class ArrayListCharlieTest {

    private static final int TIMEOUT = 200;
    private ArrayList<String> list;
    private String[] items = new String[]{"0a", "1a", "2a", "3a", "4a", "5a", "6a", "7a", "8a", "9a"};

    @Before
    public void setUp() {
        list = new ArrayList<>();
    }

    @Test(timeout = TIMEOUT, expected = IndexOutOfBoundsException.class)
    public void testAddAtIndexBelowZeroThenThrowsIndexOutOfBounds() {
        list.addAtIndex(-1, items[2]);
    }

    @Test(timeout = TIMEOUT, expected = IndexOutOfBoundsException.class)
    public void testAddAtIndexAboveSizeThenThrowsIndexOutOfBounds() {
        list.addAtIndex(0, items[1]);   // 1a
        list.addAtIndex(0, items[0]);   // 0a, 1a

        //Add at index 3 which is greater than size of 2
        list.addAtIndex(3, items[3]);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testAddNullAtIndexThenThrowsIllegalArgument() {
        list.addAtIndex(0, null);
    }

    @Test(timeout = TIMEOUT)
    public void testRemoveAllFromBack() {
        list.addToBack(items[0]);   // 0a
        list.addToBack(items[1]);   // 0a, 1a
        list.addToBack(items[2]);   // 0a, 1a, 2a
        list.addToBack(items[3]);   // 0a, 1a, 2a, 3a
        list.addToBack(items[4]);   // 0a, 1a, 2a, 3a, 4a
        list.addToBack(items[5]);   // 0a, 1a, 2a, 3a, 4a, 5a
        assertEquals(6, list.size());

        // assertSame checks for reference equality whereas assertEquals checks
        // value equality.
        for (int i = 5; i >= 0; i--) {
            assertSame(items[i], list.removeFromBack());
            assertEquals(i, list.size());
        }
    }

    @Test(timeout = TIMEOUT)
    public void addManyToFront() {
        list.addToFront(items[0]);   // 0a
        list.addToFront(items[1]);   // 1a, 0a
        list.addToFront(items[2]);   // 2a, 1a, 0a
        list.addToFront(items[3]);   // 3a, 2a, 1a, 0a
        list.addToFront(items[4]);   // 4a, 3a, 2a, 1a, 0a
        list.addToFront(items[5]);   // 5a, 4a, 3a, 2a, 1a, 0a

        Object[] expected = new Object[ArrayList.INITIAL_CAPACITY];
        expected[0] = items[5];
        expected[1] = items[4];
        expected[2] = items[3];
        expected[3] = items[2];
        expected[4] = items[1];
        expected[5] = items[0];

        assertArrayEquals(expected, list.getBackingArray());
    }

    @Test(timeout = TIMEOUT)
    public void addManyToMiddle() {
        list.addToBack(items[0]);   // 0a
        list.addToBack(items[5]);   // 0a, 5a
        list.addAtIndex(1, items[4]);   // 0a, 4a, 5a
        list.addAtIndex(1, items[3]);   // 0a, 3a, 4a, 5a
        list.addAtIndex(1, items[2]);   // 0a, 2a, 3a, 4a, 5a
        list.addAtIndex(1, items[1]);   // 0a, 1a, 2a, 3a, 4a, 5a

        Object[] expected = new Object[ArrayList.INITIAL_CAPACITY];
        expected[0] = items[0];
        expected[1] = items[1];
        expected[2] = items[2];
        expected[3] = items[3];
        expected[4] = items[4];
        expected[5] = items[5];

        assertArrayEquals(expected, list.getBackingArray());
    }

    @Test(timeout = TIMEOUT, expected = IndexOutOfBoundsException.class)
    public void testRemoveAtIndexBelowZeroThenThrowsIndexOutOfBounds() {
        list.removeAtIndex(-1);
    }

    @Test(timeout = TIMEOUT, expected = IndexOutOfBoundsException.class)
    public void testRemoveAtIndexAboveSizeThenThrowsIndexOutOfBounds() {
        list.addAtIndex(0, items[2]);   // 2a
        list.addAtIndex(0, items[1]);   // 1a, 2a

        //Remove at index 3 which is greater than size of 2
        list.removeAtIndex(3);
    }

    @Test(timeout = TIMEOUT)
    public void addUntilSizeIncrease() {
        list.addToBack(items[0]);
        list.addToBack(items[1]);
        list.addToBack(items[2]);
        list.addToBack(items[3]);
        list.addToBack(items[4]);
        list.addToBack(items[5]);
        list.addToBack(items[6]);
        list.addToBack(items[7]);
        list.addToBack(items[8]);
        assertEquals(9, list.size());
        assertEquals(ArrayList.INITIAL_CAPACITY, ((Object[]) list.getBackingArray()).length);
        list.addToBack(items[9]);
        assertEquals(10, list.size());
        assertEquals(ArrayList.INITIAL_CAPACITY * 2, ((Object[]) list.getBackingArray()).length);

        Object[] expected = new Object[ArrayList.INITIAL_CAPACITY * 2];
        expected[0] = items[0];
        expected[1] = items[1];
        expected[2] = items[2];
        expected[3] = items[3];
        expected[4] = items[4];
        expected[5] = items[5];
        expected[6] = items[6];
        expected[7] = items[7];
        expected[8] = items[8];
        expected[9] = items[9];

        assertArrayEquals(expected, list.getBackingArray());
    }

    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void removeFromFrontInvalid() {
        list.removeFromFront(); //Remove without anything in list
    }

    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void removeFromBackInvalid() {
        list.removeFromBack(); //Remove without anything in list
    }

    @Test(timeout = TIMEOUT, expected = IndexOutOfBoundsException.class)
    public void getEmpty() {
        list.get(0); //Get without anything in list
    }

    @Test(timeout = TIMEOUT, expected = IndexOutOfBoundsException.class)
    public void getNegative() {
        list.get(-1); //Get with negative index
    }
}

