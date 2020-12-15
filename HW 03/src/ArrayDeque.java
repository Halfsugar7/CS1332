import java.util.NoSuchElementException;

/**
 * Your implementation of an ArrayDeque.
 *
 * @author Ruokun Niu
 * @version 1.0
 * @userid rniu8
 * @GTID 903487882
 *
 * Collaborators: N/A
 *
 * Resources: N/A
 */
public class ArrayDeque<T> {

    /**
     * The initial capacity of the ArrayDeque.
     *
     * DO NOT MODIFY THIS VARIABLE.
     */
    public static final int INITIAL_CAPACITY = 11;

    // Do not add new instance variables or modify existing ones.
    private T[] backingArray;
    private int front;
    private int size;

    /**
     * Constructs a new ArrayDeque.
     */
    public ArrayDeque() {
        backingArray = (T[]) new Object[11];
        size = 0;
        front = 0;
    }

    /**
     * Adds the element to the front of the deque.
     *
     * If sufficient space is not available in the backing array, resize it to
     * double the current capacity. When resizing, copy elements to the
     * beginning of the new array and reset front to 0. After the resize and add
     * operation, the new data should be at index 0 of the array.
     *
     * Must be amortized O(1).
     *
     * @param data the data to add to the front of the deque
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addFirst(T data) {
        if (data == null) {
            throw new IllegalArgumentException("You can not add null data"
                    + " to the queue");
        }
        if (size == backingArray.length) {
            this.resizeAddFront();
            front = 0;
            backingArray[front] = data;
        } else {
            if (front == 0) {
                int position = backingArray.length - 1;
                backingArray[position] = data;
                front = position;
            } else {
                backingArray[front - 1] = data;
                front = front - 1;
            }
        }
        size++;
    }

    /**
     * A helper method that resizes the array whenever the capacity is full
     * and a new element needs to be added.
     */
    private void resize() {
        int newCapacity = backingArray.length * 2;
        T[] newArray = (T[]) new Object[newCapacity];
        int current = 0;
        for (int i = front; i < (backingArray.length + front); i++) {
            int anum = mod(i, backingArray.length);
            newArray[current] = backingArray[anum];
            current++;
        }
        backingArray = newArray;
    }

    /**
     * This is a helper method that is specifically designed for resizing the
     * array in the AddFront method. It puts the new data at index 0
     */
    private void resizeAddFront() {
        int newCapacity = backingArray.length * 2;
        T[] newArray = (T[]) new Object[newCapacity];
        int current = 1;
        for (int i = front; i < (backingArray.length + front); i++) {
            int anum = mod(i, backingArray.length);
            newArray[current] = backingArray[anum];
            current++;
        }
        backingArray = newArray;
    }

    /**
     * Adds the element to the back of the deque.
     *
     * If sufficient space is not available in the backing array, resize it to
     * double the current capacity. When resizing, copy elements to the
     * beginning of the new array and reset front to 0.
     *
     * Must be amortized O(1).
     *
     * @param data the data to add to the back of the deque
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addLast(T data) {
        if (data == null) {
            throw new IllegalArgumentException("You can not add null data"
                    + " to the queue");
        }
        if (size == backingArray.length) {
            this.resize();
            front = 0;
            int current = (front + size) % backingArray.length;
            backingArray[current] = data;
        } else {
            int current = (front + size) % backingArray.length;
            backingArray[current] = data;
        }
        size++;
    }

    /**
     * Removes and returns the first element of the deque.
     *
     * Do not grow or shrink the backing array.
     *
     * If the deque becomes empty as a result of this call, do not reset
     * front to 0. Rather, modify the front index as if the size did not become
     * empty as a result of this call.
     *
     * Replace any spots that you remove from with null. Failure to do so can
     * result in loss of points.
     *
     * Must be O(1).
     *
     * @return the data formerly located at the front of the deque
     * @throws java.util.NoSuchElementException if the deque is empty
     */
    public T removeFirst() {
        if (size == 0) {
            throw new NoSuchElementException("You cannot remove anything "
                    + "from an empty deque.");
        }
        T result = backingArray[front];
        backingArray[front] = null;
        front = (front + 1) % backingArray.length;
        size--;
        return result;
    }

    /**
     * Removes and returns the last element of the deque.
     *
     * Do not grow or shrink the backing array.
     *
     * If the deque becomes empty as a result of this call, do not reset
     * front to 0. Rather, modify the front index as if the size did not become
     * empty as a result of this call.
     *
     * Replace any spots that you remove from with null. Failure to do so can
     * result in loss of points.
     *
     * Must be O(1).
     *
     * @return the data formerly located at the back of the deque
     * @throws java.util.NoSuchElementException if the deque is empty
     */
    public T removeLast() {
        if (size == 0) {
            throw new NoSuchElementException("You cannot remove anything "
                    + "from an empty deque.");
        }
        int back = 0;
        back = mod((front + size - 1), backingArray.length);
        T result = backingArray[back];
        backingArray[back] = null;
        size--;
        return result;
    }

    /**
     * Returns the first data of the deque without removing it.
     *
     * Must be O(1).
     *
     * @return the first data
     * @throws java.util.NoSuchElementException if the deque is empty
     */
    public T getFirst() {
        if (size == 0) {
            throw new NoSuchElementException("You cannot remove anything "
                    + "from an empty deque.");
        }
        T result = backingArray[front];
        return result;
    }

    /**
     * Returns the last data of the deque without removing it.
     *
     * Must be O(1).
     *
     * @return the last data
     * @throws java.util.NoSuchElementException if the deque is empty
     */
    public T getLast() {
        int back = 0;
        if (size == 0) {
            throw new NoSuchElementException("You cannot remove anything "
                    + "from an empty deque.");
        }
        back = mod((front + size - 1), backingArray.length);
        T result = backingArray[back];
        return result;
    }

    /**
     * Returns the backing array of the deque.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the backing array of the deque
     */
    public T[] getBackingArray() {
        // DO NOT MODIFY THIS METHOD!
        return backingArray;
    }

    /**
     * Returns the size of the deque.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the deque
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }

    /**
     * Returns the smallest non-negative remainder when dividing index by
     * modulo. So, for example, if modulo is 5, then this method will return
     * either 0, 1, 2, 3, or 4, depending on what the remainder is.
     *
     * This differs from using the % operator in that the % operator returns
     * the smallest answer with the same sign as the dividend. So, for example,
     * (-5) % 6 => -5, but with this method, mod(-5, 6) = 1.
     *
     * Examples:
     * mod(-3, 5) => 2
     * mod(11, 6) => 5
     * mod(-7, 7) => 0
     *
     * This helper method is here to make the math part of the circular
     * behavior easier to work with.
     *
     * @param index  the number to take the remainder of
     * @param modulo the divisor to divide by
     * @return the remainder in its smallest non-negative form
     * @throws java.lang.IllegalArgumentException if the modulo is non-positive
     */
    private static int mod(int index, int modulo) {
        // DO NOT MODIFY THIS METHOD!
        if (modulo <= 0) {
            throw new IllegalArgumentException("The modulo must be positive");
        }
        int newIndex = index % modulo;
        return newIndex >= 0 ? newIndex : newIndex + modulo;
    }

}
