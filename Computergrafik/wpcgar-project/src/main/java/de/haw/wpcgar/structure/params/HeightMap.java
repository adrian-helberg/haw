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
        super("heightmap", seed, 123244);
    }

    @Override
    public double value(double x, double y, double z)
    {
        double result = 0.0;

        result += noise.fBm(0.0009 * x, 0.0009 * y, 0.0009 * z, 50, 2.2341,
                1.422561) + 0.4;

        return result;
    }

}
