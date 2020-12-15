import java.util.NoSuchElementException;

/**
 * Your implementation of a non-circular DoublyLinkedList with a tail pointer.
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
public class DoublyLinkedList<T> {

    // Do not add new instance variables or modify existing ones.
    private DoublyLinkedListNode<T> head;
    private DoublyLinkedListNode<T> tail;
    private int size;

    // Do not add a constructor.

    /**
     * Adds the element to the specified index. Don't forget to consider whether
     * traversing the list from the head or tail is more efficient!
     *
     * Must be O(1) for indices 0 and size and O(n) for all other cases.
     *
     * @param index the index at which to add the new element
     * @param data  the data to add at the specified index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index > size
     * @throws java.lang.IllegalArgumentException  if data is null
     */
    public void addAtIndex(int index, T data) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("You can not add data to "
                    + "an invalid index");
        }
        if (data == null) {
            throw new IllegalArgumentException("You can not add null data "
                    + "to a linkedlist");
        }
        DoublyLinkedListNode<T> added = new DoublyLinkedListNode<T>(data);
        if (index == 0) { //adding at the head
            this.addToFront(data);
        } else if (index == size) {
            this.addToBack(data);
        } else {
            if ((size - index) < (index)) { //if the index is closer to the tail
                DoublyLinkedListNode<T> current = tail;
                int counter = size - 1;
                while (counter != index - 1) {
                    current = current.getPrevious();
                    counter--;
                }
                added.setPrevious(current);
                added.setNext(current.getNext());
                current.getNext().setPrevious(added);
                current.setNext(added);
                size++;
            } else {
                DoublyLinkedListNode<T> current = head;
                int counter = 0;
                while (counter != index - 1) {
                    current = current.getNext();
                    counter++;
                }
                added.setPrevious(current);
                added.setNext(current.getNext());
                current.getNext().setPrevious(added);
                current.setNext(added);
                size++;
            }
        }
    }

    /**
     * Adds the element to the front of the list.
     *
     * Must be O(1).
     *
     * @param data the data to add to the front of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToFront(T data) {
        if (data == null) {
            throw new IllegalArgumentException("You can not add null data "
                    + "to the "
                    + "linked list");
        }
        DoublyLinkedListNode<T> newNode = new DoublyLinkedListNode<T>(data);
        if (head == null) {
            head = newNode;
            tail = newNode;
        } else {
            newNode.setNext(head);
            head.setPrevious(newNode);
            head = newNode;
        }
        size++;
    }

    /**
     * Adds the element to the back of the list.
     *
     * Must be O(1).
     *
     * @param data the data to add to the back of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToBack(T data) {
        if (data == null) {
            throw new IllegalArgumentException("You can not add null data "
                    + " a linked list");
        }
        DoublyLinkedListNode<T> newNode = new DoublyLinkedListNode<T>(data);
        if (head == null && tail == null) {
            head = newNode;
            tail = newNode;
        } else {
            newNode.setPrevious(tail);
            tail.setNext(newNode);
            tail = newNode;
        }
        size++;
    }

    /**
     * Removes and returns the element at the specified index. Don't forget to
     * consider whether traversing the list from the head or tail is more
     * efficient!
     *
     * Must be O(1) for indices 0 and size - 1 and O(n) for all other cases.
     *
     * @param index the index of the element to remove
     * @return the data formerly located at the specified index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T removeAtIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("You cannot remove data from"
                    + " an invalid index");
        }
        if (head == null && tail == null) {
            throw new NoSuchElementException("You cannot remove anything"
                    + "from an empty linked list.");
        }
        T result = null;
        if (index == 0) {
            return this.removeFromFront();
        } else if (index == size - 1) {
            return this.removeFromBack();
        } else {
            if ((size - index) < (index)) { //If the index is closer to the tail
                DoublyLinkedListNode<T> current = tail;
                int counter = size - 1;
                while (counter != index) {
                    current = current.getPrevious();
                    counter--;
                }
                result = current.getData();
                current.getPrevious().setNext(current.getNext());
                current.getNext().setPrevious(current.getPrevious());
                size--;
            } else { //if the index is closer to the head
                DoublyLinkedListNode<T> current = head;
                int counter = 0;
                while (counter != index) {
                    current = current.getNext();
                    counter++;
                }
                result = current.getData();
                current.getPrevious().setNext(current.getNext());
                current.getNext().setPrevious(current.getPrevious());
                size--;
            }
        }
        return result;
    }

    /**
     * Removes and returns the first element of the list.
     *
     * Must be O(1).
     *
     * @return the data formerly located at the front of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromFront() {
        if (head == null || tail == null || size == 0) {
            throw new NoSuchElementException("You cannot remove anything"
                    + " from an empty list");
        }
        T result = head.getData();
        if (size == 1) {
            head = null;
            tail = null;
        } else if (size == 2) {
            head = tail;
        } else {
            head.getNext().getNext().setPrevious((head.getNext()));
            head = head.getNext();
            head.setPrevious(null);
        }
        size--;
        return result;
    }

    /**
     * Removes and returns the last element of the list.
     *
     * Must be O(1).
     *
     * @return the data formerly located at the back of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromBack() {
        if (head == null && tail == null) {
            throw new NoSuchElementException("You cannot remove anything "
                    + "from an "
                    + "empty list");
        }
        T result = tail.getData();
        if (size == 1) {
            head = null;
            tail = null;
        } else if (size == 2) {
            tail = head;
        } else {
            tail.getPrevious().getPrevious().setNext((tail.getPrevious()));
            tail = tail.getPrevious();
            tail.setNext(null);
        }
        size--;
        return result;
    }

    /**
     * Returns the element at the specified index. Don't forget to consider
     * whether traversing the list from the head or tail is more efficient!
     *
     * Must be O(1) for indices 0 and size - 1 and O(n) for all other cases.
     *
     * @param index the index of the element to get
     * @return the data stored at the index in the list
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("You cannot retrieve data"
                    + " from an invalid index");
        }
        T result = null;
        if (index == 0) {
            result = head.getData();
        } else if (index == size - 1) {
            result = tail.getData();
        } else {
            if ((size - index) < (index)) { //if the index is closer to the tail
                DoublyLinkedListNode<T> current = tail;
                int counter = size - 1;
                while (counter != index) {
                    current = current.getPrevious();
                    counter--;
                }
                result = current.getData();
            } else { //if it is closer to the tail
                DoublyLinkedListNode<T> current = head;
                int counter = 0;
                while (counter != index) {
                    current = current.getNext();
                    counter++;
                }
                result = current.getData();
            }
        }
        return result;
    }

    /**
     * Returns whether or not the list is empty.
     *
     * Must be O(1).
     *
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return (head == null || tail == null);
    }

    /**
     * Clears the list.
     *
     * Clears all data and resets the size.
     *
     * Must be O(1).
     */
    public void clear() {
        head = null;
        tail = null;
        size = 0;
    }

    /**
     * Removes and returns the last copy of the given data from the list.
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the list.
     *
     * Must be O(1) if data is in the tail and O(n) for all other cases.
     *
     * @param data the data to be removed from the list
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if data is not found
     */
    public T removeLastOccurrence(T data) {
        if (data == null) {
            throw new IllegalArgumentException("You cannot remove null data "
                    +  "from a linked list");
        }
        boolean found = false;
        if (tail.getData().equals(data)) {
            found = true;
            return this.removeFromBack();
        }
        int index = 0;
        DoublyLinkedListNode<T> current = head;
        int counter = 0;
        while (current != null) {
            if (current.getData().equals(data)) {
                index = counter;
                found = true;
            }
            current = current.getNext();
            counter++;
        }
        if (!found) {
            throw new NoSuchElementException("The data was not found in "
                    + "the linked list.");
        }
        return this.removeAtIndex(index);
    }

    /**
     * Returns an array representation of the linked list.
     *
     * Must be O(n) for all cases.
     *
     * @return an array of length size holding all of the objects in the
     * list in the same order
     */
    public Object[] toArray() {
        Object[] newArray = new Object[size];
        int counter = 0;
        DoublyLinkedListNode<T> current = head;
        while (current != null && counter < size) {
            newArray[counter] = current.getData();
            counter++;
            current = current.getNext();
        }
        return newArray;
    }

    /**
     * Returns the head node of the list.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the node at the head of the list
     */
    public DoublyLinkedListNode<T> getHead() {
        // DO NOT MODIFY!
        return head;
    }

    /**
     * Returns the tail node of the list.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the node at the tail of the list
     */
    public DoublyLinkedListNode<T> getTail() {
        // DO NOT MODIFY!
        return tail;
    }

    /**
     * Returns the size of the list.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the list
     */
    public int size() {
        // DO NOT MODIFY!
        return size;
    }
}
