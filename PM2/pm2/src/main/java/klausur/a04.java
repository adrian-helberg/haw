/**
 * @author Adrian Helberg
 */
package klausur;

import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class a04 {

    public static void main(String[] args) {

        sum(1, 2, 3);

//        LocalDate start = LocalDate.of(1126, 7, 25);
//
//        // Unbegrenzter Stream
//        Stream<LocalDate> compostelanischeJahre = Stream
//                .iterate(start, (LocalDate x) -> x.plusYears(1))
//                .filter(x -> x.getDayOfWeek() == DayOfWeek.SUNDAY);
//
//        // Jahre zwischen 2000 und 2100
//        List<LocalDate> jahre = compostelanischeJahre
//                .limit(2100 - 2000)
//                .filter(x -> x.isAfter(LocalDate.of(2000, 01, 01).minusDays(1)))
//                .filter(x -> x.isBefore(LocalDate.of(2100, 01, 01).plusDays(1)))
//                .collect(Collectors.toList());
//
//        List<Integer> daysbetween = new ArrayList<>();
//        for (int i = 1; i < jahre.size(); i++) {
//            LocalDate current = jahre.get(i - 1);
//            LocalDate next = jahre.get(i);
//
//            Instant currentInstant = LocalDateTime.of(current, LocalTime.of(0, 0)).atZone(ZoneId.systemDefault()).toInstant();
//            Instant nextInstant = LocalDateTime.of(next, LocalTime.of(0, 0)).atZone(ZoneId.systemDefault()).toInstant();
//
//            long days = Duration.between(currentInstant, nextInstant).toDays();
//            daysbetween.add((int)days);
//        }
    }

    public static int sum(int ... col) {

        int result = Arrays.stream(col).sum();
col.len
        int sum = 0;
        for (int i : col) {
            sum += i;
        }

        System.out.println(sum);

        return 0;
    }
}
