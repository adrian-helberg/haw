package edu.hawhamburg.shared.datastructures.project.parameter;

import edu.hawhamburg.shared.datastructures.project.Parameter;

public class Snowy extends Parameter {

    public Snowy(String seed) {
        super("snowy", seed);
    }

    @Override
    protected double value(double x, double y, double z) {
        double value = 0.0;

        value += noise.fBm(
                0.001 * x,
                0.001 * y,
                0.001 * z,
                8,
                1.3464641,
                1.46156142
        ) + 0.465654;

        return value;
    }
}
