/**
 * @author Adrian Helberg
 */
package klausur;

import org.junit.*;
import static org.junit.Assert.*;

import java.util.*;

/**
 * Test Class for Collections02 class
 */
public class Collections02Test {
    private Collection<Collection<Integer>> collectionOfCollectionsInt;
    private Collection<Collection<String>> collectionOfCollectionsString;

    private Collection<ComparableObject> collectionOfComparableInt;
    private Collection<ComparableObject> collectionOfComparableString;

    /**
     * Run before tests
     */
    @Before
    public void initialize() {
        // Collection of size 1 with 3 Collections of size 3 of Integers
        collectionOfCollectionsInt = new ArrayList<>();
        collectionOfCollectionsInt.add(new ArrayList<>(Arrays.asList(1)));
        collectionOfCollectionsInt.add(new ArrayList<>(Arrays.asList(2, 3)));
        collectionOfCollectionsInt.add(new ArrayList<>(Arrays.asList(4, 5, 6)));

        // Collection of size 1 with 3 Collections of size 3 of String
        collectionOfCollectionsString = new ArrayList<>();
        collectionOfCollectionsString.add(new ArrayList<>(Arrays.asList("eins")));
        collectionOfCollectionsString.add(new ArrayList<>(Arrays.asList("zwei", "drei")));
        collectionOfCollectionsString.add(new ArrayList<>(Arrays.asList("vier", "fuenf", "sechs")));

        // Collection Comparables of Integers
        collectionOfComparableInt = new ArrayList<>();
        collectionOfComparableInt.add(new ComparableObject<>(2));
        collectionOfComparableInt.add(new ComparableObject<>(1));
        collectionOfComparableInt.add(new ComparableObject<>(3));

        // Collection Comparables of String
        collectionOfComparableString = new ArrayList<>();
        collectionOfComparableString.add(new ComparableObject<>("eins"));
        collectionOfComparableString.add(new ComparableObject<>("sieben"));
        collectionOfComparableString.add(new ComparableObject<>("dreizehn"));
    }

    @Test
    public void TestGetCollectionSize() {
        assertEquals(2, Collections02.GetAverageCollectionsSize(collectionOfCollectionsInt));
        assertEquals(2, Collections02.GetAverageCollectionsSize(collectionOfCollectionsString));
    }

    @Test
    public void TestGetBiggest() {
        assertEquals(Integer.valueOf(3), Collections02.GetBiggest(collectionOfComparableInt));
        assertEquals("sieben", Collections02.GetBiggest(collectionOfComparableString));
    }

    @Test
    public void TestSumExact01() {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);

        assertEquals(6, Collections02.SumExact01(list));
    }
}
