package de.haw.wpcgar.structure.biomes;

import de.haw.wpcgar.generator.WorldGenerator;
import de.haw.wpcgar.structure.Biome;
import de.haw.wpcgar.structure.params.HeightMap;
import edu.hawhamburg.shared.math.Vector;

public class Plain extends Biome {

    public Plain(WorldGenerator generator) {
        super(generator, new Vector(0, 1, 0, "plain"));
    }

    @Override
    public boolean check(double x, double y) {

        double height = getValue(HeightMap.class, x, y);

        return height <= 0.7;

    }

    @Override
    public Vector getColor() {
        return color;
    }
}
