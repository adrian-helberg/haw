package de.haw.wpcgar.structure;

import de.haw.wpcgar.math.PerlinNoise;

/**
 * Parameter container.
 * Contains named parameter with noise and value.
 * @author Adrian Helberg.
 */
public abstract class Parameter {
    // Name of parameter to call
    private final String  name;
    // Perlin noise instance
    protected final PerlinNoise noise;
    // Actual value representing the parameter
    private double value = -1.0;

    /**
     * Creates an instance with own noise.
     * @param name Name of the parameter.
     * @param seed Seed string.
     */
    public Parameter(String name, String seed)
    {
        this.name = name;
        this.noise = new PerlinNoise(seed.hashCode());
    }

    /**
     * Name getter.
     * @return name.
     */
    String getName()
    {
        return name;
    }

    /**
     * Return value in two dimensional space.
     * @param x Pixel x coordinate.
     * @param y Pixel y coordinate.
     * @return value.
     */
    public double getValue(double x, double y)
    {
        return getValue(x, y, 0.0);
    }

    /**
     * Return value for given pixel value of -1 stands for reset or unset value.
     * @param x Pixel x coordinate.
     * @param y Pixel y coordinate.
     * @param z Pixel z coordinate.
     * @return value.
     */
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

    /**
     * Resets value of parameter.
     */
    public void reset()
    {
        this.value = -1.0;
    }

    /**
     * Return value of parameter for given pixel.
     * @param x Pixel x coordinate.
     * @param y Pixel y coordinate.
     * @param z Pixel z coordinate.
     * @return value.
     */
    protected abstract double value(double x, double y, double z);
}
