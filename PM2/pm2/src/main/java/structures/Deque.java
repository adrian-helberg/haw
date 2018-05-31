/**
 * Interface Deque
 * @author Adrian Helberg, Rodrigo Ehlers
 */
package structures;

import java.util.EmptyStackException;
import java.util.NoSuchElementException;

public interface Deque<E> {
    /**
     * Pushes an item onto the top of the stack
     * @param item the item to be pushed onto this stack
     */
    void push(E item);

    /**
     * Removes the object at the top of this stack and returns that object
     * @throws EmptyStackException
     */
    void pop() throws EmptyStackException;

    /**
     * Looks at the object at the top of this stack without removing it from the stack
     * @return the object at the top of this stack
     * @throws NoSuchElementException if this queue is empty
     */
    E peekFirst() throws NoSuchElementException;

    /**
     * Looks at the object at the bottom of this stack without removing it from the stack
     * @return the object at the bottom of this stack
     * @throws NoSuchElementException if this queue is empty
     */
    E peekLast() throws NoSuchElementException;

    /**
     * Enqueue the item
     * @param item to add
     * @throws NullPointerException if the specified item is null
     */
    void enqueue(E item) throws NullPointerException;

    /**
     * Dequeue the item
     * @throws NoSuchElementException
     */
    void dequeue() throws NoSuchElementException;

    /**
     * Tests if this stack is empty
     * @return true if and only if this stack contains no items; false otherwise
     */
    boolean isEmpty();

    /**
     * Checks for NULL; does not allow NULL
     * @param data data to check
     * @throws NullPointerException if data is null
     */
    default void checkNull(E data) throws NullPointerException {
        if (data == null) {
            throw new NullPointerException("Null not allowed");
        }
    }
}
