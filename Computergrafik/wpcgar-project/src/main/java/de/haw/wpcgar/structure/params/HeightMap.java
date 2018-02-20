package de.haw.wpcgar.structure.params;

import de.haw.wpcgar.structure.Parameter;

/**
 * Parameter for 3rd dimension map.
 * @author Adrian Helberg
 */
public class HeightMap extends Parameter {

    public HeightMap(String seed) {
        super("heightmap", seed);
    }

    @Override
    protected double value(double x, double y, double z) {
        return 0.0;
    }
}
