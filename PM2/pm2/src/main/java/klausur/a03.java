package klausur;

import java.time.*;

public class a03 {

    public static void main(String[] args) {
        LocalDate klausurEinsichtTag = LocalDate.of(2016, 7, 1);
        LocalDate durchsichtStartTag = LocalDate.of(2016, 6, 28);

        Instant klausurEinsicht = LocalDateTime.of(2016, 7, 1, 12, 0).atZone(ZoneId.systemDefault()).toInstant();
        Instant durchsichtStart = LocalDateTime.of(2016, 6, 28, 6, 0).atZone(ZoneId.systemDefault()).toInstant();

        Duration einsichtDauer = Duration.between(durchsichtStart, klausurEinsicht);
        long einsichtMinuten = einsichtDauer.getSeconds() / 60;
        long einsichtMinutenProStudent = einsichtMinuten / 50;

        long einsichtTage = Period.between(durchsichtStartTag, klausurEinsichtTag).getDays();
        // Tag der Klausureinsicht wird hier nicht beruecksichtigt
        long pausenInMinuten = (einsichtTage - 1) * 8 * 60;

        long einsichtMitPausenInMinutenProStudent = (einsichtMinuten - pausenInMinuten) / 80;
    }
}
