/**
 * Implementation of double linked list as deque
 * @author Adrian Helberg, Rodrigo Ehlers
 */
package structures;

import java.util.NoSuchElementException;

public class ListDeque<E> implements Deque<E> {

    private Element<E> first;

    private Element<E> last;

    public ListDeque() {
        first = null;
        last = null;
    }

    @Override
    public void push(E data) {
        checkNull(data);

        Element<E> current = new Element<>();
        current.setContent(data);
        current.setNext(first);

        if (isEmpty()) {
            first = last = current;
        } else {
            first.setPrev(current);
            first = current;
        }
    }

    @Override
    public void pop() throws NoSuchElementException {
        if (isEmpty()) {
            throw new NoSuchElementException("Stack is empty");
        } else {
            if (first == last) {
                first = last = null;
            } else {
                first = first.getNext();
                first.setPrev(null);
            }
        }
    }

    @Override
    public E peekFirst() throws NoSuchElementException {
        if (isEmpty()) {
            throw new NoSuchElementException("List is empty");
        } else {
            return first.getContent();
        }
    }

    @Override
    public E peekLast() throws NoSuchElementException {
        if (last == null) {
            throw new NoSuchElementException("List is empty");
        } else {
            return last.getContent();
        }
    }

    @Override
    public void enqueue(E data) {
        checkNull(data);

        Element<E> current = new Element<>();
        current.setContent(data);

        if (isEmpty()) {
            first = last = current;
        } else {
            current.setPrev(last);
            last.setNext(current);
            last = current;
        }
    }

    @Override
    public void dequeue() throws NoSuchElementException {
        if (isEmpty()) {
            throw new NoSuchElementException("List is empty");
        } else {
            if (first == last) {
                first = last = null;
            } else {
                first = first.getNext();
                first.setPrev(null);
            }
        }
    }

    @Override
    public boolean isEmpty() {
        return first == null;
    }

    @Override
    public String toString() {
        if (isEmpty()) {
            return "empty";
        } else {
            StringBuilder result = new StringBuilder();
            Element<E> tmp = first;

            result.append(tmp.getContent());
            tmp = tmp.getNext();

            while (tmp != null) {
                result.append("\n");
                result.append("||");
                result.append("\n");
                result.append(tmp.getContent());
                tmp = tmp.getNext();
            }

            return result.toString();
        }
    }

    public String toStringBottomUp() {
        if (isEmpty()) {
            return "empty";
        } else {
            StringBuilder result = new StringBuilder();
            Element<E> tmp = last;

            result.append(tmp.getContent());
            tmp = tmp.getPrev();

            while (tmp != null) {
                result.append("\n");
                result.append("||");
                result.append("\n");
                result.append(tmp.getContent());
                tmp = tmp.getPrev();
            }

            return result.toString();
        }
    }
}
