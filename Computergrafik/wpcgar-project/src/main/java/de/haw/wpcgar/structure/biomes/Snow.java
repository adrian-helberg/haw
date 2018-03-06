package de.haw.wpcgar.structure.biomes;

import de.haw.wpcgar.generator.WorldGenerator;
import de.haw.wpcgar.structure.Biome;
import de.haw.wpcgar.structure.params.HeightMap;
import de.haw.wpcgar.structure.params.Snowy;
import de.haw.wpcgar.structure.params.Temperature;
import edu.hawhamburg.shared.math.Vector;

import java.awt.*;

public class Snow extends Biome {

    public Snow(WorldGenerator generator) {
        super(generator, new Vector(1, 1, 1, "snow"));
    }

    @Override
    public boolean check(double x, double y) {

        double height = getValue(HeightMap.class, x, y);

        return height > 0.94;

    }

    @Override
    public Vector getColor() {
        return color;
    }
}
