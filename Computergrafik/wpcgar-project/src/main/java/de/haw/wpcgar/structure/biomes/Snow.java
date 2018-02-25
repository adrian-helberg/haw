package de.haw.wpcgar.structure.biomes;

import de.haw.wpcgar.generator.WorldGenerator;
import de.haw.wpcgar.structure.Biome;
import de.haw.wpcgar.structure.params.HeightMap;
import de.haw.wpcgar.structure.params.Snowy;
import de.haw.wpcgar.structure.params.Temperature;

import java.awt.*;

public class Snow extends Biome {

    public Snow(WorldGenerator generator) {
        super(generator);
    }

    @Override
    public boolean check(double x, double y) {

        double height = getValue(HeightMap.class, x, y);
        double temperature = getValue(Temperature.class, x, y);
        double snow = getValue(Snowy.class, x, y);

        if(height > 0.85 && temperature < 55 && snow > 0.5)
        {
            return true;
        }

        return false;
    }

    @Override
    public Color getColor(double x, double y) {

        if(check(x, y))
        {
            return Color.white;
        }

        return null;
    }
}
