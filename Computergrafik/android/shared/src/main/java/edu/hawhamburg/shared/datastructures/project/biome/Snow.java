package de.haw.wpcgar.structure.biomes;

import de.haw.wpcgar.generator.WorldGenerator;
import de.haw.wpcgar.structure.Biome;
import de.haw.wpcgar.structure.params.HeightMap;
import edu.hawhamburg.shared.math.Vector;

public class Snow extends Biome {

    public Snow(WorldGenerator generator) {
        super(generator, new Vector(1, 1, 1, "snow"));
    }

    @Override
    public boolean check(double x, double y) {

        double height = getValue(HeightMap.class, x, y);

        return height > 0.95;

    }

    @Override
    public Vector getColor() {
        return color;
    }
}
