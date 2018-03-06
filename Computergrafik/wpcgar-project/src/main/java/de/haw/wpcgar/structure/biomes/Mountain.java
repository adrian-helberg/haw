package de.haw.wpcgar.structure.biomes;

import de.haw.wpcgar.generator.WorldGenerator;
import de.haw.wpcgar.structure.Biome;
import de.haw.wpcgar.structure.params.HeightMap;
import edu.hawhamburg.shared.math.Vector;

import java.awt.*;

public class Mountain extends Biome {

    public Mountain(WorldGenerator generator) {
        super(generator, new Vector(0.75, 0.75, 0.75, "mountain"));
    }

    @Override
    public boolean check(double x, double y) {

        HeightMap h = generator.getEnvironment().getParameter(HeightMap.class);
        double height = h.getValue(x, y);

        return height > 0.7;

    }

    @Override
    public Vector getColor() {
        return color;
    }
}
