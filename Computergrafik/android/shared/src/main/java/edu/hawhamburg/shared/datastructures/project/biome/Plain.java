package edu.hawhamburg.shared.datastructures.project.biome;

import android.graphics.Color;

import edu.hawhamburg.shared.datastructures.project.Biome;
import edu.hawhamburg.shared.datastructures.project.WorldGenerator;
import edu.hawhamburg.shared.datastructures.project.parameter.HeightMap;
import edu.hawhamburg.shared.math.Vector;

public class Plain extends Biome {

    private static final Vector COLOR_GREEN = new Vector(255. / 255, 255. / 255, 0. / 255, 1);

    public Plain(WorldGenerator generator) {
        super(generator);
    }

    @Override
    public boolean check(double x, double y) {

        double height = getValue(HeightMap.class, x, y);

        if(height <= 0.7)
        {
            return true;
        }

        return false;
    }

    @Override
    public Vector getColor(double x, double y) {

        if (check(x, y))
        {
            return COLOR_GREEN;
        }

        return null;
    }
}
