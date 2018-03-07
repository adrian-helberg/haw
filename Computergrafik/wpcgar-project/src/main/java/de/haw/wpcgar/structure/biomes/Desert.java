package de.haw.wpcgar.structure.biomes;

import de.haw.wpcgar.generator.WorldGenerator;
import de.haw.wpcgar.structure.Biome;
import de.haw.wpcgar.structure.params.HeightMap;
import de.haw.wpcgar.structure.params.Temperature;
import edu.hawhamburg.shared.math.Vector;

public class Desert extends Biome {

    public Desert(WorldGenerator generator) {
        super(generator, new Vector(1, 1, 0, "desert"));
    }

    @Override
    public boolean check(double x, double y) {

        double height = getValue(HeightMap.class, x, y);
        double temperature = getValue(Temperature.class, x, y);

        if(temperature > 70 && height < 0.7)
        {
            return true;
        }

        return false;
    }

    @Override
    public Vector getColor() {
        return color;
    }
}
