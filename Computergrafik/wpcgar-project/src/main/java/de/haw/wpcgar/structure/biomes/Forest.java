package de.haw.wpcgar.structure.biomes;

import de.haw.wpcgar.generator.MeshGenerator;
import de.haw.wpcgar.generator.WorldGenerator;
import de.haw.wpcgar.structure.Biome;
import de.haw.wpcgar.structure.params.HeightMap;
import de.haw.wpcgar.structure.params.Temperature;
import edu.hawhamburg.shared.math.Vector;

import java.io.IOException;

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
        super(generator, new Vector(0, 0.5, 0, "forest"));
    }

    @Override
    public boolean check(double x, double y)
    {
        double height = getValue(HeightMap.class, x, y);
        double temperature = getValue(Temperature.class, x, y);

        return height > 0.1 && height < 0.75 && temperature > 35 && temperature < 50;
    }

    @Override
    public Vector getColor()
    {
        return color;
    }
}
