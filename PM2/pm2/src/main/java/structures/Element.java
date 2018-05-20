/**
 * Element representation as part as a double linked list
 * @author Adrian Helberg, Rodrigo Ehlers
 */
package structures;

public class Element<E> {

    private E content;

    private Element<E> next;

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

    public Element<E> getPrev() {
        return this.prev;
    }

    public void setNext(Element<E> next) {
        this.next = next;
    }

    public void setPrev(Element<E> prev) {
        this.prev = prev;
    }
}
