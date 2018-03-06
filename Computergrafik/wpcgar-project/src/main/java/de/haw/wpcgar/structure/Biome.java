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

    public Biome(WorldGenerator generator)
    {
        this.generator = generator;
    }

    public <T extends Parameter> double getValue(Class<T> c, double x, double y)
    {
        return getValue(c, x, y, 0.0);
    }

    protected <T extends Parameter> double getValue(Class<T> c, double x, double y, double z)
    {
        return getParameter(c).getValue(x, y, z);
    }

    private <T extends Parameter> T getParameter(Class<T> c)
    {
        return generator.getEnvironment().getParameter(c);
    }

    @SuppressWarnings("unchecked")
    protected <T extends Biome> T getBiome(Class<T> c)
    {
        return (T) generator.getBiomes().get(c);
    }

    public abstract boolean check(double x, double y);

    public abstract Color getColor(double x, double y);
}