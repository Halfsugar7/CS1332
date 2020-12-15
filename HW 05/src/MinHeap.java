import java.util.ArrayList;
import java.util.NoSuchElementException;

/**
 * Your implementation of a MinHeap.
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
public class MinHeap<T extends Comparable<? super T>> {

    /**
     * The initial capacity of the MinHeap when created with the default
     * constructor.
     *
     * DO NOT MODIFY THIS VARIABLE!
     */
    public static final int INITIAL_CAPACITY = 13;

    // Do not add new instance variables or modify existing ones.
    private T[] backingArray;
    private int size;

    /**
     * Constructs a new MinHeap.
     *
     * The backing array should have an initial capacity of INITIAL_CAPACITY.
     */
    public MinHeap() {
        size = 0;
        backingArray = (T[]) new Comparable[INITIAL_CAPACITY];
        backingArray[0] = null;
    }

    /**
     * Creates a properly ordered heap from a set of initial values.
     *
     * You must use the BuildHeap algorithm that was taught in lecture! Simply
     * adding the data one by one using the add method will not get any credit.
     * As a reminder, this is the algorithm that involves building the heap
     * from the bottom up by repeated use of downHeap operations.
     *
     * Before doing the algorithm, first copy over the data from the
     * ArrayList to the backingArray (leaving index 0 of the backingArray
     * empty). The data in the backingArray should be in the same order as it
     * appears in the passed in ArrayList before you start the BuildHeap
     * algorithm.
     *
     * The backingArray should have capacity 2n + 1 where n is the
     * number of data in the passed in ArrayList (not INITIAL_CAPACITY).
     * Index 0 should remain empty, indices 1 to n should contain the data in
     * proper order, and the rest of the indices should be empty.
     *
     * @param data a list of data to initialize the heap with
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public MinHeap(ArrayList<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("The arraylist is null. You "
                    + "cannot add this to the heap");
        }
        int length = data.size() * 2 + 1;
        backingArray = (T[]) new Comparable[length];
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i) == null) {
                throw new IllegalArgumentException("The data is null. You "
                        + "cannot add this to the heap");
            }
            backingArray[i + 1] = data.get(i);
            size++;
        }
        backingArray[0] = null;
        int current = size / 2;
        while (current >= 1) {
            this.downheapHC(current);
            current--;
        }
    }



    /**
     * Adds an item to the heap. If the backing array is full (except for
     * index 0) and you're trying to add a new item, then double its capacity.
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("You cannot add null data to "
                    + "the heap");
        }
        if (size == 0) {
            backingArray[size + 1] = data;
        } else if (size == backingArray.length - 1) {
            this.resize();
            backingArray[size + 1] = data;
            int current = size + 1;
            int par = current / 2;
            boolean added = false;
            while (current > 1 && !added) {
                par = current / 2;
                if (backingArray[current].compareTo(backingArray[par]) < 0) {
                    //if the current is less than the parent
                    T ditto = backingArray[par];
                    backingArray[par] = backingArray[current];
                    backingArray[current] = ditto;
                    current = current / 2;
                } else {
                    added = true;
                    break;
                }
            }
        } else {
            backingArray[size + 1] = data;
            int current = size + 1;
            int par = current / 2;
            boolean added = false;
            while (current > 1 && !added) {
                par = current / 2;
                if (backingArray[current].compareTo(backingArray[par]) < 0) {
                    //if the current is less than the parent
                    T ditto = backingArray[par];
                    backingArray[par] = backingArray[current];
                    backingArray[current] = ditto;
                    current = current / 2;
                } else {
                    added = true;
                    break;
                }
            }
        }
        size++;
    }

    /**
     * Resize method
     * resizes the array into a 2X capacity
     */
    private void resize() {
        int newCapacity = backingArray.length * 2;
        T[] newArray = (T[]) new Comparable[newCapacity];
        for (int i = 0; i < backingArray.length; i++) {
            newArray[i] = backingArray[i];
        }
        backingArray = newArray;
    }
    /**
     * Removes and returns the min item of the heap. As usual for array-backed
     * structures, be sure to null out spots as you remove. Do not decrease the
     * capacity of the backing array.
     *
     * @return the data that was removed
     * @throws java.util.NoSuchElementException if the heap is empty
     */
    public T remove() {
        if (size == 0) {
            throw new NoSuchElementException("You can not throw anything "
                    + "from a empty heap");
        }
        T result = backingArray[1];
        backingArray[1] = backingArray[size];
        backingArray[size] = null;
        this.downheapH(1);
        size--;
        return result;
    }

    /**
     * The private helper that assists the downheaping process in remove
     * uses recursion
     * @param i the current index that wil be compared with its child(ren)
     */
    private void downheapH(int i) {
        int childIndex = i * 2;
        if (childIndex > size - 1 && childIndex + 1 > size - 1) {
            //No child case (ending case)
            backingArray[i] = backingArray[i];
        } else if (childIndex <= size - 1 && childIndex + 1 <= size - 1) {
            //two child case
            int alpha = 0;
            if (backingArray[childIndex].compareTo(backingArray[childIndex + 1])
                    < 0) {
                alpha = childIndex;
            } else {
                alpha = childIndex + 1;
            }
            if (backingArray[i].compareTo(backingArray[alpha]) > 0) {
                T ditto = backingArray[alpha];
                backingArray[alpha] = backingArray[i];
                backingArray[i] = ditto;
                this.downheapH(alpha);
            } else {
                backingArray[i] = backingArray[i];
            }
        } else if (!(childIndex + 1 <= size - 1) && childIndex <= size - 1) {
            //one child case
            int alpha = childIndex;
            if (backingArray[i].compareTo(backingArray[alpha]) > 0) {
                T ditto = backingArray[alpha];
                backingArray[alpha] = backingArray[i];
                backingArray[i] = ditto;
                this.downheapH(alpha);
            } else {
                backingArray[i] = backingArray[i];
            }
        }
    }

    /**
     * The private helper that assists the downheaping process in remove
     * uses recursion
     * different from downheapH(), this one is used specifically for the
     * constructor.
     * @param i the current index that wil be compared with its child(ren)
     */
    private void downheapHC(int i) {
        int childIndex = i * 2;
        if (childIndex > size && childIndex + 1 > size) {
            //No child case (ending case)
            backingArray[i] = backingArray[i];
        } else if (childIndex <= size && childIndex + 1 <= size) {
            //two child case
            int alpha = 0;
            if (backingArray[childIndex].compareTo(backingArray[childIndex + 1])
                    < 0) {
                alpha = childIndex;
            } else {
                alpha = childIndex + 1;
            }
            if (backingArray[i].compareTo(backingArray[alpha]) > 0) {
                T ditto = backingArray[alpha];
                backingArray[alpha] = backingArray[i];
                backingArray[i] = ditto;
                this.downheapHC(alpha);
            } else {
                backingArray[i] = backingArray[i];
            }
        } else if (!(childIndex + 1 <= size) && childIndex <= size) {
            //one child case
            int alpha = childIndex;
            if (backingArray[i].compareTo(backingArray[alpha]) > 0) {
                T ditto = backingArray[alpha];
                backingArray[alpha] = backingArray[i];
                backingArray[i] = ditto;
                this.downheapHC(alpha);
            } else {
                backingArray[i] = backingArray[i];
            }
        }
    }
    /**
     * Returns the minimum element in the heap.
     *
     * @return the minimum element
     * @throws java.util.NoSuchElementException if the heap is empty
     */
    public T getMin() {
        if (size == 0) {
            throw new NoSuchElementException("You can not retrieve any data "
                    + "from an empty heap");
        }
        T result = backingArray[1];
        return result;
    }

    /**
     * Returns whether or not the heap is empty.
     *
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return (size == 0);
    }

    /**
     * Clears the heap.
     *
     * Resets the backing array to a new array of the initial capacity and
     * resets the size.
     */
    public void clear() {
        T[] beta = (T[]) new Comparable[INITIAL_CAPACITY];
        size = 0;
        backingArray = beta;
    }

    /**
     * Returns the backing array of the heap.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the backing array of the list
     */
    public T[] getBackingArray() {
        // DO NOT MODIFY THIS METHOD!
        return backingArray;
    }

    /**
     * Returns the size of the heap.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the list
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}
