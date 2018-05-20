/**
 * Test class ListDeque
 * @author Adrian Helberg, Rodrigo Ehlers
 */

import org.junit.Test;
import structures.ListDeque;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ListDequeTest {

    @Test
    public void testPush() {
        ListDeque<Integer> deque = new ListDeque<>();

        deque.push(10);
        assertEquals(deque.peekFirst(), new Integer(10));
        assertEquals(deque.peekLast(), new Integer(10));

        deque.push(11);
        assertEquals(deque.peekFirst(), new Integer(11));
        assertEquals(deque.peekLast(), new Integer(10));
    }

    @Test
    public void testPop() {
        ListDeque<Integer> deque = new ListDeque<>();

        deque.push(10);
        deque.push(11);
        deque.pop();
        assertEquals(deque.peekFirst(), new Integer(10));
    }

    @Test
    public void testPeekFirst() {
        ListDeque<Integer> deque = new ListDeque<>();

        deque.push(10);
        assertEquals(deque.peekFirst(), new Integer(10));
    }

    @Test
    public void testPeekLast() {
        ListDeque<Integer> deque = new ListDeque<>();

        deque.push(10);
        assertEquals(deque.peekFirst(), new Integer(10));
    }

    @Test
    public void testEnqueue() {
        ListDeque<Integer> deque = new ListDeque<>();

        deque.enqueue(10);
        assertEquals(deque.peekFirst(), new Integer(10));
        assertEquals(deque.peekLast(), new Integer(10));

        deque.enqueue(11);
        assertEquals(deque.peekFirst(), new Integer(10));
        assertEquals(deque.peekLast(), new Integer(11));
    }

    @Test
    public void testDequeue() {
        ListDeque<Integer> deque = new ListDeque<>();

        deque.enqueue(10);
        deque.enqueue(11);
        deque.dequeue();

        assertEquals(deque.peekFirst(), new Integer(11));
    }

    @Test
    public void testIsEmpty() {
        ListDeque<Integer> deque = new ListDeque<>();

        deque.push(10);
        deque.pop();
        assertTrue(deque.isEmpty());
    }
}
