package structures;

import java.time.LocalDate;

import static java.time.temporal.ChronoUnit.DAYS;

/**
 * Distance between two May 1st
 * @author Adrian Helberg, Rodrigo Ehlers
 */
public class MayDistance {
    private LocalDate first;
    private LocalDate second;

    public MayDistance(LocalDate f, LocalDate s) {
        first = f;
        second = s;
    }

    public String getFormattedDistanceString() {
        return "Days between the two MAY 1st: " + DAYS.between(first, second) + "";
    }
}
