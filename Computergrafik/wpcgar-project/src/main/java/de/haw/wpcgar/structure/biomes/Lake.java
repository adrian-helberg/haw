package de.haw.wpcgar.structure.biomes;

import de.haw.wpcgar.generator.WorldGenerator;
import de.haw.wpcgar.structure.Biome;
import de.haw.wpcgar.structure.params.HeightMap;
import de.haw.wpcgar.structure.params.Rivers;
import de.haw.wpcgar.structure.params.Temperature;

import java.awt.*;

public class Lake extends Biome {

    public Lake(WorldGenerator generator) {
        super(generator);
    }

    @Override
    public boolean check(double x, double y) {

        double height = getValue(HeightMap.class, x, y);
        double rivers = getValue(Rivers.class, x, y);
        double temperature = getValue(Temperature.class, x, y);

        return rivers < 0.5 && temperature < 55 && height < 0.75;
    }

    @Override
    public Color getColor(double x, double y) {

        //Parameter h = generator.getEnvironment().getParameter("heightmap");
        //double height = h.getValue(x, y);

        //int greenAndBlue = Helper.getAlignedValueFromParameter(height, 180, 255, 0.1, 0.75);

        if (check(x, y)) {
            //return new Color(0, greenAndBlue, greenAndBlue);
            return Color.cyan;
        }

        return null;
    }
}
