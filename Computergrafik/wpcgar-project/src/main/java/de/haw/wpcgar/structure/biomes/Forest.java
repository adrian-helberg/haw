package de.haw.wpcgar.structure.biomes;

import de.haw.wpcgar.generator.WorldGenerator;
import de.haw.wpcgar.structure.Biome;

import java.awt.*;

/**
 * Forest biome.
 * @author Adrian Helberg
 */
public class Forest extends Biome {

    public Forest(WorldGenerator generator) {
        super(generator);
    }

    // TODO
    @Override
    public boolean check(double x, double y) {
        return true;
    }

    @Override
    public Color getColor(double x, double y) {
        if (check(x, y)) {
            return Color.green.darker().darker();
        }

        return null;
    }

    public String getName() {
        return "Forest";
    }
}
