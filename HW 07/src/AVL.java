import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Your implementation of an AVL.
 *
 * @author Ruokun Niu
 * @version 1.0
 * @userid rniu8
 * @GTID 903487882
 *
 * Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 *
 * Resources: LIST ALL NON-COURSE RESOURCES YOU CONSULTED HERE
 */
public class AVL<T extends Comparable<? super T>> {

    /*
     * Do not add new instance variables or modify existing ones.
     */
    private AVLNode<T> root;
    private int size;

    /**
     * Constructs a new AVL.
     *
     * This constructor should initialize an empty AVL.
     *
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public AVL() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Constructs a new AVL.
     *
     * This constructor should initialize the AVL with the data in the
     * Collection. The data should be added in the same order it is in the
     * Collection.
     *
     * @param data the data to add to the tree
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public AVL(Collection<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("You can not add null data to "
                    + "a BST");
        }
        for (T element : data) {
            if (element == null) {
                throw new IllegalArgumentException("You can not add null "
                        + "data to a BST");
            }
            this.add(element);
        }
    }

    /**
     * Adds the element to the tree.
     *
     * Start by adding it as a leaf like in a regular BST and then rotate the
     * tree as necessary.
     *
     * If the data is already in the tree, then nothing should be done (the
     * duplicate shouldn't get added, and size should not be incremented).
     *
     * Remember to recalculate heights and balance factors while going back
     * up the tree after adding the element, making sure to rebalance if
     * necessary.
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("You cannot add null "
                    + "data to the AVL.");
        }
        root = this.addH(root, data);

    }

    /**
     * This is a helper method that adds a new node to the AVL recursively
     * @param curr The current node that is being traversed
     * @param data The data that will be added to the AVL
     * @return  The current node being traversed; maintains the shape of the AVL
     */
    private AVLNode addH(AVLNode<T> curr, T data) {
        if (curr == null) {
            size++;
            AVLNode<T> newNode = new AVLNode<T>(data);
            newNode.setRight(null);
            newNode.setLeft(null);
            newNode.setHeight(0);
            newNode.setBalanceFactor(0);
            return newNode;
        } else if (data.compareTo(curr.getData()) < 0) {
            curr.setLeft(addH(curr.getLeft(), data));
            this.updateH(curr);
            curr = balanceH(curr);
        } else if (data.compareTo(curr.getData()) > 0) {
            curr.setRight(addH(curr.getRight(), data));
            this.updateH(curr);
            curr = balanceH(curr);
        }
        return curr;
    }

    /**
     * Private method for retrieving the height of a node; avoid null pointer
     * @param curr The current node
     * @return the height of that current node
     */
    private int getHeightH(AVLNode<T> curr) {
        if (curr == null) {
            return -1;
        } else {
            return curr.getHeight();
        }
    }

    /**
     * A private helper method for updating the height and BF of a node
     * @param curr The current node that will have its height and BF updated
     */
    private void updateH(AVLNode<T> curr) {
        int newBF = getHeightH(curr.getLeft()) - getHeightH(curr.getRight());
        curr.setBalanceFactor(newBF);
        int newHeight = Math.max(getHeightH(curr.getLeft()),
                getHeightH(curr.getRight())) + 1;
        curr.setHeight(newHeight);
    }

    /**
     * A private helper method that checks whether a node in the AVL should be
     * rotated (also checks for double rotation)
     * @param curr The node that will be rotated if its |BF| > 1
     * @return the node after the rotation is performed
     */
    private AVLNode balanceH(AVLNode<T> curr) {
        if (curr.getBalanceFactor() <= -2) {
            int beta = 0;
            if (curr.getRight() == null) {
                beta = 0;
            } else {
                beta = curr.getRight().getBalanceFactor();
            }
            if (beta >= 1) {
                curr.setRight(rightRotate(curr.getRight()));
                return this.leftRotate(curr);
            } else {
                return this.leftRotate(curr);
            }
        } else if (curr.getBalanceFactor() >= 2) {
            int beta = 0;
            if (curr.getLeft() == null) {
                beta = 0;
            } else {
                beta = curr.getLeft().getBalanceFactor();
            }
            if (beta <= -1) {
                curr.setLeft(leftRotate(curr.getLeft()));
                return this.rightRotate(curr);
            } else {
                return this.rightRotate(curr);
            }
        }
        return curr;
    }

