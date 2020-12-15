import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.NoSuchElementException;

/**
 * Your implementation of a BST.
 *
 * @author Ruokun Niu
 * @version 1.0
 * @userid rniu8
 * @GTID 903487882
 *
 * Collaborators: N/A
 *
 * Resources: https://www.geeksforgeeks.org/write-a-c-program-to-find-
 *              the-maximum-depth-or-height-of-a-tree/
 */
public class BST<T extends Comparable<? super T>> {

    /*
     * Do not add new instance variables or modify existing ones.
     */
    private BSTNode<T> root;
    private int size;

    /**
     * Constructs a new BST.
     *
     * This constructor should initialize an empty BST.
     *
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public BST() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Constructs a new BST.
     *
     * This constructor should initialize the BST with the data in the
     * Collection. The data should be added in the same order it is in the
     * Collection.
     *
     * Hint: Not all Collections are indexable like Lists, so a regular for loop
     * will not work here. However, all Collections are Iterable, so what type
     * of loop would work?
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public BST(Collection<T> data) {
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
     * Adds the data to the tree.
     *
     * This must be done recursively.
     *
     * The data becomes a leaf in the tree.
     *
     * Traverse the tree to find the appropriate location. If the data is
     * already in the tree, then nothing should be done (the duplicate
     * shouldn't get added, and size should not be incremented).
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("You can not add null data to "
                    + "a BST");
        }
        root = this.addH(root,data);
    }
    /**
     * This is the private helper method for adding recursively
     * @param curr The current "root" node that will be compared
     * @param data The data that will be added correctly into the BST
     * @return returns the node that has the data added
     */
    private BSTNode addH(BSTNode<T> curr, T data) {
        if (curr == null) {
            size++;
            BSTNode<T> newNode = new BSTNode<T>(data);
            newNode.setRight(null);
            newNode.setLeft(null);
            return newNode;
        } else if (data.compareTo(curr.getData()) < 0) {
            curr.setLeft(addH(curr.getLeft(),data));
        } else if (data.compareTo(curr.getData()) > 0) {
            curr.setRight(addH(curr.getRight(),data));
        }
        return curr;
    }
    /**
     * Removes and returns the data from the tree matching the given parameter.
     *
     * This must be done recursively.
     *
     * There are 3 cases to consider:
     * 1: The node containing the data is a leaf (no children). In this case,
     * simply remove it.
     * 2: The node containing the data has one child. In this case, simply
     * replace it with its child.
     * 3: The node containing the data has 2 children. Use the successor to
     * replace the data. You MUST use recursion to find and remove the
     * successor (you will likely need an additional helper method to
     * handle this case efficiently).
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to remove
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("You cannot remove null data" +
                    " from the BST");
        }
        BSTNode<T> ditto1 = new BSTNode<T>(null); //A dummy node
        root = this.removeH(root,data,ditto1);
        if (ditto1.getData() == null) {
            throw new NoSuchElementException("The data is not in the tree");
        }
        if (size == 0) {
            root = null;
        }
        return ditto1.getData();
    }

    /**
     * A helper removal method that returns the Node that is removed
     * @param curr the current data; its data will be compared
     * @param data the data that will be removed (using recursion)
     * @param ditto1 The dummy Node that will be returned
     * @return returns the dummyNode that has the data removed
     */
    private BSTNode removeH(BSTNode curr, T data, BSTNode ditto1) {
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
                BSTNode<T> ditto2 = new BSTNode<T>(null);
                curr.setRight(removeSucc(curr.getRight(), ditto2));
                curr.setData(ditto2.getData());
            }
        } else if (curr.getData().compareTo(data) > 0) {
            curr.setLeft(removeH(curr.getLeft(),data,ditto1));
        } else if (curr.getData().compareTo(data) < 0) {
            curr.setRight(removeH(curr.getRight(),data,ditto1));
        }
        return curr;
    }

    /**
     * The prviate helper method that replaces the removed node with its
     * successor
     * @param alpha the current node that is greater than the removed Node
     * @param ditto2 the second dummy node, contains the successor node
     * @return returns ditto2, the second dummy node
     */
    private BSTNode removeSucc(BSTNode alpha, BSTNode ditto2) {
        if (alpha.getLeft() == null) {
            ditto2.setData(alpha.getData());
            return alpha.getRight();
        } else {
            alpha.setLeft(removeSucc(alpha.getLeft(),ditto2));
        }
        return alpha;
    }
    /**
     * Returns the data from the tree matching the given parameter.
     *
     * This must be done recursively.
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to search for
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
        BSTNode<T> ditto = new BSTNode<T>(null);
        root = getH(root, data, ditto);
        if (ditto.getData() == null) {
            throw new NoSuchElementException("The data is not found "
                    + "in the tree");
        }
        T result = ditto.getData();
        return result;
    }

    /**
     * private helper method that retrieves the data from the BST
     * @param curr curr node in the BST
     * @param data the data that one wants to retrieve
     * @param result1 the dummy node
     * @return returns the found data node to the public method
     */
    private BSTNode getH(BSTNode curr, T data,BSTNode result1) {
        if (curr == null) {
            return curr;
        }
        if (curr.getData().equals(data)) {
            result1.setData(curr.getData());
            return curr;
        } else if (curr.getData().compareTo(data) > 0) {
            curr.setLeft(getH(curr.getLeft(),data,result1));
        } else if (curr.getData().compareTo(data) < 0) {
            curr.setRight(getH(curr.getRight(),data,result1));
        }
        return curr;
    }
    /**
     * Returns whether or not data matching the given parameter is contained
     * within the tree.
     *
     * This must be done recursively.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to search for
     * @return true if the parameter is contained within the tree, false
     * otherwise
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public boolean contains(T data) {
        if (data == null) {
            throw new IllegalArgumentException("You cannot search for a "
                    + "null data");
        }
        if (root == null) {
            return false;
        }
        BSTNode<T> ditto1 = new BSTNode<T>(null);
        boolean result = false;
        root = containsH(root, data, ditto1);
        if (ditto1.getData() != null) {
            result = true;
        }
        return result;
    }
    private BSTNode containsH(BSTNode curr, T data, BSTNode ditto1) {
        if (curr == null) {
            return curr;
        }
        if (curr.getData().equals(data)) {
            ditto1.setData(curr.getData());
            return curr;
        } else if (curr.getData().compareTo(data) > 0) {
            curr.setLeft(containsH(curr.getLeft(),data,ditto1));
        } else if (curr.getData().compareTo(data) < 0) {
            curr.setRight(containsH(curr.getRight(),data,ditto1));
        }
        return curr;
    }
    /**
     * Generate a pre-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the preorder traversal of the tree
     */
    public List<T> preorder() {
        List<T> result = new LinkedList<T>();
        root = preorderH(root,result);
        return result;
    }

    /**
     * A private helper method for preorder traversal
     * @param curr the current node that is being traversed
     * @param result the list that contains the traversed result
     * @return return the current node
     */
    private BSTNode preorderH(BSTNode curr, List result) {
        if (curr != null) {
            result.add(curr.getData());
            curr.setLeft(preorderH(curr.getLeft(),result));
            curr.setRight(preorderH(curr.getRight(),result));
        }
        return curr;
    }

    /**
     * Generate an in-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the inorder traversal of the tree
     */
    public List<T> inorder() {
        List<T> result = new LinkedList<T>();
        root = inorderH(root,result);
        return result;
    }

    /**
     * A private helper method for Inorder traversal
     * @param curr the current node that is being traversed
     * @param result the list that contains the traversed result
     * @return return the current node
     */
    private BSTNode inorderH(BSTNode curr, List result) {
        if (curr != null) {
            curr.setLeft(inorderH(curr.getLeft(),result));
            result.add(curr.getData());
            curr.setRight(inorderH(curr.getRight(),result));
        }
        return curr;
    }

    /**
     * Generate a post-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the postorder traversal of the tree
     */
    public List<T> postorder() {
        List<T> result = new LinkedList<T>();
        root = postorderH(root,result);
        return result;
    }

    /**
     * A private helper method for Postorder traversal
     * @param curr the current node that is being traversed
     * @param result the list that contains the traversed result
     * @return return the current node
     */
    private BSTNode postorderH(BSTNode curr, List result) {
        if (curr != null) {
            curr.setLeft(postorderH(curr.getLeft(),result));
            curr.setRight(postorderH(curr.getRight(),result));
            result.add(curr.getData());
        }
        return curr;
    }
    /**
     * Generate a level-order traversal of the tree.
     *
     * This does not need to be done recursively.
     *
     * Hint: You will need to use a queue of nodes. Think about what initial
     * node you should add to the queue and what loop / loop conditions you
     * should use.
     *
     * Must be O(n).
     *
     * @return the level order traversal of the tree
     */
    public List<T> levelorder() {
        Queue<BSTNode<T>> alpha = new LinkedList<>();
        alpha.add(root);
        List<T> result = new LinkedList<T>();
        BSTNode<T> curr = null;
        while (!(alpha.isEmpty())) {
            curr = alpha.remove();
            if (curr != null) {
                alpha.add(curr.getLeft());
                alpha.add(curr.getRight());
                result.add(curr.getData());
            }
        }
        return result;
    }

    /**
     * Returns the height of the root of the tree.
     *
     * This must be done recursively.
     *
     * A node's height is defined as max(left.height, right.height) + 1. A
     * leaf node has a height of 0 and a null child has a height of -1.
     *
     * Must be O(n).
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        if (size == 0) {
            return -1;
        }
        int result = 0;
        result = heightH(root, result);
        result--;
        return result;
    }

    /**
     * A private helper method that determines the height of the BST
     * @param curr The current node that is being traversed
     * @param result the height of the previous node
     * @return return the height of the current node
     */
    private int heightH(BSTNode curr, int result) {
        if (curr == null) {
            return 0;
        } else {
            int leftHeight = heightH(curr.getLeft(),result);
            int rightHeight = heightH(curr.getRight(),result);
            if (leftHeight > rightHeight) {
                result = leftHeight + 1;
                return result;
            } else {
                result = rightHeight + 1;
                return result;
            }
        }
    }
    /**
     * Clears the tree.
     *
     * Clears all data and resets the size.
     *
     * Must be O(1).
     */
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * Finds and retrieves the k-largest elements from the BST in sorted order,
     * least to greatest.
     *
     * This must be done recursively.
     *
     * In most cases, this method will not need to traverse the entire tree to
     * function properly, so you should only traverse the branches of the tree
     * necessary to get the data and only do so once. Failure to do so will
     * result in an efficiency penalty.
     *
     * EXAMPLE: Given the BST below composed of Integers:
     *
     *                50
     *              /    \
     *            25      75
     *           /  \
     *          12   37
     *         /  \    \
     *        10  15    40
     *           /
     *          13
     *
     * kLargest(5) should return the list [25, 37, 40, 50, 75].
     * kLargest(3) should return the list [40, 50, 75].
     *
     * Should have a running time of O(log(n) + k) for a balanced tree and a
     * worst case of O(n + k).
     *
     * @param k the number of largest elements to return
     * @return sorted list consisting of the k largest elements
     * @throws java.lang.IllegalArgumentException if k > n, the number of data
     *                                            in the BST
     */
    public List<T> kLargest(int k) {
        if (k > size || k < 0) {
            throw new IllegalArgumentException("The value of k is invalid "
                    + "because it is greater than the size of the BST");
        }
        LinkedList<T> result = new LinkedList<>();
        int alpha = 0;
        root = kLargestH(root, alpha, result, k);
        return (List) result;
    }


    private BSTNode kLargestH(BSTNode curr, int alpha, LinkedList result,
                              int k) {
        if (result.size() >= k) {
            return curr;
        } else {
            if (curr != null) {
                curr.setRight(kLargestH(curr.getRight(), alpha, result, k));
                if (result.size() < k){
                    result.addFirst(curr.getData());
                }
                curr.setLeft(kLargestH(curr.getLeft(), alpha, result, k));
            }
        }
        return curr;
    }

    /**
     * Returns the root of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the root of the tree
     */
    public BSTNode<T> getRoot() {
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
