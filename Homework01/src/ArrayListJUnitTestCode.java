import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

import java.util.NoSuchElementException;

/**
 * This is an additional set of JUnit tests to the TA provided tests.
 *
 * @author Vincent Davies
 * @version 1.0
 */
public class ArrayListJUnitTestCode {
    private static final int TIMEOUT = 200;
    private ArrayList<String> list;

    @Before
    public void setUp() {
        list = new ArrayList<>();
    }

    @Test(timeout = TIMEOUT, expected = IndexOutOfBoundsException.class)
    public void testBigIndex() {
        list.addAtIndex(0, "2a");   // 2a
        list.addAtIndex(0, "1a");   // 1a, 2a
        list.addAtIndex(2, "4a");   // 1a, 2a, 4a
        list.addAtIndex(2, "3a");   // 1a, 2a, 3a, 4a
        list.addAtIndex(400, "0a");   //Should throw IllegalArgumentException.
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testIllegalArg() {
        list.addAtIndex(0, "2a");   // 2a
        list.addAtIndex(0, "1a");   // 1a, 2a
        list.addAtIndex(2, "4a");   // 1a, 2a, 4a
        list.addAtIndex(2, null);   // 1a, 2a, 3a, 4a
    }

    @Test(timeout = TIMEOUT)
    public void testResize() {
        list.addAtIndex(0, "0");
        list.addAtIndex(1, "1");
        list.addAtIndex(2, "3");
        list.addToBack("4");
        list.addToBack("5");
        list.addToBack("6");
        list.addToBack("7");
        list.addToBack("8");
        list.addAtIndex(2, "2");
        assertEquals(9, list.size());

        //Now add another to force the resize to occur.

        list.addToFront("-1");
        assertEquals(10, list.size());

        String[] tArray = new String[18];
        tArray[0] = "-1";
        tArray[1] = "0";
        tArray[2] = "1";
        tArray[3] = "2";
        tArray[4] = "3";
        tArray[5] = "4";
        tArray[6] = "5";
        tArray[7] = "6";
        tArray[8] = "7";
        tArray[9] = "8";

        assertArrayEquals(tArray, list.getBackingArray());
    }

    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void testRemoveTooMuch() {
        list.addAtIndex(0, "0");
        list.addAtIndex(1, "1");
        list.addAtIndex(2, "3");
        list.addToBack("4");

        list.removeFromBack();
        list.removeFromBack();
        list.removeFromBack();
        list.removeFromBack();
        list.removeFromBack();
    }

    @Test(timeout = TIMEOUT, expected = IndexOutOfBoundsException.class)
    public void testRemoveBigIndex() {
        list.addAtIndex(0, "0");
        list.addAtIndex(1, "1");
        list.addAtIndex(2, "3");
        list.addToBack("4");

        list.removeAtIndex(6);
    }

    @Test(timeout = TIMEOUT)
    public void testVeryLargeAdd() {
        list.addToBack("0a");
        list.addToBack("1a");
        list.addToBack("2a");
        list.addToBack("3a");
        list.addToBack("4a");
        list.addToBack("5a");
        list.addToBack("6a");
        list.addToBack("7a");
        list.addToBack("8a");
        list.addToBack("9a");
        list.addToBack("10a");
        list.addToBack("11a");
        list.addToBack("12a");
        list.addToBack("13a");
        list.addToBack("14a");
        list.addToBack("15a");
        list.addToBack("16a");
        list.addToBack("17a");
        list.addToBack("18a");
        list.addToBack("19a");
        list.addToBack("20a");

        assertEquals(21, list.size());

        Object[] expected = new Object[36];
        expected[0] = "0a";
        expected[1] = "1a";
        expected[2] = "2a";
        expected[3] = "3a";
        expected[4] = "4a";
        expected[5] = "5a";
        expected[6] = "6a";
        expected[7] = "7a";
        expected[8] = "8a";
        expected[9] = "9a";
        expected[10] = "10a";
        expected[11] = "11a";
        expected[12] = "12a";
        expected[13] = "13a";
        expected[14] = "14a";
        expected[15] = "15a";
        expected[16] = "16a";
        expected[17] = "17a";
        expected[18] = "18a";
        expected[19] = "19a";
        expected[20] = "20a";

        assertArrayEquals(expected, list.getBackingArray());
    }

    @Test(timeout = TIMEOUT)
    public void testRemoveFromBack() {
        list.addToBack("0a");
        list.addToBack("1a");
        list.addToBack("2a");
        list.addToBack("3a");
        list.addToBack("4a");
        list.addToBack("5a");
        list.addToBack("6a");
        list.addToBack("7a");
        list.addToBack("8a");

        list.removeFromBack();
        list.removeFromBack();
        list.removeFromBack();
        list.removeFromBack();
        list.removeFromBack();
        list.removeFromBack();
        list.removeFromBack();
        list.removeFromBack();
        list.removeFromBack();

        assertTrue(list.isEmpty());
        assertArrayEquals(new String[9], list.getBackingArray());
    }
}