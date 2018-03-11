package edu.hawhamburg.shared.datastructures.project.biome;

import android.graphics.Color;

import edu.hawhamburg.shared.datastructures.project.Biome;
import edu.hawhamburg.shared.datastructures.project.WorldGenerator;
import edu.hawhamburg.shared.datastructures.project.parameter.HeightMap;
import edu.hawhamburg.shared.datastructures.project.parameter.Snowy;
import edu.hawhamburg.shared.datastructures.project.parameter.Temperature;
import edu.hawhamburg.shared.math.Vector;

public class Snow extends Biome {

    private static final Vector COLOR_WHITE = new Vector(255. / 255, 255. / 255, 255. / 255, 1);

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
    public Vector getColor(double x, double y) {

        if(check(x, y))
        {
            return COLOR_WHITE;
        }

        return null;
    }
}
