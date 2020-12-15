import java.util.NoSuchElementException;
/**
 * Your implementation of an ArrayList.
 *
 * @author Ruokun Niu
 * @version 1.0
 * @userid rniu8
 * @GTID 903487882
 * <p>
 * Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 * <p>
 * Resources: https://stackoverflow.com/questions/17831896/creating-generic-array-in-java-via-unchecked-type-cast
 */

public class ArrayList<T> {

    /**
     * The initial capacity of the ArrayList.
     * <p>
     * DO NOT MODIFY THIS VARIABLE!
     */
    public static final int INITIAL_CAPACITY = 9;

    // Do not add new instance variables or modify existing ones.
    private T[] backingArray;
    private int size;

    /**
     * Constructs a new ArrayList.
     * <p>
     * Java does not allow for regular generic array creation, so you will have
     * to cast an Object[] to a T[] to get the generic typing.
     */
    public ArrayList() {
        size = 0;
        backingArray = (T[]) new Object[INITIAL_CAPACITY];
    }

    /**
     * Adds the element to the specified index.
     * <p>
     * Remember that this add may require elements to be shifted.
     * <p>
     * Must be amortized O(1) for index size and O(n) for all other cases.
     *
     * @param index the index at which to add the new element
     * @param data  the data to add at the specified index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index > size
     * @throws java.lang.IllegalArgumentException  if data is null
     */
    public void addAtIndex(int index, T data) {
        boolean found = false;
        int currentLength = size;
        if (data == null) {
            throw new IllegalArgumentException("Can not insert null data to the arraylist.");
        }
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Can not add data to an invalid index.");
        }
        if (size == backingArray.length) {
            this.resize();
        }
        T[] newArray = (T[]) new Object[backingArray.length];
        if (index == size) {
            backingArray[size] = data;
        } else {
            for (int i = 0; i < size; i++) { //should be i < size -1
                if (!found) {
                    if (index == i) {
                        newArray[i + 1] = backingArray[i];
                        newArray[i] = data;
                        found = true;
                    } else {
                        newArray[i] = backingArray[i];
                    }
                } else {
                    newArray[i + 1] = backingArray[i];
                }
            }
            backingArray = newArray;
        }
        size++;
    }
    /**
     * Resize method
     * resizes the array into a 2X capacity
     */
    private void resize() {
        int newCapacity = size * 2;
        T[] newArray = (T[]) new Object[newCapacity];
        for (int i = 0; i < backingArray.length; i++) {
            newArray[i] = backingArray[i];
        }
        backingArray = newArray;
    }

    /**
     * Adds the element to the front of the list.
     * <p>
     * Remember that this add may require elements to be shifted.
     * <p>
     * Must be O(n).
     *
     * @param data the data to add to the front of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToFront(T data) {
        int currentLength = size;
        if (data == null) {
            throw new IllegalArgumentException("Can not insert null data to the arraylist.");
        }
        if (size == backingArray.length) {
            this.resize();
        }
        for (int i = currentLength - 1; i >= 0; i--) {
            backingArray[i + 1] = backingArray[i];
        }
        backingArray[0] = data;
        size++;
    }

    /**
     * Adds the element to the back of the list.
     * <p>
     * Must be amortized O(1).
     *
     * @param data the data to add to the back of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToBack(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Can not insert null data to the arraylist.");
        }
        if (size == backingArray.length) {
            this.resize();
        }
        backingArray[size] = data;
        size++;
    }

    /**
     * Removes and returns the element at the specified index.
     * <p>
     * Remember that this remove may require elements to be shifted.
     * <p>
     * Must be O(1) for index size - 1 and O(n) for all other cases.
     *
     * @param index the index of the element to remove
     * @return the data formerly located at the specified index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T removeAtIndex(int index) {
        T result = backingArray[index];
        T[] newArray = (T[]) new Object[backingArray.length];
        boolean found = false;
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Can not remove data from an invalid index.");
        }
        if (size == index) { // size == index - 1
            backingArray[size] = null;
        } else {
            for (int i = 0; i < size; i++) { // i < size - 1
                if (!found) {
                    if (index == i) {
                        newArray[i] = backingArray[i + 1];
                        found = true;
                    } else {
                        newArray[i] = backingArray[i];
                    }
                } else {
                    newArray[i] = backingArray[i + 1];
                }
            }
            backingArray = newArray;
        }
        size--;
        return result;
    }

    /**
     * Removes and returns the first element of the list.
     * <p>
     * Remember that this remove may require elements to be shifted.
     * <p>
     * Must be O(n).
     *
     * @return the data formerly located at the front of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromFront() {
        if (backingArray[0] == null) {
            throw new NoSuchElementException("You can not remove anything if the list is empty");
        }
        T result = backingArray[0];
        for (int i = 0; i < size; i++) {
            backingArray[i] = backingArray[i + 1];
        }
        size--;
        return result;
    }

    /**
     * Removes and returns the last element of the list.
     * <p>
     * Must be O(1).
     *
     * @return the data formerly located at the back of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromBack() {
        if (backingArray[0] == null) {
            throw new NoSuchElementException("You can not remove anything if the list is empty");
        }
        T result = backingArray[size - 1];
        backingArray[size - 1] = null;
        size--;
        return result;
    }

    /**
     * Returns the element at the specified index.
     * <p>
     * Must be O(1).
     *
     * @param index the index of the element to get
     * @return the data stored at the index in the list
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Can not retrieve data from an invalid index.");
        }
        T result = backingArray[index];
        return result;
    }

    /**
     * Returns whether or not the list is empty.
     * <p>
     * Must be O(1).
     *
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return ((backingArray[0] == null) || (size == 0));
    }

    /**
     * Clears the list.
     * <p>
     * Resets the backing array to a new array of the initial capacity and
     * resets the size.
     * <p>
     * Must be O(1).
     */
    public void clear() {
        size = 0;
        backingArray = (T[]) new Object[INITIAL_CAPACITY];
    }

    /**
     * Returns the backing array of the list.
     * <p>
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
     * Returns the size of the list.
     * <p>
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the list
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }

    public static void main(String[] args) {
        ArrayList<String> alpha = new ArrayList<>();
        alpha.addToBack("Tommy");
        alpha.addToBack("David");
        alpha.addToBack("Sam");
        alpha.addToBack("Richard");
        alpha.removeAtIndex(2);
        System.out.println(alpha.get(0));
        System.out.println(alpha.get(1));
        System.out.println(alpha.get(2));
        System.out.println(alpha.size());
    }
}
