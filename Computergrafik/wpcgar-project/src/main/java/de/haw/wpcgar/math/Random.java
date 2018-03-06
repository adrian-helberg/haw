package de.haw.wpcgar.math;

/**
 * Randomizer.
 * Contains random specific stuff.
 *
 * @author Adrian Helberg
 */
public class Random {

    private long _seed = System.currentTimeMillis();

    /**
     * Initializes a new instance of the random number generator using
     * a specified seed.
     *
     * @param seed The seed to use
     */
    Random(long seed) {
        this._seed = seed;
    }

    /**
     * Initializes a new instance of the random number generator using
     * System.currentTimeMillis() as seed.
     */
    public Random() {}

    /**
     * Returns a random value as long.
     * min: -9223372027538671720, max: 9223372026111068325
     * @return Random value
     */
    private long randomLong() {
        _seed ^= (_seed << 21);
        _seed ^= (_seed >>> 35);
        _seed ^= (_seed << 4);
        return _seed;
    }

    /**
     * Returns a random value as integer.
     * min: -2147483646, max: 2147483631
     * @return Random value
     */
    int randomInt() {
        return (int) randomLong();
    }

    /**
     * Returns a random value as double.
     * min: -0.99_ , max: 0.99_
     * @return Random value
     */
    private double randomDouble() {
        return randomLong() / ((double) Long.MAX_VALUE - 1d);
    }

    /**
     * Returns a random character string with a specified length.
     *
     * @param length The length of the generated string
     * @return Random character string
     */
    public String randomCharacterString(int length) {
        StringBuilder s = new StringBuilder();

        for (int i = 0; i < length / 2; i++) {
            s.append((char) ('a' + Math.abs(randomDouble()) * 26d));
            s.append((char) ('A' + Math.abs(randomDouble()) * 26d));
        }

        return s.toString();
    }
}
