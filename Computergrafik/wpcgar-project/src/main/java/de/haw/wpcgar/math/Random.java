package de.haw.wpcgar.math;

/**
 * Randomizer.
 * Contains random specific stuff.
 * @author Adrian Helberg
 */
public class Random {

    private long seed = System.currentTimeMillis();

    public Random() {

    }

    /**
     * getRandomString uses current system time to generate a random string
     * @param length length of the returning random string
     * @return random string
     */
    public String getRandomString(int length) {
        StringBuilder sb = new StringBuilder();

        // Lets randomize a bit
        for (int i = 0; i < length / 2; i++) {
            sb.append((char) ('a' + Math.abs(randomDouble()) * 26d));
            sb.append((char) ('A' + Math.abs(randomDouble()) * 26d));
        }

        return sb.toString();
    }

    private double randomDouble() {
        return randomLong() / ((double) Long.MAX_VALUE - 1d);
    }

    // Use bitwise exclusive OR to bit shift seed character string
    private long randomLong() {
        seed ^= (seed << 21); // Signed left shift
        seed ^= (seed >>> 35); // Unsigned right shift
        seed ^= (seed << 4); // Signed left shift
        return seed;
    }
}
