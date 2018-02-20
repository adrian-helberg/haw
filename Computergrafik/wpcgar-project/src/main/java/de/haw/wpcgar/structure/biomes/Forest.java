package de.haw.wpcgar.structure.biomes;

import de.haw.wpcgar.generator.WorldGenerator;
import de.haw.wpcgar.structure.Biome;
import de.haw.wpcgar.structure.params.HeightMap;

import java.awt.*;

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

        if (height > 0.1 && !(height > 0.75))
        {
            return true;
        }

        return false;
    }

    @Override
    public Color getColor(double x, double y)
    {
        if (check(x, y))
        {
            return Color.green.darker().darker();
        }

        return null;
    }
}
