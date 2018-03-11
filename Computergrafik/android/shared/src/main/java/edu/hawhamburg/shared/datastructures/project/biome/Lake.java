package edu.hawhamburg.shared.datastructures.project.biome;

import android.graphics.Color;

import edu.hawhamburg.shared.datastructures.project.Biome;
import edu.hawhamburg.shared.datastructures.project.WorldGenerator;
import edu.hawhamburg.shared.datastructures.project.parameter.HeightMap;
import edu.hawhamburg.shared.datastructures.project.parameter.Rivers;
import edu.hawhamburg.shared.datastructures.project.parameter.Temperature;
import edu.hawhamburg.shared.math.Vector;

public class Lake extends Biome {

    private static final Vector COLOR_BLUE = new Vector(0. / 255, 0. / 255, 255. / 255, 1);

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
    public Vector getColor(double x, double y) {

        //Parameter h = generator.getEnvironment().getParameter("heightmap");
        //double height = h.getValue(x, y);

        //int greenAndBlue = Helper.getAlignedValueFromParameter(height, 180, 255, 0.1, 0.75);

        if (check(x, y)) {
            //return new Color(0, greenAndBlue, greenAndBlue);
            return COLOR_BLUE;
        }

        return null;
    }
}
