package applications;

import structures.MayDistance;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import static java.time.temporal.TemporalAdjusters.next;

/**
 * PM2 Praktikum, Aufgabenblatt 3
 * @author Adrian Helberg, Rodrigo Ehlers
 */
public class Task3 {

    public static void main(String[] args) {

        // 1.1
        LocalDate workers_day = LocalDate.of(1889, Month.MAY, 1);
        LocalDate until = LocalDate.of(2100, Month.MAY, 1);

        List<Integer> dates = Stream
                .iterate(workers_day, d -> d.plusYears(1))
                .limit(ChronoUnit.YEARS.between(workers_day, until))
                .filter(d -> d.getDayOfWeek().toString().equals("TUESDAY"))
                .mapToInt(LocalDate::getYear)
                .boxed() // Primitive stream
                .collect(Collectors.toList());

        System.out.println("Years of 1st May on tuesday: ");
        dates.forEach(d -> System.out.print(d + ", "));
        System.out.println("\n");

        // 1.2
        LocalDate date1 = LocalDate.of(1894, 5, 1);
        LocalDate date2 = LocalDate.of(1900, 5, 1);
        final MayDistance distance = new MayDistance(date1, date2);
        System.out.println(distance.getFormattedDistanceString());

        // 1.3
        final LocalDate date = LocalDate.of(2018, Month.MAY, 1);
        final LocalDate nextSunday = date.with(next(DayOfWeek.SUNDAY));
        final int nextLeapYear = nextLeapYear(date);
        final LocalDate after42Days = date.plusDays(42);

        System.out.println("Next sunday: " + nextSunday);
        System.out.println("Next leap year: " + nextLeapYear);
        System.out.println("Date after 42 days: " + after42Days);


    }

    private static int nextLeapYear(LocalDate date) {
        return date.isLeapYear() ? date.getYear() : nextLeapYear(date.plusYears(1));
    }
}
