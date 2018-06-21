/**
 * Implementation of double linked list as deque
 * @author Adrian Helberg, Rodrigo Ehlers
 */
package deque;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.NoSuchElementException;

public class ListDeque<E> implements Deque<E>, Serializable {

    /**
     * The first element
     * @serial
     */
    private Element<E> first;

    /**
     * The last element
     * @serial
     */
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
            return "[]";
        } else {
            StringBuilder result = new StringBuilder("[");
            Element<E> tmp = first;

            result.append(tmp.getContent());
            tmp = tmp.getNext();

            while (tmp != null) {
                result.append(" > ");
                result.append(tmp.getContent());
                tmp = tmp.getNext();
            }

            result.append("]");

            return result.toString();
        }
    }

    public String toStringBottomUp() {
        if (isEmpty()) {
            return "[]";
        } else {
            StringBuilder result = new StringBuilder("[");
            Element<E> tmp = last;

            result.append(tmp.getContent());
            tmp = tmp.getPrev();

            while (tmp != null) {
                result.append(" < ");
                result.append(tmp.getContent());
                tmp = tmp.getPrev();
            }

            result.append("]");

            return result.toString();
        }
    }

    private void writeObject(ObjectOutputStream s) throws IOException {
        s.writeObject(first);
        s.writeObject(last);
    }

    private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
        first = (Element<E>) s.readObject();
        last = (Element<E>) s.readObject();
    }
}
