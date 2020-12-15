import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
/**
 * This is a few extra edge cases for ArrayList Homework. I assumed the tests in ArrayListStudentTest
 * were already passed.
 *
 * @author Mija Kennedy
 * @version 1.0
 */
public class ArrayListKTest {

    private static final int TIMEOUT = 200;
    private ArrayList<String> list;

    @Before
    public void setUp() {
        list = new ArrayList<>();
    }

    @Test(timeout = TIMEOUT)
    public void testResizeAtIndex() {
        list.addAtIndex(0, "2a");   // 2a
        list.addAtIndex(0, "1a");   // 1a, 2a
        list.addAtIndex(2, "4a");   // 1a, 2a, 4a
        list.addAtIndex(2, "3a");   // 1a, 2a, 3a, 4a
        list.addAtIndex(0, "0a");   // 0a, 1a, 2a, 3a, 4a
        list.addAtIndex(2, "4a");   // 0a, 1a, 4a, 2a, 3a, 4a
        list.addAtIndex(2, "3a");   // 0a, 1a, 3a, 4a, 2a, 3a, 4a
        list.addAtIndex(2, "4a");   // 0a, 1a, 4a, 3a, 4a, 2a, 3a, 4a
        list.addAtIndex(2, "3a");   // 0a, 1a, 3a, 4a, 3a, 4a, 2a, 3a, 4a
        list.addAtIndex(2, "2a");   // 0a, 1a, 2a, 3a, 4a, 3a, 4a, 2a, 3a, 4a
        list.addAtIndex(2, "2a");   // 0a, 1a, 2a, 2a, 3a, 4a, 3a, 4a, 2a, 3a, 4a

        assertEquals( 11, list.size());
        Object[] expected = new Object[18];
        expected[0] = "0a";
        expected[1] = "1a";
        expected[2] = "2a";
        expected[3] = "2a";
        expected[4] = "3a";
        expected[5] = "4a";
        expected[6] = "3a";
        expected[7] = "4a";
        expected[8] = "2a";
        expected[9] = "3a";
        expected[10] = "4a";
        assertArrayEquals(expected, list.getBackingArray());
    }

    @Test(timeout = TIMEOUT)
    public void testResizeFront() {
        list.addToFront("4a");
        list.addToFront("3a");
        list.addToFront("2a");
        list.addToFront("4a");
        list.addToFront("3a");
        list.addToFront("4a");
        list.addToFront("3a");
        list.addToFront("2a");
        list.addToFront("2a");
        list.addToFront("1a");
        list.addToFront("0a");
        assertEquals( 11, list.size());

        Object[] expected = new Object[18];
        expected[0] = "0a";
        expected[1] = "1a";
        expected[2] = "2a";
        expected[3] = "2a";
        expected[4] = "3a";
        expected[5] = "4a";
        expected[6] = "3a";
        expected[7] = "4a";
        expected[8] = "2a";
        expected[9] = "3a";
        expected[10] = "4a";
        assertArrayEquals(expected, list.getBackingArray());
    }

    @Test(timeout = TIMEOUT)
    public void testResizeBack() {
        list.addToBack("0a");
        list.addToBack("1a");
        list.addToBack("2a");
        list.addToBack("2a");
        list.addToBack("3a");
        list.addToBack("4a");
        list.addToBack("3a");
        list.addToBack("4a");
        list.addToBack("2a");
        list.addToBack("3a");
        list.addToBack("4a");

        assertEquals( 11, list.size());

        Object[] expected = new Object[18];
        expected[0] = "0a";
        expected[1] = "1a";
        expected[2] = "2a";
        expected[3] = "2a";
        expected[4] = "3a";
        expected[5] = "4a";
        expected[6] = "3a";
        expected[7] = "4a";
        expected[8] = "2a";
        expected[9] = "3a";
        expected[10] = "4a";
        assertArrayEquals(expected, list.getBackingArray());
    }

    @Test(timeout = TIMEOUT, expected = IndexOutOfBoundsException.class)
    public void testNegInput() {
        list.addToFront("0a");
        list.addToFront("1a");
        list.addToFront("2a");
        list.addToFront("3a");
        list.addToFront("4a");

        assertEquals(5, list.size());

        list.addAtIndex( -2, "0a");
    }

    @Test(timeout = TIMEOUT, expected = IndexOutOfBoundsException.class)
    public void testBigInput() {
        list.addToFront("0a");
        list.addToFront("1a");
        list.addToFront("2a");
        list.addToFront("3a");
        list.addToFront("4a");

        assertEquals(5, list.size());

        list.addAtIndex( 18, "0a");
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testInvFrontInput() {
        list.addToFront("0a");
        list.addToFront("1a");
        list.addToFront("2a");
        list.addToFront("3a");
        list.addToFront("4a");

        assertEquals(5, list.size());

        list.addToFront(null);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testInvBackInput() {
        list.addToFront("0a");
        list.addToFront("1a");
        list.addToFront("2a");
        list.addToFront("3a");
        list.addToFront("4a");

        assertEquals(5, list.size());

        list.addToBack(null);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testInvIndInput() {
        list.addToFront("0a");
        list.addToFront("1a");
        list.addToFront("2a");
        list.addToFront("3a");
        list.addToFront("4a");

        assertEquals(5, list.size());

        list.addAtIndex(2, null);
    }
}
