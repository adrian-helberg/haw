package de.haw.wpcgar.math;

/**
 * Helper.
 * @author Adrian Helberg
 */
public class Helper {

    public static int getAlignedValueFromHeight(double height, int minValue, int maxValue, double minHeight, double maxHeight) {
        double percentage = (height - minHeight) / (maxHeight - minHeight);
        double value = minValue + percentage * (maxValue - minValue);

        return (int) value;
    }
}