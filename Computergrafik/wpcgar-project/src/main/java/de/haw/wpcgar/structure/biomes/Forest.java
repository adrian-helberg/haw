package de.haw.wpcgar.structure.biomes;

import de.haw.wpcgar.generator.WorldGenerator;
import de.haw.wpcgar.math.Helper;
import de.haw.wpcgar.structure.Biome;
import de.haw.wpcgar.structure.Parameter;
import de.haw.wpcgar.structure.params.HeightMap;
import de.haw.wpcgar.structure.params.Temperature;

import java.awt.*;

/**
 * Forest biome.
 * @author Adrian Helberg
 *
 * The forest growth is determined by the parameters height and temperature.
 * The greater the value of the height, the more likely the mixing ratio
 * of deciduous to coniferous forest is in the direction of coniferous forest.
 * The greater the value of the temperature, the denser the forest
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
        double temperature = getValue(Temperature.class, x, y);

        return height > 0.1 && height < 0.75 && temperature > 35 && temperature < 50;

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
            //Parameter h = generator.getEnvironment().getParameter("heightmap");

            //double height = h.getValue(x, y);

            // Get the relative percentage of range hit by the height
            //int green = Helper.getAlignedValueFromParameter(height, 100, 200, 0.1, 0.75);

            // Always an instance of color green
            //return new Color(0, green, 0);
            return Color.green;
        }

        return null;
    }
}
