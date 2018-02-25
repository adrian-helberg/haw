package de.haw.wpcgar.math;

/**
 * Helper.
 * @author Adrian Helberg
 */
public class Helper {

    public static int getAlignedValueFromParameter(double parameterValue, int minColorValue, int maxColorValue, double minParameterValue, double maxParameterValue) {

        if (parameterValue < minParameterValue) {
            parameterValue = minParameterValue;
        }

        if (parameterValue > maxParameterValue) {
            parameterValue = maxParameterValue;
        }

        double percentage = (parameterValue - minParameterValue) / (maxParameterValue - minParameterValue);
        double value = minColorValue + percentage * (maxColorValue - minColorValue);

        return (int) value;
    }
}