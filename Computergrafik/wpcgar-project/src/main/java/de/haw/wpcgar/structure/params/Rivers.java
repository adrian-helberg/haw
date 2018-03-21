package de.haw.wpcgar.structure.params;

import de.haw.wpcgar.structure.Parameter;

public class Rivers extends Parameter {

    public Rivers(String seed) {
        super("rivers", seed);
    }

    @Override
    protected double value(double x, double y, double z) {
        double value = noise.fBm(
                0.008 * x,
                0.008 * y,
                0.008 * (z + 1),
                3,
                2.1836171,
                1.9631
        );

        value = Math.sqrt(Math.abs(value));

        return value;
    }
}
