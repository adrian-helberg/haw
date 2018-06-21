/**
 * Element representation as part as a double linked list
 *
 * @author Adrian Helberg, Rodrigo Ehlers
 */
package deque;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Element<E> implements Serializable {

    /**
     * The elements content
     * @serial
     */
    private E content;

    /**
     * The next element
     * @serial
     */
    private Element<E> next;

    /**
     * The previous element
     * @serial
     */
    private Element<E> prev;

    // Default constructor
    public Element() {
        this.content = null;
        this.next = this.prev = null;
    }

    public E getContent() {
        return this.content;
    }

    public void setContent(E content) {
        this.content = content;
    }

    public Element<E> getNext() {
        return this.next;
    }

    public void setNext(Element<E> next) {
        this.next = next;
    }

    public Element<E> getPrev() {
        return this.prev;
    }

    public void setPrev(Element<E> prev) {
        this.prev = prev;
    }

    private void writeObject(ObjectOutputStream s) throws IOException {
        s.writeObject(content);
        s.writeObject(next);
        s.writeObject(prev);
    }

    private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
        content = (E) s.readObject();
        next = (Element<E>) s.readObject();
        prev = (Element<E>) s.readObject();
    }
}
