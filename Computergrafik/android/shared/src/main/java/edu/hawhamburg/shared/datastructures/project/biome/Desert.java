package edu.hawhamburg.shared.datastructures.project.biome;

import android.graphics.Color;

import edu.hawhamburg.shared.datastructures.project.Biome;
import edu.hawhamburg.shared.datastructures.project.WorldGenerator;
import edu.hawhamburg.shared.datastructures.project.parameter.HeightMap;
import edu.hawhamburg.shared.datastructures.project.parameter.Temperature;
import edu.hawhamburg.shared.math.Vector;

public class Desert extends Biome {
    private static final Vector COLOR_Yellow = new Vector(255. / 255, 200. / 255, 0. / 255, 1);

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
    public Vector getColor(double x, double y) {

        if(check(x, y))
        {
            return COLOR_Yellow;
        }

        return null;
    }
}
