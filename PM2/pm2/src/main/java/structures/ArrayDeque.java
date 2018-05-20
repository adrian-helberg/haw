package structures;

import java.util.NoSuchElementException;

public class ArrayDeque<E> implements Deque<E> {
    private int length, top = -1;
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
                if (elements[i-1] != null) {
                    elements[i] = elements[i-1];
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
            return "empty";
        } else {
            StringBuilder result = new StringBuilder();
            result.append(elements[elements.length - 1]);

            for (int i = elements.length - 2; i > -1; i--) {
                result.append("\n");
                result.append("||");
                result.append("\n");
                result.append(elements[i]);
            }

            return result.toString();
        }
    }
}
