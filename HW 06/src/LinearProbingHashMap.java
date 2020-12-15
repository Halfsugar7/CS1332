import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.util.NoSuchElementException;

/**
 * Your implementation of a LinearProbingHashMap.
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
public class LinearProbingHashMap<K, V> {

    /**
     * The initial capacity of the LinearProbingHashMap when created with the
     * default constructor.
     *
     * DO NOT MODIFY THIS VARIABLE!
     */
    public static final int INITIAL_CAPACITY = 13;

    /**
     * The max load factor of the LinearProbingHashMap
     *
     * DO NOT MODIFY THIS VARIABLE!
     */
    public static final double MAX_LOAD_FACTOR = 0.67;

    // Do not add new instance variables or modify existing ones.
    private LinearProbingMapEntry<K, V>[] table;
    private int size;

    /**
     * Constructs a new LinearProbingHashMap.
     *
     * The backing array should have an initial capacity of INITIAL_CAPACITY.
     *
     * Use constructor chaining.
     */
    public LinearProbingHashMap() {
        this(INITIAL_CAPACITY);
    }

    /**
     * Constructs a new LinearProbingHashMap.
     *
     * The backing array should have an initial capacity of initialCapacity.
     *
     * You may assume initialCapacity will always be positive.
     *
     * @param initialCapacity the initial capacity of the backing array
     */
    public LinearProbingHashMap(int initialCapacity) {
        table = (LinearProbingMapEntry<K, V>[]) new
                LinearProbingMapEntry[initialCapacity];
    }

    /**
     * Adds the given key-value pair to the map. If an entry in the map
     * already has this key, replace the entry's value with the new one
     * passed in.
     *
     * In the case of a collision, use linear probing as your resolution
     * strategy.
     *
     * Before actually adding any data to the HashMap, you should check to
     * see if the array would violate the max load factor if the data was
     * added. For example, let's say the array is of length 5 and the current
     * size is 3 (LF = 0.6). For this example, assume that no elements are
     * removed in between steps. If another entry is attempted to be added,
     * before doing anything else, you should check whether (3 + 1) / 5 = 0.8
     * is larger than the max LF. It is, so you would trigger a resize before
     * you even attempt to add the data or figure out if it's a duplicate. Be
     * careful to consider the differences between integer and double
     * division when calculating load factor.
     *
     * When regrowing, resize the length of the backing table to
     * 2 * old length + 1. You must use the resizeBackingTable method to do so.
     *
     * Return null if the key was not already in the map. If it was in the map,
     * return the old value associated with it.
     *
     * @param key   the key to add
     * @param value the value to add
     * @return null if the key was not already in the map. If it was in the
     * map, return the old value associated with it
     * @throws java.lang.IllegalArgumentException if key or value is null
     */
    public V put(K key, V value) {
        if (key == null || value == null) {
            throw new IllegalArgumentException("You can not add a null "
                    + "value or a null key to the hash map");
        }
        V result = null;
        if (((double) (size + 1) / table.length) >= MAX_LOAD_FACTOR) {
            this.resizeBackingTable(table.length * 2 + 1);
        }
        int addAtIndex = Math.abs(key.hashCode() % table.length);
        int constantH = 0;
        boolean added = false;
        boolean removed = false;
        int removeIndex = 0;
        while (!added) {
            if (table[addAtIndex] == null) {
                if (removed) {
                    table[removeIndex] = new LinearProbingMapEntry<K, V>(key,
                            value);
                    added = true;
                    size++;
                    break;
                } else {
                    table[addAtIndex] = new LinearProbingMapEntry<K, V>(key,
                            value);
                    added = true;
                    size++;
                    break;
                }
            } else if (table[addAtIndex].getKey().equals(key) //if kes equal,
                    //just at it this position immediately
                    && !(table[addAtIndex].isRemoved())) {
                result = table[addAtIndex].getValue();
                table[addAtIndex] = new LinearProbingMapEntry<K, V>(key, value);
                added = true;
                break;
            } else if (table[addAtIndex].isRemoved()
                    && table[addAtIndex].getKey().equals(key)) {
                if (removed) {
                    table[removeIndex] = new LinearProbingMapEntry<K, V>(key,
                            value);
                    added = true;
                    size++;
                    break;
                } else {
                    table[addAtIndex] = new LinearProbingMapEntry<K, V>(key,
                            value);
                    added = true;
                    result = null;
                    size++;
                    break;
                }
            } else if (table[addAtIndex] != null
                    && table[addAtIndex].isRemoved()) {
                if (!removed) { //The first DEL spot
                    removeIndex = addAtIndex;
                    removed = true;
                }
                addAtIndex = (addAtIndex + 1) % table.length;
            } else {
                addAtIndex = (addAtIndex + 1) % table.length;
            }
        }
        return result;
    }

    /**
     * Removes the entry with a matching key from map by marking the entry as
     * removed.
     *
     * @param key the key to remove
     * @return the value previously associated with the key
     * @throws java.lang.IllegalArgumentException if key is null
     * @throws java.util.NoSuchElementException   if the key is not in the map
     */
    public V remove(K key) {
        if (key == null) {
            throw new IllegalArgumentException("You can not remove a "
                    + "null key from the map");
        }
        boolean found = false;
        V result = null;
        int removeAtIndex = Math.abs(key.hashCode() % table.length);
        while (!found) {
            if (table[removeAtIndex] == null) {
                break;
            }
            if (table[removeAtIndex].getKey().equals(key)
                    && !(table[removeAtIndex].isRemoved())) {
                result = table[removeAtIndex].getValue();
                table[removeAtIndex].setRemoved(true);
                found = true;
                break;
            } else {
                removeAtIndex = (removeAtIndex + 1) % table.length;
            }
        }
        if (!found) {
            throw new NoSuchElementException("The key was not found in the "
                    + "hash map");
        }
        size--;
        return result;
    }

    /**
     * Gets the value associated with the given key.
     *
     * @param key the key to search for in the map
     * @return the value associated with the given key
     * @throws java.lang.IllegalArgumentException if key is null
     * @throws java.util.NoSuchElementException   if the key is not in the map
     */
    public V get(K key) {
        if (key == null) {
            throw new IllegalArgumentException("You can not remove a "
                    + "null key from the map");
        }
        boolean found = false;
        V result = null;
        int getAtIndex = Math.abs(key.hashCode() % table.length);
        while (!found) {
            if (table[getAtIndex] == null) {
                break;
            }
            if (table[getAtIndex].getKey().equals(key)
                    && !(table[getAtIndex].isRemoved())) {
                result = table[getAtIndex].getValue();
                found = true;
                break;
            } else {
                getAtIndex = (getAtIndex + 1) % table.length;
            }
        }
        if (!found) {
            throw new NoSuchElementException("The key was not found in the "
                    + "hash map");
        }
        return result;
    }

    /**
     * Returns whether or not the key is in the map.
     *
     * @param key the key to search for in the map
     * @return true if the key is contained within the map, false
     * otherwise
     * @throws java.lang.IllegalArgumentException if key is null
     */
    public boolean containsKey(K key) {
        if (key == null) {
            throw new IllegalArgumentException("You can not remove a "
                    + "null key from the map");
        }
        boolean found = false;
        int getAtIndex = Math.abs(key.hashCode() % table.length);
        int constantH = 0;
        while (!found) {
            getAtIndex = (getAtIndex + constantH) % table.length;
            if (table[getAtIndex] == null) {
                break;
            }
            if (table[getAtIndex].getKey().equals(key)
                    && !(table[getAtIndex].isRemoved())) {
                found = true;
                break;
            } else {
                constantH++;
            }
        }
        return found;
    }

    /**
     * Returns a Set view of the keys contained in this map.
     *
     * Use java.util.HashSet.
     *
     * @return the set of keys in this map
     */
    public Set<K> keySet() {
        Set<K> keySet = new HashSet<K>();
        for (LinearProbingMapEntry<K, V> element : table) {
            if (element == null) {
                continue;
            }
            if (element.isRemoved()) {
                continue;
            } else {
                keySet.add(element.getKey());
            }
        }
        return keySet;
    }

    /**
     * Returns a List view of the values contained in this map.
     *
     * Use java.util.ArrayList or java.util.LinkedList.
     *
     * You should iterate over the table in order of increasing index and add
     * entries to the List in the order in which they are traversed.
     *
     * @return list of values in this map
     */
    public List<V> values() {
        List<V> result = new ArrayList<V>(size);
        for (LinearProbingMapEntry<K, V> element : table) {
            if (element == null) {
                continue;
            }
            if (element.isRemoved()) {
                continue;
            } else {
                result.add(element.getValue());
            }
        }
        return result;
    }

    /**
     * Resize the backing table to length.
     *
     * Disregard the load factor for this method. So, if the passed in length is
     * smaller than the current capacity, and this new length causes the table's
     * load factor to exceed MAX_LOAD_FACTOR, you should still resize the table
     * to the specified length and leave it at that capacity.
     *
     * You should iterate over the old table in order of increasing index and
     * add entries to the new table in the order in which they are traversed.
     *
     * Since resizing the backing table is working with the non-duplicate
     * data already in the table, you shouldn't explicitly check for
     * duplicates.
     *
     * Hint: You cannot just simply copy the entries over to the new array.
     *
     * @param length new length of the backing table
     * @throws java.lang.IllegalArgumentException if length is less than the
     *                                            number of items in the hash
     *                                            map
     */
    public void resizeBackingTable(int length) {
        if (length < size) {
            throw new IllegalArgumentException("You can not resize the hash"
                    + " map into one that has a smaller length than the "
                    + "current size");
        }
        LinearProbingMapEntry<K, V>[] newTable = (LinearProbingMapEntry<K, V>[])
                new LinearProbingMapEntry[length];
        for (LinearProbingMapEntry<K, V> element : table) {
            if (element == null) {
                continue;
            } else if (element.isRemoved()) {
                continue;
            } else {
                K key = element.getKey();
                V value = element.getValue();
                int addAtIndex = Math.abs(key.hashCode() % newTable.length);
                int constantH = 0;
                boolean added = false;
                boolean removed = false;
                int removeIndex = 0;
                while (!added) {
                    if (newTable[addAtIndex] == null) {
                        if (removed) {
                            newTable[removeIndex] = new
                                    LinearProbingMapEntry<K, V>(key, value);
                            added = true;
                            break;
                        } else {
                            newTable[addAtIndex] = new
                                    LinearProbingMapEntry<K, V>(key, value);
                            added = true;
                            break;
                        }
                    } else if (newTable[addAtIndex].getKey().equals(key)) {
                        newTable[addAtIndex] = new LinearProbingMapEntry<K, V>(
                                key, value);
                        added = true;
                        break;
                    } else if (newTable[addAtIndex] != null
                            && newTable[addAtIndex].isRemoved()) {
                        if (!removed) { //The first DEL spot
                            removeIndex = addAtIndex;
                            removed = true;
                        }
                        addAtIndex = (addAtIndex + constantH) % newTable.length;
                        constantH++;
                    } else if (newTable[addAtIndex] != null) {
                        addAtIndex = (addAtIndex + constantH) % newTable.length;
                        constantH++;
                    }
                }
            }
        }
        table = newTable;
    }

    /**
     * Clears the map.
     *
     * Resets the table to a new array of the INITIAL_CAPACITY and resets the
     * size.
     *
     * Must be O(1).
     */
    public void clear() {
        LinearProbingMapEntry<K, V>[] newTable = (LinearProbingMapEntry<K, V>[])
                new LinearProbingMapEntry[table.length];
        size = 0;
        table = newTable;
    }

    /**
     * Returns the table of the map.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the table of the map
     */
    public LinearProbingMapEntry<K, V>[] getTable() {
        // DO NOT MODIFY THIS METHOD!
        return table;
    }

    /**
     * Returns the size of the map.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the map
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}
