/**
 * Test class ArrayDeque
 * @author Adrian Helberg, Rodrigo Ehlers
 */

import org.junit.Test;
import structures.ArrayDeque;
import structures.ListDeque;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ArrayDequeTest {

    @Test
    public void testPush() {
        ArrayDeque<Integer> deque = new ArrayDeque<>(5);

        deque.push(10);
        assertEquals(deque.peekFirst(), new Integer(10));
        assertEquals(deque.peekLast(), new Integer(10));

        deque.push(11);
        assertEquals(deque.peekFirst(), new Integer(11));
        assertEquals(deque.peekLast(), new Integer(10));
    }

    @Test
    public void testPop() {
        ArrayDeque<Integer> deque = new ArrayDeque<>(5);

        deque.push(10);
        deque.push(11);
        deque.pop();
        assertEquals(deque.peekFirst(), new Integer(10));
    }

    @Test
    public void testPeekFirst() {
        ArrayDeque<Integer> deque = new ArrayDeque<>(5);

        deque.push(10);
        assertEquals(deque.peekFirst(), new Integer(10));
    }

    @Test
    public void testPeekLast() {
        ArrayDeque<Integer> deque = new ArrayDeque<>(5);

        deque.push(10);
        assertEquals(deque.peekFirst(), new Integer(10));
    }

    @Test
    public void testEnqueue() {
        ArrayDeque<Integer> deque = new ArrayDeque<>(5);

        deque.enqueue(10);
        assertEquals(deque.peekFirst(), new Integer(10));
        assertEquals(deque.peekLast(), new Integer(10));

        deque.enqueue(11);
        assertEquals(deque.peekFirst(), new Integer(10));
        assertEquals(deque.peekLast(), new Integer(11));
    }

    @Test
    public void testDequeue() {
        ArrayDeque<Integer> deque = new ArrayDeque<>(5);

        deque.enqueue(10);
        deque.enqueue(11);
        deque.dequeue();

        assertEquals(deque.peekFirst(), new Integer(11));
    }

    @Test
    public void testIsEmpty() {
        ArrayDeque<Integer> deque = new ArrayDeque<>(5);

        deque.push(10);
        deque.pop();
        assertTrue(deque.isEmpty());
    }
}
