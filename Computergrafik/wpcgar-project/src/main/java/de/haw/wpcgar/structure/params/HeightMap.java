package de.haw.wpcgar.structure.params;

import de.haw.wpcgar.structure.Parameter;

/**
 * Parameter for 3rd dimension map.
 * @author Adrian Helberg
 */
public class HeightMap extends Parameter
{

    public HeightMap(String seed)
    {
        super("heightmap", seed);
    }

    @Override
    public double value(double x, double y, double z)
    {
        double result = 0.0;

        result += noise.fBm(
                0.004 * x,
                0.004 * y,
                0.004 * z,
                50,
                2.2341,
                1.422561
        ) + 0.5;

        return result;
    }

}
