package edu.hawhamburg.shared.datastructures.project.biome;

import android.graphics.Color;

import edu.hawhamburg.shared.datastructures.project.Biome;
import edu.hawhamburg.shared.datastructures.project.WorldGenerator;
import edu.hawhamburg.shared.datastructures.project.parameter.HeightMap;
import edu.hawhamburg.shared.datastructures.project.parameter.Temperature;
import edu.hawhamburg.shared.math.Vector;

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

    private static final Vector COLOR_GREEN = new Vector(6, 163, 63, 1);

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
    public Vector getColor(double x, double y)
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
            return COLOR_GREEN;
        }

        return null;
    }
}
