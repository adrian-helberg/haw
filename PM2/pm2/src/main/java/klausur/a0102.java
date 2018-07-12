/**
 * @author Adrian Helberg
 */
package klausur;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class a0102 {

    public static void main(String[] args) {

        LocalDate start = LocalDate.of(2016, 9,1);
        LocalDate end = start.plusYears(3);

        Instant startInstant = LocalDateTime.of(start, LocalTime.of(0, 0)).atZone(ZoneId.systemDefault()).toInstant();
        Instant endInstant = LocalDateTime.of(end, LocalTime.of(0, 0)).atZone(ZoneId.systemDefault()).toInstant();

        long days = Duration.between(startInstant, endInstant).toDays();

        System.out.println("Das Studium wird nach 6 Semestern " + (int) days + " Tage gedauert haben.");

        LocalDate pruefungDrittesSemester = LocalDate.of(2018, 3, 1).minusDays(1);
        LocalDate drittVersuch = pruefungDrittesSemester.plusYears(2).plusDays(1);

        System.out.println("Bis zum " + drittVersuch.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")) + " muss die Pruefung bestanden sein.");

        Instant drittVersuchInstant = LocalDateTime.of(drittVersuch, LocalTime.of(0, 0)).atZone(ZoneId.systemDefault()).toInstant();
        long verlaengerung = Duration.between(endInstant, drittVersuchInstant).toMinutes();

        System.out.println("Das Studium verlaengert sich um " + (int) verlaengerung + " Minuten.");

        try (Scanner scn = new Scanner(new File("test.txt"))) {

            String line;
            while ((line = scn.nextLine()) != null) {

                if (line.startsWith("import")) {
                    System.out.println(line);
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
