import org.junit.Test;
import utils.FormatUtils;

import java.util.ArrayList;
import java.util.List;
/**
 * Test class FormatUtils
 * @author Adrian Helberg, Rodrigo Ehlers
 */
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

/**
 * Test interface ExtendedPredicate
 * @author Adrian Helberg, Rodrigo Ehlers
 */
public class FormatUtilsTest {

    @Test
    public void andAllTest() {
        List<String> values = new ArrayList<>();
        values.add("Automat");
        values.add("Baum");
        values.add("Auto");
        values.add("Laut");

        Predicate<String>[] predicates = new Predicate[3];
        predicates[0] = c -> c.startsWith("A");
        predicates[1] = c -> c.length() == 4;
        predicates[2] = c -> c.contains("o");

        Predicate<String> p = FormatUtils.andAll(predicates);

        List<String> filteredList = values
                .stream()
                .filter(p)
                .collect(Collectors.toList());

        List<String> expected = new ArrayList<>();
        expected.add("Auto");

        assertEquals(expected, filteredList);
    }

    @Test
    public void orAnyTest() {
        List<String> values = new ArrayList<>();
        values.add("Automat");
        values.add("Baum");
        values.add("Auto");
        values.add("Laut");

        Predicate<String>[] predicates = new Predicate[3];
        predicates[0] = c -> c.startsWith("A");
        predicates[1] = c -> c.length() == 4;
        predicates[2] = c -> c.contains("o");

        Predicate<String> p = FormatUtils.orAny(predicates);

        List<String> filteredList = values
                .stream()
                .filter(p)
                .collect(Collectors.toList());

        List<String> expected = new ArrayList<>();
        expected.add("Automat");
        expected.add("Baum");
        expected.add("Auto");
        expected.add("Laut");


        assertEquals(expected, filteredList);
    }

}
