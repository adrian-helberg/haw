/**
 * @author Adrian Helberg
 */
package klausur;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Generic utility class
 */
class Collections02 {

    // Returns the avarage size of collections in a collection of collections
    static <T> int GetAverageCollectionsSize(Collection<Collection<T>> collectionOfCollection) {

        int numberOfElements = 0;
        for (Collection<T> c: collectionOfCollection) {
            numberOfElements += c.size();
        }

        return numberOfElements / collectionOfCollection.size();
    }

    // Returns the biggest Element of a collection of comparables
    static <T> T GetBiggest(Collection<ComparableObject> collectionOfComparables) {

        Collection list = collectionOfComparables.stream().map(c -> c.GetData()).collect(Collectors.toList());

        //noinspection unchecked, warning can be ignored because T only can be a Comparable
        return (T) Collections.max(list);
    }

    static int SumExact01(List<Integer> list) throws ArithmeticException {

        long sum = 0;
        for(int i : list) {
            sum += (long)i;

            if (sum < Integer.MIN_VALUE || sum > Integer.MAX_VALUE) {
                throw new ArithmeticException("Integer overflow");
            }
        }

        return (int) sum;
    }
}
