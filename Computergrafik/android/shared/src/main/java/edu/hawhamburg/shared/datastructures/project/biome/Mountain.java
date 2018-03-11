package edu.hawhamburg.shared.datastructures.project.biome;

import android.graphics.Color;

import edu.hawhamburg.shared.datastructures.project.Biome;
import edu.hawhamburg.shared.datastructures.project.WorldGenerator;
import edu.hawhamburg.shared.datastructures.project.parameter.HeightMap;
import edu.hawhamburg.shared.math.Vector;

public class Mountain extends Biome {

    private static final Vector COLOR_GRAY = new Vector(128. / 255, 128. / 255, 128. / 255, 1);

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
    public Vector getColor(double x, double y) {
        if (check(x, y)) {
            return COLOR_GRAY;
        }

        return null;
    }
}
