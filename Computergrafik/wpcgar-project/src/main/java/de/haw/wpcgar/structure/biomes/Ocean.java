package de.haw.wpcgar.structure.biomes;

import de.haw.wpcgar.generator.WorldGenerator;
import de.haw.wpcgar.structure.Biome;
import de.haw.wpcgar.structure.Parameter;
import edu.hawhamburg.shared.math.Vector;

import java.awt.*;

public class Ocean extends Biome {
    public Ocean(WorldGenerator generator) {
        super(generator, new Vector(0, 0, 1, "ocean"));
    }

    @Override
    public boolean check(double x, double y) {

        Parameter h = generator.getEnvironment().getParameter("heightmap");

        double height = h.getValue(x, y);

        return height < 0.13;

    }

    @Override
    public Vector getColor() {
        return color;
    }
}
