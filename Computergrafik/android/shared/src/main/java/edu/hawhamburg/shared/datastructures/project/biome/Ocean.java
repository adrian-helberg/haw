package edu.hawhamburg.shared.datastructures.project.biome;

import android.graphics.Color;

import edu.hawhamburg.shared.datastructures.project.Biome;
import edu.hawhamburg.shared.datastructures.project.Parameter;
import edu.hawhamburg.shared.datastructures.project.WorldGenerator;
import edu.hawhamburg.shared.math.Vector;

public class Ocean extends Biome {

    private static final Vector COLOR_BLUE = new Vector(0. / 255, 0. / 255, 255. / 255, 1);

    public Ocean(WorldGenerator generator) {
        super(generator);
    }

    @Override
    public boolean check(double x, double y) {

        Parameter h = generator.getEnvironment().getParameter("heightmap");

        double height = h.getValue(x, y);

        return height < 0.13;

    }

    @Override
    public Vector getColor(double x, double y) {

        if (check(x, y)) {
            return COLOR_BLUE;
        }

        return null;
    }
}