    /**
     * a helper method for performing a left rotation on the current node
     * @param curr The current node that will perform the left rotate
     * @return The new root node after the rotation is performed.
     */
    private AVLNode leftRotate(AVLNode<T> curr) {
        AVLNode<T> alpha = curr.getRight();
        curr.setRight(alpha.getLeft());
        alpha.setLeft(curr);
        this.updateH(curr);
        this.updateH(alpha);
        return alpha;
    }
    /**
     * a helper method for performing a right rotation on the current node
     * @param curr The current node that will perform the right rotate
     * @return The new root node after the rotation is performed.
     */
    private AVLNode rightRotate(AVLNode<T> curr) {
        AVLNode<T> alpha = curr.getLeft();
        curr.setLeft(alpha.getRight());
        alpha.setRight(curr);
        this.updateH(curr);
        this.updateH(alpha);
        return alpha;
    }
    /**
     * Removes and returns the element from the tree matching the given
     * parameter.
     *
     * There are 3 cases to consider:
     * 1: The node containing the data is a leaf (no children). In this case,
     *    simply remove it.
     * 2: The node containing the data has one child. In this case, simply
     *    replace it with its child.
     * 3: The node containing the data has 2 children. Use the predecessor to
     *    replace the data, NOT successor. As a reminder, rotations can occur
     *    after removing the predecessor node.
     *
     * Remember to recalculate heights and balance factors while going back
     * up the tree after removing the element, making sure to rebalance if
     * necessary.
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * @param data the data to remove
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if the data is null
     * @throws java.util.NoSuchElementException   if the data is not found
     */
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("You can not remove null "
                    + "data from the AVL");
        }
        AVLNode<T> ditto1 = new AVLNode<T>(null); //A dummy node
        root = this.removeH(root, data, ditto1);
        if (ditto1.getData() == null) {
            throw new NoSuchElementException("The data is not in the tree");
        }
        if (size == 0) {
            root = null;
        }
        return ditto1.getData();
    }

    /**
     * Private helper method that attempts to remove an item recursively from
     * the AVL
     * @param curr The current node that is being recursed
     * @param data The data that will be removed form the AVL
     * @param ditto1 a dummy node that will contain the removed data
     * @return The node that is resulted from recursion
     */
    private AVLNode removeH(AVLNode<T> curr, T data, AVLNode<T> ditto1) {
        if (curr == null) {
            ditto1.setData(null);
            return ditto1;
        }
        if (curr.getData().equals(data)) { //if it is found
            size--;
            ditto1.setData(curr.getData()); //give ditto1 its data
            if (curr.getLeft() == null && curr.getRight() == null) {
                //0 child case
                return null;
            } else if (curr.getLeft() == null) {
                return curr.getRight();
            } else if (curr.getRight() == null) {
                return curr.getLeft();
            } else if (curr.getLeft() != null && curr.getRight() != null) {
                AVLNode<T> ditto2 = new AVLNode<T>(null);
                curr.setLeft(removePre(curr.getLeft(), ditto2));
                curr.setData(ditto2.getData());
                curr.setBalanceFactor(ditto2.getBalanceFactor());
                curr.setHeight(ditto2.getHeight());
                this.updateH(curr);
            }
        } else if (curr.getData().compareTo(data) > 0) {
            curr.setLeft(removeH(curr.getLeft(), data, ditto1));
            this.updateH(curr);
            curr = balanceH(curr);
        } else if (curr.getData().compareTo(data) < 0) {
            curr.setRight(removeH(curr.getRight(), data, ditto1));
            this.updateH(curr);
            curr = balanceH(curr);
        }
        this.updateH(curr);
        return curr;
    }

    /**
     * A private method that updates the removed node with its predecessor
     * @param alpha the current node that is being recursed through
     * @param ditto2 a dummy node
     * @return the new node that will be replacing the removed node
     */
    private AVLNode removePre(AVLNode<T> alpha, AVLNode<T> ditto2) {
        if (alpha.getRight() == null) {
            ditto2.setData(alpha.getData());
            ditto2.setHeight(alpha.getHeight());
            ditto2.setBalanceFactor(alpha.getBalanceFactor());
            ditto2.setLeft(alpha.getLeft());
            return alpha.getLeft();
        } else {
            alpha.setRight(removePre(alpha.getRight(), ditto2));
        }
        this.updateH(alpha);
        return alpha;
    }
    /**
     * Returns the element from the tree matching the given parameter.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * @param data the data to search for in the tree
     * @return the data in the tree equal to the parameter
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T get(T data) {
        if (data == null) {
            throw new IllegalArgumentException("You cannot search for a "
                    + "null value in a BST");
        }
        if (root == null) {
            throw new NoSuchElementException("The data is not found "
                    + "in the tree");
        }
        AVLNode<T> ditto = new AVLNode<T>(null);
        root = getH(root, data, ditto);
        if (ditto.getData() == null) {
            throw new NoSuchElementException("The data is not found "
                    + "in the tree");
        }
        T result = ditto.getData();
        return result;
    }

    /**
     * private helper method that retrieves the data from the AVL
     * @param curr curr node in the AVL
     * @param data the data that one wants to retrieve
     * @param result1 the dummy node
     * @return returns the found data node to the public method
     */
    private AVLNode getH(AVLNode<T> curr, T data, AVLNode<T> result1) {
        if (curr == null) {
            return curr;
        }
        if (curr.getData().equals(data)) {
            result1.setData(curr.getData());
            return curr;
        } else if (curr.getData().compareTo(data) > 0) {
            curr.setLeft(getH(curr.getLeft(), data, result1));
        } else if (curr.getData().compareTo(data) < 0) {
            curr.setRight(getH(curr.getRight(), data, result1));
        }
        return curr;
    }
    /**
     * Returns whether or not data matching the given parameter is contained
     * within the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * @param data the data to search for in the tree
     * @return true if the parameter is contained within the tree, false
     * otherwise
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public boolean contains(T data) {
        if (data == null) {
            throw new IllegalArgumentException("You can not search for null "
                    + "data in an AVL");
        }
        if (root == null) {
            return false;
        }
        AVLNode<T> ditto1 = new AVLNode<T>(null);
        boolean result = false;
        root = containsH(root, data, ditto1);
        if (ditto1.getData() != null) {
            result = true;
        }
        return result;
    }

    /**
     * private helper method that checks whether the AVL contains a certain data
     * @param curr The current node that is being traversed
     * @param data the data that is checked for
     * @param ditto1 this node will containt the data if a match has been found
     * @return the current node that is being travserd; it will not be updated
     */
    private AVLNode containsH(AVLNode<T> curr, T data, AVLNode<T> ditto1) {
        if (curr == null) {
            return curr;
        }
        if (curr.getData().equals(data)) {
            ditto1.setData(curr.getData());
            return curr;
        } else if (curr.getData().compareTo(data) > 0) {
            curr.setLeft(containsH(curr.getLeft(), data, ditto1));
        } else if (curr.getData().compareTo(data) < 0) {
            curr.setRight(containsH(curr.getRight(), data, ditto1));
        }
        return curr;
    }
    /**
     * Returns the height of the root of the tree. Do NOT compute the height
     * recursively. This method should be O(1).
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        if (size == 0) {
            return -1;
        } else {
            return (root.getHeight());
        }
    }

    /**
     * Clears the tree.
     *
     * Clears all data and resets the size.
     */
    public void clear() {
        size = 0;
        root  = null;
    }

    /**
     * Find a path of letters in the tree that spell out a particular word,
     * if the path exists.
     *
     * Ex: Given the following AVL
     *
     *                   g
     *                 /   \
     *                e     i
     *               / \   / \
     *              b   f h   n
     *             / \         \
     *            a   c         u
     *
     * wordSearch([b, e, g, i, n]) returns the list [b, e, g, i, n],
     * where each letter in the returned list is from the tree and not from
     * the word array.
     *
     * wordSearch([h, i]) returns the list [h, i], where each letter in the
     * returned list is from the tree and not from the word array.
     *
     * wordSearch([a]) returns the list [a].
     *
     * wordSearch([]) returns an empty list [].
     *
     * wordSearch([h, u, g, e]) throws NoSuchElementException. Although all
     * 4 letters exist in the tree, there is no path that spells 'huge'.
     * The closest we can get is 'hige'.
     *
     * To do this, you must first find the deepest common ancestor of the
     * first and last letter in the word. Then traverse to the first letter
     * while adding letters on the path to the list while preserving the order
     * they appear in the word (consider adding to the front of the list).
     * Finally, traverse to the last letter while adding its ancestor letters to
     * the back of the list. Please note that there is no relationship between
     * the first and last letters, in that they may not belong to the same
     * branch. You will most likely have to split off to traverse the tree when
     * searching for the first and last letters.
     *
     * You may only use 1 list instance to complete this method. Think about
     * what type of list to use since you may have to add to the front and
     * back of the list.
     *
     * You will only need to traverse to the deepest common ancestor once.
     * From that node, go to the first and last letter of the word in one
     * traversal each. Failure to do so will result in a efficiency penalty.
     * Validating the path against the word array efficiently after traversing
     * the tree will NOT result in an efficiency penalty.
     *
     * If there exists a path between the first and last letters of the word,
     * there will only be 1 valid path.
     *
     * You may assume that the word will not contain duplicate letters.
     *
     * WARNING: Do not return letters from the passed-in word array!
     * If a path exists, the letters should be retrieved from the tree.
     * Returning any letter from the word array will result in a penalty!
     *
     * @param word array of T, where each element represents a letter in the
     * word (in order).
     * @return list containing the path of letters in the tree that spell out
     * the word, if such a path exists. Order matters! The ordering of the
     * letters in the returned list should match that of the word array.
     * @throws java.lang.IllegalArgumentException if the word array is null
     * @throws java.util.NoSuchElementException if the path is not in the tree
     */
    public List<T> wordSearch(T[] word) {
        if (word == null) {
            throw new IllegalArgumentException("You cannot perform a word "
                    + "search on a null array");
        }
        LinkedList<T> result = new LinkedList<T>();
        if (word.length == 0) {
            return result;
        }
        T firstElement = word[0];
        T lastElement = word[word.length - 1];
        boolean found = false;
        AVLNode<T> curr = root;
        T temp = firstElement;
        T temp2 = lastElement;
        if (firstElement.compareTo(lastElement) > 0) {
            firstElement = lastElement;
            lastElement = temp;
        }
        while (curr != null && !found) {
            if (curr.getData().compareTo(firstElement) >= 0
                    && curr.getData().compareTo(lastElement) <= 0) {
                found = true;
                break;
            } else if (curr.getData().compareTo(firstElement) < 0
                    && curr.getData().compareTo(lastElement) <= 0) {
                curr = curr.getRight();
            } else if (curr.getData().compareTo(firstElement) >= 0
                    && curr.getData().compareTo(lastElement) > 0) {
                curr = curr.getLeft();
            }
        }
        if (curr == null) {
            throw new NoSuchElementException("The path is not found in"
                    + " the tree");
        }
        AVLNode<T> x = null;
        T currData = curr.getData();
        firstElement = temp;
        lastElement = temp2;
        if (firstElement.compareTo(lastElement) > 0) {
            x = curr.getLeft();
        } else {
            x = curr.getRight();
        }
        this.searchfrontH(curr, result, firstElement);
        if (!(lastElement.equals(currData))) {
            this.searchbackH(x, result, lastElement);
        }
        int counter = 0;
        boolean same = false;
        if (result.size() == 0) {
            throw new NoSuchElementException("The path is not found in"
                    + " the tree");
        }
        for (T element : result) {
            if (element.equals(word[counter])) {
                same = true;
            } else {
                same = false;
                break;
            }
            counter++;
        }
        if (!same) {
            throw new NoSuchElementException("The path is not found in"
                    + " the tree");
        }
        return result;
    }

    /**
     * In this method, the first half of the word will be added to the
     * front of the result list
     * @param curr The current node being traversed
     * @param list The result list that contains the added characters
     * @param first the first character of the word
     */
    private void searchfrontH(AVLNode<T> curr, LinkedList<T> list, T first) {
        if (curr == null) {
            return;
        }
        if (curr.getData().equals(first)) {
            list.addFirst(curr.getData());
        } else if (curr.getData().compareTo(first) > 0) {
            list.addFirst(curr.getData());
            this.searchfrontH(curr.getLeft(), list, first);
        } else if (curr.getData().compareTo(first) < 0) {
            list.addFirst(curr.getData());
            this.searchfrontH(curr.getRight(), list, first);
        }
    }

    /**
     * In this method, the second half of the word will be added to the
     * back of the result list
     * @param curr The current node being traversed
     * @param list The result list that contains the added characters
     * @param last the last character of the word
     */
    private void searchbackH(AVLNode<T> curr, LinkedList<T> list, T last) {
        if (curr == null) {
            return;
        }
        if (curr.getData().equals(last)) {
            list.add(curr.getData());
        } else if (curr.getData().compareTo(last) > 0) {
            list.add(curr.getData());
            this.searchbackH(curr.getLeft(), list, last);
        } else if (curr.getData().compareTo(last) < 0) {
            list.add(curr.getData());
            this.searchbackH(curr.getRight(), list, last);
        }
    }

    /**
     * Returns the root of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the root of the tree
     */
    public AVLNode<T> getRoot() {
        // DO NOT MODIFY THIS METHOD!
        return root;
    }

    /**
     * Returns the size of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the tree
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}