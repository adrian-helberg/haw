package de.haw.wpcgar.structure;

import de.haw.wpcgar.generator.WorldGenerator;

import java.awt.*;

/**
 * Abstract biome class.
 * Contains attributes natural biomes can inherit from.
 * @author Adrian Helberg
 */
public abstract class Biome {
    protected final WorldGenerator generator;

    public Biome(WorldGenerator generator) {
        this.generator = generator;
    }

    protected <T extends Biome> T getBiome(Class<T> c)
    {
        return (T) generator.getBiomes().get(c);
    }

    public abstract boolean check(double x, double y);

    public abstract Color getColor(double x, double y);

    public abstract String getName();
}