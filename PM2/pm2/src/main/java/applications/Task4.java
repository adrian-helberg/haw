/**
 * Task 4 Entry Point
 * @author Adrian Helberg, Rodrigo Ehlers
 */
package applications;

public class Task4 {

    public static void main(String[] args) {
        Object x = "Try";
        String i = "again";
        x = x + i; // Must be LEGAL
        x += i; // Was ILLEGAL ’til Java 6
    }
}