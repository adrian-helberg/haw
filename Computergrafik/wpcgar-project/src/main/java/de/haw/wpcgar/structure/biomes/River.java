package de.haw.wpcgar.structure.biomes;

import de.haw.wpcgar.generator.WorldGenerator;
import de.haw.wpcgar.structure.Biome;
import de.haw.wpcgar.structure.params.HeightMap;
import de.haw.wpcgar.structure.params.Rivers;
import de.haw.wpcgar.structure.params.Temperature;

import java.awt.*;

public class River extends Biome {

    public River(WorldGenerator generator) {
        super(generator);
    }

    @Override
    public boolean check(double x, double y) {

        double height = getValue(HeightMap.class, x, y);
        double rivers = getValue(Rivers.class, x, y);
        double temperature = getValue(Temperature.class, x, y);

        return rivers < 0.2 && temperature < 55 && !(height > 0.75 && temperature < 55);

    }

    @Override
    public Color getColor(double x, double y) {

        if (check(x, y)) {
            return Color.cyan;
        }

        return Color.black;
    }
}
