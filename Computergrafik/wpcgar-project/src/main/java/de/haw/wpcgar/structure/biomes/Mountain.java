package de.haw.wpcgar.structure.biomes;

import de.haw.wpcgar.generator.WorldGenerator;
import de.haw.wpcgar.structure.Biome;
import de.haw.wpcgar.structure.params.HeightMap;

import java.awt.*;

public class Mountain extends Biome {

    public Mountain(WorldGenerator generator) {
        super(generator);
    }

    @Override
    public boolean check(double x, double y) {

        HeightMap h = generator.getEnvironment().getParameter(HeightMap.class);
        double height = h.getValue(x, y);

        if (height > 0.7) {
            return true;
        }

        return false;
    }

    @Override
    public Color getColor(double x, double y) {
        if (check(x, y)) {
            return Color.lightGray;
        }

        return null;
    }
}
