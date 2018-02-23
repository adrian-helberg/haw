package de.haw.wpcgar.structure.biomes;

import de.haw.wpcgar.generator.WorldGenerator;
import de.haw.wpcgar.math.Helper;
import de.haw.wpcgar.structure.Biome;
import de.haw.wpcgar.structure.Parameter;
import de.haw.wpcgar.structure.params.HeightMap;

import java.awt.*;
import java.util.Random;

/**
 * Forest biome.
 * @author Adrian Helberg
 */
public class Forest extends Biome {

    public Forest(WorldGenerator generator)
    {
        super(generator);
    }

    @Override
    public boolean check(double x, double y)
    {
        double height = getValue(HeightMap.class, x, y);

        if (height > 0.3 && height < 0.75)
        {
            return true;
        }

        return false;
    }

    /**
     * Returns the color of the forest for corresponding coordinates
     * Color is in between rgb(0,100,0) and rgb(0,200,0)
     * @param x Pixel x coordinate
     * @param y Pixel y coordinate
     * @return Color of biome
     */
    @Override
    public Color getColor(double x, double y)
    {
        if (check(x, y))
        {
            // Get height from heightmap to modify the color to corresponding height
            Parameter p = generator.getEnvironment().getParameter("heightmap");
            double height = p.getValue(x, y);

            // Get the relative percentage of range hit by the height
            int value = Helper.getAlignedValueFromHeight(height, 100, 200, 0.1, 0.75);

            return new Color(0, value, 0);
        }

        return null;
    }
}
