/**
 * @author Adrian Helberg
 */
package klausur;

/**
 * Helper class for ensuring that T is a Comparable
 * @param <T> Data
 */
class ComparableObject <T extends Comparable> {
    // Representing actual data from this wrapper
    private T data;

    /**
     * Constructor
     * @param data Data
     */
    ComparableObject(T data) {
        this.data = data;
    }

    /**
     * Data access
     * @return data
     */
    T GetData() {
        return this.data;
    }
}
