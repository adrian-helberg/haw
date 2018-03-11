package de.haw.wpcgar.structure.params;

import de.haw.wpcgar.structure.Parameter;

public class Population extends Parameter {

    public Population(String seed) {
        super("population", seed);
    }

    @Override
    protected double value(double x, double y, double z) {

        double value = noise.fBm(
                1 * x,
                1 * y,
                1 * z,
                1,
                1.0,
                1.0
        );

        return value;
    }
}
