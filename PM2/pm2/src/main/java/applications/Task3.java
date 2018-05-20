package applications;

import structures.MayDistance;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.List;
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
                .filter(d -> d.getDayOfWeek()
                        .toString().equals("TUESDAY"))
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

        try {

            // 3.2
            List<String> palindromes = Files.lines(Paths.get("scrabble.txt"))
                    .filter(s -> s.length() == 6 && s.equals(new StringBuilder(s).reverse().toString()))
                    .collect(Collectors.toList());

            System.out.println("Palindromes in scrabble.txt: ");
            palindromes.forEach(p -> System.out.print(p + ", "));
            System.out.println("\n");

            // 3.3
            List<String> eee = Files.lines(Paths.get("scrabble.txt"))
                    .filter(s -> s.length() > 5 && s.charAt(1) == 'e' && s.charAt(3) == 'e' && s.charAt(5) == 'e')
                    .collect(Collectors.toList());

            System.out.println("EEE in scrabble.txt: ");
            eee.forEach(e -> System.out.print(e + ", "));
            System.out.println("\n");

            // 3.4
            List<String> vowels = Files.lines(Paths.get("scrabble.txt"))
                    .filter(s -> s.contains("aa") || s.contains("ee") || s.contains("ii") || s.contains("oo") || s.contains("uu"))
                    .collect(Collectors.toList());

            System.out.println("Vowel pairs in scrabble.txt: ");
            vowels.forEach(v -> System.out.print(v + ", "));
            System.out.println("\n");

            // 3.5
            List<String> sgb = Files.lines(Paths.get("sgb-words.txt"))
                    .collect(Collectors.toList());

            List<String> common = Files.lines(Paths.get("scrabble.txt"))
                    .filter(sgb::contains)
                    .collect(Collectors.toList());

            System.out.println("Common in scrabble.txt and sgb-words.txt: ");
            common.forEach(c -> System.out.print(c + ", "));
            System.out.println("\n");

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static int nextLeapYear(LocalDate date) {
        return date.isLeapYear() ? date.getYear() : nextLeapYear(date.plusYears(1));
    }
}
