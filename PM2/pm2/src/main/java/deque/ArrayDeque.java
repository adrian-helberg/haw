package deque;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.NoSuchElementException;

public class ArrayDeque<E> implements Deque<E>, Serializable {

    private static final long serialVersionUID = 1;

    /**
     * The arrays length
     * @serial
     */
    private int length;

    /**
     * The arrays current top element index
     * @serial
     */
    private int top = -1;
    private E elements[];

    public ArrayDeque(int size) {
        length = size;
        elements = (E[]) new Object[length];
    }

    public int size() {
        return top + 1;
    }

    @Override
    public void push(E item) throws IllegalStateException {
        checkNull(item);

        if (size() == length) {
            throw new IllegalStateException("Stack is full");
        } else {
            elements[++top] = item;
        }
    }

    @Override
    public void pop() throws NoSuchElementException {
        if (isEmpty()) {
            throw new NoSuchElementException("Stack is empty");
        } else {
            elements[top--] = null;
        }
    }

    @Override
    public E peekFirst() throws NoSuchElementException {
        if (isEmpty()) {
            throw new NoSuchElementException("List is empty");
        } else {
            return elements[top];
        }
    }

    @Override
    public E peekLast() throws NoSuchElementException {
        if (isEmpty()) {
            throw new NoSuchElementException("List is empty");
        } else {
            return elements[0];
        }
    }

    @Override
    public void enqueue(E item) {
        checkNull(item);

        if (size() == length) {
            throw new IllegalStateException("Stack is full");
        } else {

            for (int i = elements.length - 1; i > 0; i--) {
                if (elements[i - 1] != null) {
                    elements[i] = elements[i - 1];
                }
            }

            elements[0] = item;
            top++;
        }
    }

    @Override
    public void dequeue() throws NoSuchElementException {
        if (isEmpty()) {
            throw new NoSuchElementException("Array is empty");
        } else {
            elements[top--] = null;
        }
    }

    @Override
    public boolean isEmpty() {
        return top < 0;
    }

    @Override
    public String toString() {
        if (isEmpty()) {
            return "[]";
        } else {
            StringBuilder result = new StringBuilder("[");
            result.append(elements[elements.length - 1]);

            for (int i = elements.length - 2; i > -1; i--) {
                result.append(" > ");
                result.append(elements[i]);
            }

            result.append("]");

            return result.toString();
        }
    }

    private void writeObject(ObjectOutputStream s) throws IOException {
        s.writeInt(length);
        s.writeObject(elements);
    }

    private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
        int i = s.readInt();
        length = i;
        top = i;
        elements = (E[]) s.readObject();
    }
}
