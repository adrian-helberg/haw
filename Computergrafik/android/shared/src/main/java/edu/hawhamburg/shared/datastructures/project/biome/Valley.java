package de.haw.wpcgar.structure.biomes;

import de.haw.wpcgar.generator.WorldGenerator;
import de.haw.wpcgar.structure.Biome;
import de.haw.wpcgar.structure.params.HeightMap;
import de.haw.wpcgar.structure.params.Population;
import de.haw.wpcgar.structure.params.Temperature;
import edu.hawhamburg.shared.math.Vector;

public class Valley extends Biome {

    public Valley(WorldGenerator generator) {
        super(generator, new Vector(0.04, 0.04, 0.04, "valley"));
    }

    @Override
    public boolean check(double x, double y) {

        double height = getValue(HeightMap.class, x, y);
        double temperature = getValue(Temperature.class, x, y);
        double population = getValue(Population.class, x, y);

        System.out.print(temperature + ", ");

        return false;
    }

    @Override
    public Vector getColor() { return color; }
}
