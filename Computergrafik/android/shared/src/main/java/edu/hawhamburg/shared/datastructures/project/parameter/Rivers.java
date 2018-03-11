package edu.hawhamburg.shared.datastructures.project.parameter;

import edu.hawhamburg.shared.datastructures.project.Parameter;

public class Rivers extends Parameter {

    public Rivers(String seed) {
        super("rivers", seed);
    }

    @Override
    protected double value(double x, double y, double z) {
        double value = noise.fBm(
                x * 0.01,
                0.01 * y,
                0.01 * (z + 1),
                3,
                2.1836171,
                0.9631
        );

        value = Math.sqrt(Math.abs(value));

        return value;
    }
}
