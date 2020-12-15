import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class KrishStudentTest {

    private static final int TIMEOUT = 200;

    private BST<String> strTree;
    private BST<Integer> intTree;

    @Before
    public void initTree() {
        this.strTree = new BST<>();
        this.intTree = new BST<>();
    }

    @Test(timeout = TIMEOUT)
    public void testInitialization() {
        assertEquals(null, strTree.getRoot());
        assertEquals(0, strTree.size());
    }

    @Test(timeout = TIMEOUT)
    public void testCollectionConstructor() {
        ArrayList<String> strList = new ArrayList<>();
        strList.add("fish");
        strList.add("beetle");
        strList.add("deer");
        strList.add("wildebeest");
        strList.add("gazelle");
        strList.add("antelope");
        /* expected tree
                     fish
                   /      \
                beetle    wildebeest
                /  \     /
         antelope  deer  gazelle
         */
        BST<String> newTree = new BST(strList);
        BSTNode<String> root = newTree.getRoot();

        assertEquals(6, newTree.size());
        assertEquals("fish", root.getData());
        assertEquals("beetle", root.getLeft().getData());
        assertEquals("antelope", root.getLeft().getLeft().getData());
        assertEquals("deer", root.getLeft().getRight().getData());
        assertEquals("wildebeest", root.getRight().getData());
        assertEquals("gazelle", root.getRight().getLeft().getData());
    }

    @Test(timeout = TIMEOUT)
    public void testConstructorNullData() {
        ArrayList<String> strList = new ArrayList<>();
        strList.add("hawaii");
        strList.add("massachusetts");
        strList.add(null);
        strList.add("minnesota");
        try {
            BST<String> newTree = new BST(strList);
            fail();
        } catch (IllegalArgumentException e) { }
    }

    @Test(timeout = TIMEOUT)
    public void testConstructorNullCollection() {
        try {
            BST<String> newTree = new BST(null);
            fail();
        } catch (IllegalArgumentException e) { }
    }

    @Test(timeout = TIMEOUT)
    public void testAddNull() {
        strTree.add("coffee");
        strTree.add("flavor");
        try {
            strTree.add(null);
            fail();
        } catch (IllegalArgumentException e) {
            // check to make sure the tree was not affected
            assertEquals(2, strTree.size());
            assertEquals("coffee", strTree.getRoot().getData());
            assertEquals("flavor",
                    strTree.getRoot().getRight().getData());
        }
    }

    @Test(timeout = TIMEOUT)
    public void testAdd() {
        strTree.add("elephant");
        assertEquals(1, strTree.size());
        assertEquals("elephant", strTree.getRoot().getData());
        assertNull(strTree.getRoot().getLeft());
        assertNull(strTree.getRoot().getRight());

        strTree.add("star");
        assertEquals(2, strTree.size());
        assertEquals("elephant", strTree.getRoot().getData());
        assertNull(strTree.getRoot().getLeft());
        assertEquals("star", strTree.getRoot().getRight().getData());

        strTree.add("dabble");
        assertEquals(3, strTree.size());
        assertEquals("elephant", strTree.getRoot().getData());
        assertEquals("dabble", strTree.getRoot().getLeft().getData());
        assertEquals("star", strTree.getRoot().getRight().getData());

        strTree.add("geometry");
        assertEquals(4, strTree.size());
        assertEquals("elephant", strTree.getRoot().getData());
        assertEquals("dabble", strTree.getRoot().getLeft().getData());
        assertEquals("star", strTree.getRoot().getRight().getData());
        assertEquals("geometry",
                strTree.getRoot().getRight().getLeft().getData());

        strTree.add("dengue");
        assertEquals(5, strTree.size());
        assertEquals("elephant", strTree.getRoot().getData());
        assertEquals("dabble", strTree.getRoot().getLeft().getData());
        assertEquals("dengue",
                strTree.getRoot().getLeft().getRight().getData());
        assertEquals("star", strTree.getRoot().getRight().getData());
        assertEquals("geometry",
                strTree.getRoot().getRight().getLeft().getData());

        strTree.add("ant");

        assertEquals(6, strTree.size());
        assertEquals("elephant", strTree.getRoot().getData());
        assertEquals("dabble", strTree.getRoot().getLeft().getData());
        assertEquals("ant",
                strTree.getRoot().getLeft().getLeft().getData());
        assertEquals("dengue",
                strTree.getRoot().getLeft().getRight().getData());
        assertEquals("star", strTree.getRoot().getRight().getData());
        assertEquals("geometry",
                strTree.getRoot().getRight().getLeft().getData());

        /** Expected tree:
         *                elephant
         *               /        \
         *           dabble       star
         *           /   \        /
         *         ant  dengue   geometry
         */

    }


    @Test(timeout = TIMEOUT)
    public void testAddDuplicate() {
        strTree.add("hi");
        strTree.add("there");
        strTree.add("bye");

        strTree.add("hi");
        strTree.add("there");
        strTree.add("bye");

        /**
         *      hi
         *     /  \
         *   bye there
         */

        assertEquals(3, strTree.size());
        assertEquals("hi", strTree.getRoot().getData());
        assertEquals("bye", strTree.getRoot().getLeft().getData());
        assertNull(strTree.getRoot().getLeft().getLeft());
        assertNull(strTree.getRoot().getLeft().getRight());
        assertEquals("there", strTree.getRoot().getRight().getData());
        assertNull(strTree.getRoot().getRight().getLeft());
        assertNull(strTree.getRoot().getRight().getRight());


    }

    @Test(timeout = TIMEOUT)
    public void testRemoveNull() {
        strTree.add("garcia");
        strTree.add("cherry");
        try {
            strTree.remove(null);
            fail();
        } catch (IllegalArgumentException e) {
            // check to make sure the tree was not affected
            assertEquals(2, strTree.size());
            assertEquals("garcia", strTree.getRoot().getData());
            assertEquals("cherry",
                    strTree.getRoot().getLeft().getData());
        }
    }

    @Test(timeout = TIMEOUT)
    public void testRemoveNotExist() {
        strTree.add("milky");
        strTree.add("way");
        try {
            strTree.remove("bar");
            fail();
        } catch (NoSuchElementException e) {
            // check to make sure the tree was not affected
            assertEquals(2, strTree.size());
            assertEquals("milky", strTree.getRoot().getData());
            assertEquals("way", strTree.getRoot().getRight().getData());
        }
    }


    @Test(timeout = TIMEOUT)
    public void testRemoveLeaf() {
        intTree.add(98);
        intTree.add(15);
        intTree.add(280);
        intTree.add(625);
        intTree.add(350);
        intTree.add(648);
        assertEquals(6, intTree.size());

        /* Tree before remove:
         *                98
         *              /    \
         *             15    280
         *                       \
         *                        625
         *                       /   \
         *                     350  648
         * */

        assertEquals((Integer) 350, intTree.remove(350));
        /*
         * Tree after remove:
         *                98
         *              /    \
         *             15    280
         *                       \
         *                        625
         *                          \
         *                          648
         * */

        assertEquals(5, intTree.size());
        BSTNode<Integer> root = intTree.getRoot();
        assertEquals((Integer) 98, root.getData());
        assertEquals((Integer) 15, root.getLeft().getData());
        assertEquals((Integer) 280, root.getRight().getData());
        assertEquals((Integer) 625, root.getRight().getRight().getData());
        assertEquals((Integer) 648,
                root.getRight().getRight().getRight().getData());

    }

    @Test(timeout = TIMEOUT)
    public void testRemoveOneChild() {

        intTree.add(98);
        intTree.add(15);
        intTree.add(280);
        intTree.add(625);
        intTree.add(350);
        intTree.add(648);
        assertEquals(6, intTree.size());

        /* Tree before remove:
         *                98
         *              /    \
         *             15    280
         *                       \
         *                        625
         *                       /   \
         *                     350  648
         * */
        assertEquals((Integer) 280, intTree.remove(280));

        /*
         * Tree after remove:
         *                98
         *              /    \
         *             15     625
         *                   /   \
         *                 350   648
         * */
        assertEquals(5, intTree.size());
        BSTNode<Integer> root = intTree.getRoot();
        assertEquals((Integer) 98, root.getData());
        assertEquals((Integer) 15, root.getLeft().getData());
        assertEquals((Integer) 625, root.getRight().getData());
        assertEquals((Integer) 350, root.getRight().getLeft().getData());
        assertEquals((Integer) 648, root.getRight().getRight().getData());
    }

    @Test(timeout = TIMEOUT)
    public void testRemoveTwoChild() {
        // per javadoc, replace removed node with the successor if 2 children
        intTree.add(98);
        intTree.add(15);
        intTree.add(280);
        intTree.add(175);
        intTree.add(625);
        intTree.add(213);
        intTree.add(350);
        intTree.add(648);
        assertEquals(8, intTree.size());

        /* Tree before remove:
        *                98
        *              /    \
        *             15    280
        *                  /    \
        *               175      625
        *                 \     /   \
        *                 213  350  648
        * */

        assertEquals((Integer) 98, intTree.remove(98));

        /* Tree after remove:
         *                175
         *              /    \
         *             15    280
         *                  /    \
         *               213      625
         *                       /   \
         *                      350  648
         * */

        assertEquals(7, intTree.size());
        BSTNode<Integer> root = intTree.getRoot();
        assertEquals((Integer) 175, root.getData());
        assertEquals((Integer) 15, root.getLeft().getData());
        assertEquals((Integer) 280, root.getRight().getData());
        assertEquals((Integer) 213, root.getRight().getLeft().getData());
        assertEquals((Integer) 625, root.getRight().getRight().getData());
        assertEquals((Integer) 350,
                root.getRight().getRight().getLeft().getData());
        assertEquals((Integer) 648,
                root.getRight().getRight().getRight().getData());
    }
    
    @Test(timeout = TIMEOUT)
    public void testGetRoot() {

        strTree.add("jackie");
        strTree.add("robinson");
        strTree.add("dodgers");

        assertEquals("jackie", strTree.get("jackie"));
        BSTNode<String> root = strTree.getRoot();
        assertEquals("jackie", root.getData());
        assertEquals("dodgers", root.getLeft().getData());
        assertEquals("robinson", root.getRight().getData());
    }

    /**
     * Creates a tree of strings with names of fruits
     */
    private void initFruitTree() {
        strTree.add("mango");
        strTree.add("strawberry");
        strTree.add("banana");
        strTree.add("peach");
        strTree.add("blueberry");
        strTree.add("apple");
        strTree.add("citrus");
        strTree.add("orange");
        strTree.add("watermelon");

        /**
         *             mango
         *           /       \
         *      banana     strawberry
         *    /     \         /      \
         * apple  blueberry  peach  watermelon
         *           \       /
         *          citrus  orange
         */
    }
    
    @Test(timeout = TIMEOUT)
    public void testGetRootAndLeftBranch() {
        this.initFruitTree();
        assertEquals("mango", strTree.get("mango"));
        assertEquals("banana", strTree.get("banana"));
        assertEquals("apple", strTree.get("apple"));
        assertEquals("blueberry", strTree.get("blueberry"));
        assertEquals("citrus", strTree.get("citrus"));
    }
    
    @Test(timeout = TIMEOUT)
    public void testGetRightBranch() {
        this.initFruitTree();
        assertEquals("strawberry", strTree.get("strawberry"));
        assertEquals("peach", strTree.get("peach"));
        assertEquals("watermelon", strTree.get("watermelon"));
        assertEquals("orange", strTree.get("orange"));
    }

    @Test(timeout = TIMEOUT)
    public void testGetNotExists() {
        this.initFruitTree();
        try {
            strTree.get("raspberry");
            fail();
        } catch (NoSuchElementException e) { }
    }

    @Test(timeout = TIMEOUT)
    public void testGetNull() {
        this.initFruitTree();
        try {
            strTree.get(null);
            fail();
        } catch (IllegalArgumentException e) { }
    }

    @Test(timeout = TIMEOUT)
    public void testContainsLeftAndRoot() {
        this.initFruitTree();
        assertTrue(strTree.contains("mango"));
        assertTrue(strTree.contains("banana"));
        assertTrue(strTree.contains("apple"));
        assertTrue(strTree.contains("blueberry"));
        assertTrue(strTree.contains("citrus"));

        assertFalse(strTree.contains("fruit"));
        assertFalse(strTree.contains("guava"));
        assertFalse(strTree.contains("kiwi"));
        assertFalse(strTree.contains("lychee"));

    }

    @Test(timeout = TIMEOUT)
    public void testContainsRight() {
        this.initFruitTree();
        assertTrue(strTree.contains("strawberry"));
        assertTrue(strTree.contains("peach"));
        assertTrue(strTree.contains("watermelon"));
        assertTrue(strTree.contains("orange"));

        assertFalse(strTree.contains("pomegranate"));
        assertFalse(strTree.contains("plums"));
        assertFalse(strTree.contains("tomato"));
        assertFalse(strTree.contains("nectarines"));
    }

    @Test(timeout = TIMEOUT)
    public void testContainsNull() {
        this.initFruitTree();
        try {
            strTree.contains(null);
            fail();
        } catch (IllegalArgumentException e) { }
    }

    /**         Fruit tree:
     *             mango
     *           /       \
     *      banana     strawberry
     *    /     \         /      \
     * apple  blueberry  peach  watermelon
     *           \       /
     *          citrus  orange
     */

    @Test(timeout = TIMEOUT)
    public void testPreorder() {
        this.initFruitTree();
        List<String> preOrderList = strTree.preorder();
        Iterator<String> preOrderIterator = preOrderList.iterator();
        assertEquals(preOrderIterator.next(), "mango");
        assertEquals(preOrderIterator.next(), "banana");
        assertEquals(preOrderIterator.next(), "apple");
        assertEquals(preOrderIterator.next(), "blueberry");
        assertEquals(preOrderIterator.next(), "citrus");
        assertEquals(preOrderIterator.next(), "strawberry");
        assertEquals(preOrderIterator.next(), "peach");
        assertEquals(preOrderIterator.next(), "orange");
        assertEquals(preOrderIterator.next(), "watermelon");
    }

    @Test(timeout = TIMEOUT)
    public void testInOrder() {
        this.initFruitTree();
        List<String> inOrderList = strTree.inorder();
        Iterator<String> inOrderIterator = inOrderList.iterator();
        assertEquals(inOrderIterator.next(), "apple");
        assertEquals(inOrderIterator.next(), "banana");
        assertEquals(inOrderIterator.next(), "blueberry");
        assertEquals(inOrderIterator.next(), "citrus");
        assertEquals(inOrderIterator.next(), "mango");
        assertEquals(inOrderIterator.next(), "orange");
        assertEquals(inOrderIterator.next(), "peach");
        assertEquals(inOrderIterator.next(), "strawberry");
        assertEquals(inOrderIterator.next(), "watermelon");
    }

    @Test(timeout = TIMEOUT)
    public void testPostOrder() {
        this.initFruitTree();
        List<String> postOrderList = strTree.postorder();
        Iterator<String> postOrderIterator = postOrderList.iterator();
        assertEquals(postOrderIterator.next(), "apple");
        assertEquals(postOrderIterator.next(), "citrus");
        assertEquals(postOrderIterator.next(), "blueberry");
        assertEquals(postOrderIterator.next(), "banana");
        assertEquals(postOrderIterator.next(), "orange");
        assertEquals(postOrderIterator.next(), "peach");
        assertEquals(postOrderIterator.next(), "watermelon");
        assertEquals(postOrderIterator.next(), "strawberry");
        assertEquals(postOrderIterator.next(), "mango");
    }

    @Test(timeout = TIMEOUT)
    public void testLevelOrder() {
        this.initFruitTree();
        List<String> levelOrderList = strTree.levelorder();
        Iterator<String> levelOrderIterator = levelOrderList.iterator();
        assertEquals(levelOrderIterator.next(), "mango");
        assertEquals(levelOrderIterator.next(), "banana");
        assertEquals(levelOrderIterator.next(), "strawberry");
        assertEquals(levelOrderIterator.next(), "apple");
        assertEquals(levelOrderIterator.next(), "blueberry");
        assertEquals(levelOrderIterator.next(), "peach");
        assertEquals(levelOrderIterator.next(), "watermelon");
        assertEquals(levelOrderIterator.next(), "citrus");
        assertEquals(levelOrderIterator.next(), "orange");
    }

    @Test(timeout = TIMEOUT)
    public void testHeight() {
        assertEquals(-1, strTree.height());

        strTree.add("hey");
        assertEquals(0, strTree.height());

        strTree.add("there");
        assertEquals(1, strTree.height());

        strTree.add("adam");
        assertEquals(1, strTree.height());

        strTree.add("what's");
        assertEquals(2, strTree.height());

        strTree.add("up");
        assertEquals(3, strTree.height());

        strTree.add("how");
        assertEquals(3, strTree.height());

        strTree.add("are");
        assertEquals(3, strTree.height());

        strTree.add("you");
        assertEquals(3, strTree.height());
        assertEquals(8, strTree.size());

        /**         FINAL TREE:
         *                  hey
         *                 /   \
         *             adam    there
         *               \     /    \
         *               are how    what's
         *                          /  \
         *                         up  you
         */
    }

    @Test(timeout = TIMEOUT)
    public void testClear() {
        this.initFruitTree();

        assertEquals(9, strTree.size());
        BSTNode<String> root = strTree.getRoot();
        assertEquals("mango", root.getData());
        assertEquals("banana", root.getLeft().getData());
        assertEquals("strawberry", root.getRight().getData());

        strTree.clear();
        assertEquals(0, strTree.size());
        assertNull(strTree.getRoot());
    }

    /**         Fruit tree:
     *             mango
     *           /       \
     *      banana     strawberry
     *    /     \         /      \
     * apple  blueberry  peach  watermelon
     *           \       /
     *          citrus  orange
     */

    @Test(timeout = TIMEOUT)
    public void testKLargest() {
        this.initFruitTree();

        List<String> largest0 = new ArrayList<>();
        assertEquals(largest0, strTree.kLargest(0));

        List<String> largest2 = new ArrayList<>();
        largest2.add("strawberry");
        largest2.add("watermelon");
        assertEquals(largest2, strTree.kLargest(2));

        List<String> largest5 = new ArrayList<>();
        largest5.add("mango");
        largest5.add("orange");
        largest5.add("peach");
        largest5.add("strawberry");
        largest5.add("watermelon");
        assertEquals(largest5, strTree.kLargest(5));

        List<String> largest9 = new ArrayList<>();
        largest9.add("apple");
        largest9.add("banana");
        largest9.add("blueberry");
        largest9.add("citrus");
        largest9.add("mango");
        largest9.add("orange");
        largest9.add("peach");
        largest9.add("strawberry");
        largest9.add("watermelon");
        assertEquals(largest9, strTree.kLargest(9));
    }

    @Test(timeout = TIMEOUT)
    public void testKLargestTooBig() {
        this.initFruitTree();
        try {
            strTree.kLargest(10);
            fail();
        } catch (IllegalArgumentException e) { }
    }


    @Test(timeout = TIMEOUT)
    public void testKLargestTooSmall() {
        this.initFruitTree();
        try {
            strTree.kLargest(-1);
            fail();
        } catch (IllegalArgumentException e) { }
    }

    @Test(timeout = TIMEOUT)
    public void testKLargestEmpty() {
        assertEquals(0, this.strTree.size());
        assertNull(this.strTree.getRoot());

        List<String> largest0 = new ArrayList<>();
        assertEquals(largest0, this.strTree.kLargest(0));

    }

}