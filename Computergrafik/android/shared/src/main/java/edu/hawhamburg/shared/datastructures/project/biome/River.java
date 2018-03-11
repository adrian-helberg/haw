package de.haw.wpcgar.structure.biomes;

import de.haw.wpcgar.generator.WorldGenerator;
import de.haw.wpcgar.structure.Biome;
import de.haw.wpcgar.structure.params.HeightMap;
import de.haw.wpcgar.structure.params.Rivers;
import de.haw.wpcgar.structure.params.Temperature;
import edu.hawhamburg.shared.math.Vector;

public class River extends Biome {

    public River(WorldGenerator generator) {
        super(generator, new Vector(0, 0.1, 1, "river"));
    }

    @Override
    public boolean check(double x, double y) {

        double height = getValue(HeightMap.class, x, y);
        double rivers = getValue(Rivers.class, x, y);
        double temperature = getValue(Temperature.class, x, y);

        return rivers < 0.2 && height > 0.13 && height < 0.6 && temperature < 55;

    }

    @Override
    public Vector getColor() {
        return color;
    }
}
