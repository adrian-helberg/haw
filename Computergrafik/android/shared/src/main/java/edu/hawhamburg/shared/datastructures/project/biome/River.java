package edu.hawhamburg.shared.datastructures.project.biome;

import android.graphics.Color;

import edu.hawhamburg.shared.datastructures.project.Biome;
import edu.hawhamburg.shared.datastructures.project.WorldGenerator;
import edu.hawhamburg.shared.datastructures.project.parameter.HeightMap;
import edu.hawhamburg.shared.datastructures.project.parameter.Rivers;
import edu.hawhamburg.shared.datastructures.project.parameter.Temperature;
import edu.hawhamburg.shared.math.Vector;

public class River extends Biome {

    private static final Vector COLOR_BLUE = new Vector(0. / 255, 0. / 255, 255. / 255, 1);

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
    public Vector getColor(double x, double y) {

        if (check(x, y)) {
            return COLOR_BLUE;
        }

        return null;
    }
}
