package edu.hawhamburg.shared.datastructures.project.parameter;

import edu.hawhamburg.shared.datastructures.project.Parameter;

public class Temperature extends Parameter {

    public Temperature(String seed) {
        super("temperature", seed);
    }

    @Override
    protected double value(double x, double y, double z) {

        double temperature = noise.fBm(
                x * 0.0008,
                0.0008 * y,
                0,
                7,
                2.1836171,
                0.7631
        );

        temperature = 32.0 + temperature * 64.0;

        return temperature;
    }
}
