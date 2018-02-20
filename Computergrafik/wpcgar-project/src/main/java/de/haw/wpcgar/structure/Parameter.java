package de.haw.wpcgar.structure;

import de.haw.wpcgar.math.PerlinNoise;

/**
 * Parameter container.
 * Contains named parameter with noise and value.
 * @author Adrian Helberg
 */
public abstract class Parameter {
    protected final String      name;
    protected final PerlinNoise noise;
    protected double            value = -1.0;

    public Parameter(String name, String seed, int salt)
    {
        this.name = name;
        this.noise = new PerlinNoise(seed.hashCode() + salt);
    }

    public String getName()
    {
        return name;
    }

    public PerlinNoise getNoise()
    {
        return noise;
    }

    public double getValue(double x, double y)
    {
        return getValue(x, y, 0.0);
    }

    public double getValue(double x, double y, double z)
    {
        if(this.value == -1.0)
        {
            double value = value(x, y, z);
            this.value = value;

            return value;
        }

        return this.value;
    }

    public void reset()
    {
        this.value = -1.0;
    }

    protected abstract double value(double x, double y, double z);
}
