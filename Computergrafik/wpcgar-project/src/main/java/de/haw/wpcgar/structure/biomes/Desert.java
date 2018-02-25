package de.haw.wpcgar.structure.biomes;

import de.haw.wpcgar.generator.WorldGenerator;
import de.haw.wpcgar.structure.Biome;
import de.haw.wpcgar.structure.params.HeightMap;
import de.haw.wpcgar.structure.params.Temperature;

import java.awt.*;

public class Desert extends Biome {

    public Desert(WorldGenerator generator) {
        super(generator);
    }

    @Override
    public boolean check(double x, double y) {

        double height = getValue(HeightMap.class, x, y);
        double temperature = getValue(Temperature.class, x, y);

        if(temperature > 60 && height < 0.6)
        {
            return true;
        }

        return false;
    }

    @Override
    public Color getColor(double x, double y) {

        if(check(x, y))
        {
            return Color.yellow;
        }

        return null;
    }
}
