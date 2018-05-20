/**
 * Task 4 Entry Point
 * @author Adrian Helberg, Rodrigo Ehlers
 */
package applications;

import structures.ArrayDeque;
import structures.ListDeque;

public class Task4 {

    public static void main(String[] args) {

        ArrayDeque<Integer> deque = new ArrayDeque<>(5);

        deque.enqueue(10);

        System.out.println(deque);
    }
}
