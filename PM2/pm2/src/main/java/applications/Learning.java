package applications;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.Arrays;
import java.util.Comparator;

public class Learning {

    public static void main(String[] args) {
        dateTime();
        streams();
    }

    private static void dateTime() {
        // Datum
        LocalDate date = LocalDate.now();

        // Uhrzeit
        LocalTime time = LocalTime.now();

        // Datum und Uhrzeit
        LocalDateTime dateTime = LocalDateTime.now();

        // Datum setzen
        LocalDate fixDate = LocalDate.of(2018, 01, 01);

        // Formatierer
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

        // Formatierung
        String formattedDateString = fixDate.format(formatter);

        // Date Parsen
        LocalDate parsedDate = LocalDate.parse(formattedDateString, formatter);

        // Zeit Parsen
        LocalTime parsedTime = LocalTime.parse(LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm")), DateTimeFormatter.ofPattern("HH:mm"));

        // Zeitpunkt
        Instant now = Instant.now();
        Instant nowInAMinute = ChronoUnit.MINUTES.addTo(now, 1);

        // Zeitpunkt konvertieren
        LocalDateTime timeFromInstant = LocalDateTime.ofInstant(now, ZoneId.systemDefault());

        // Dauer zwischen zwei Zeitpunken
        Duration duration = Duration.between(now, now.plusSeconds(3600));

        // Dauer zwischen zwei Daten
        Period period = Period.between(date, date.plusDays(10));

        // Zeitpunk relativ zu einen anderen Zeitpunkt
        LocalDate firstFridayInNextMonth = date.plusMonths(1).with(TemporalAdjusters.firstInMonth(DayOfWeek.FRIDAY));
    }

    private static void streams() {
        // Byteorientiert
        try (OutputStream out = Files.newOutputStream(Paths.get("test.ppm"))) {
            out.write("P3 1 1 255 255 0 0".getBytes(StandardCharsets.ISO_8859_1));
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (InputStream in = Files.newInputStream(Paths.get("test.ppm"))) {
            while (in.available() > 0) {
                System.out.print((char) in.read());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            System.out.print("\n");
        }

        // Zeichenorientiert
        try (BufferedWriter out = Files.newBufferedWriter(Paths.get("test.txt"), StandardCharsets.ISO_8859_1)){
            out.write("Example string");
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (BufferedReader in = Files.newBufferedReader(Paths.get("test.txt"), StandardCharsets.ISO_8859_1)) {
            in.lines().forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
