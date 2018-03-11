package edu.hawhamburg.shared.math;

/**
 * Perlin Noise Algorithm.
 * @author Adrian Helberg
 */
public class PerlinNoise {
    private final int[] _noisePermutations, _noiseTable;

    /**
     * Creates an instance with random noise permutations between 0 and 255.
     * @param seed Seed string hashcode.
     */
    public PerlinNoise(int seed) {
        Random rand = new Random(seed);
        // Doubled permutation to avoid overflow
        _noisePermutations = new int[512];
        _noiseTable = new int[256];

        for (int i = 0; i < 256; i++)
            _noiseTable[i] = i;

        for (int i = 0; i < 256; i++) {
            // Random integer between -255 and 255
            int j = rand.randomInt() % 256;
            // Absolute value
            j = (j < 0) ? -j : j;

            // Swap a random entry from noise table with entry of current index
            int swap = _noiseTable[i];
            _noiseTable[i] = _noiseTable[j];
            _noiseTable[j] = swap;
        }

        for (int i = 0; i < 256; i++) {
            _noisePermutations[i] = _noisePermutations[i + 256] = _noiseTable[i];
        }
    }

    /**
     * Noise function that computes a noise value for given pixel
     * @param x Pixel x coordinate
     * @param y Pixel y coordinate
     * @param z Pixel z coordinate
     * @return noise value
     */
    public double noise(double x, double y, double z) {
        // Calculate the unit cube that the given point is located in
        // Bind the values to the range of 0 to 255
        int X = (int) Math.floor(x) & 255;
        int Y = (int) Math.floor(y) & 255;
        int Z = (int) Math.floor(z) & 255;

        x -= Math.floor(x);
        y -= Math.floor(y);
        z -= Math.floor(z);

        // Ease values with fade function
        double u = fade(x);
        double v = fade(y);
        double w = fade(z);

        // Get a unique value for every coordinate through hash
        int A = _noisePermutations[X] + Y;
        int AA = _noisePermutations[A] + Z;
        int AB = _noisePermutations[(A + 1)] + Z;
        int B = _noisePermutations[(X + 1)] + Y;
        int BA = _noisePermutations[B] + Z;
        int BB = _noisePermutations[(B + 1)] + Z;

        // Lerp all together as a sort of weighted average based on the faded (u,v,w) values
        return lerp(w, lerp(v, lerp(u, grad(_noisePermutations[AA], x, y, z),
                grad(_noisePermutations[BA], x - 1, y, z)),
                lerp(u, grad(_noisePermutations[AB], x, y - 1, z),
                        grad(_noisePermutations[BB], x - 1, y - 1, z))),
                lerp(v, lerp(u, grad(_noisePermutations[(AA + 1)], x, y, z - 1),
                        grad(_noisePermutations[(BA + 1)], x - 1, y, z - 1)),
                        lerp(u, grad(_noisePermutations[(AB + 1)], x, y - 1, z - 1),
                                grad(_noisePermutations[(BB + 1)], x - 1, y - 1, z - 1))));
    }

    /**
     * Fade function that eases coordinate values so that they ease towards integral values.
     * Ease function by Ken Perlin: 6 * t^5 - 15 * t^4 + 10 * t^3
     * @param t T
     * @return eased value
     */
    private static double fade(double t) {
        return t * t * t * (t * (t * 6 - 15) + 10);
    }

    /**
     * Linear interpolation function
     * @param t value 1
     * @param a value 2
     * @param b Alpha value
     * @return Linear interpolated value
     */
    private static double lerp(double t, double a, double b) {
        return a + t * (b - a);
    }

    /**
     * Gradient function that calculates the dot product of a randomly selected gradient vector
     * and the location vectors.
     *
     * hash & 0xF ->
     *      case 0x0: return  x + y;
     *      case 0x1: return -x + y;
     *      case 0x2: return  x - y;
     *      case 0x3: return -x - y;
     *      case 0x4: return  x + z;
     *      case 0x5: return -x + z;
     *      case 0x6: return  x - z;
     *      case 0x7: return -x - z;
     *      case 0x8: return  y + z;
     *      case 0x9: return -y + z;
     *      case 0xA: return  y - z;
     *      case 0xB: return -y - z;
     *      case 0xC: return  y + x;
     *      case 0xD: return -y + z;
     *      case 0xE: return  y - x;
     *      case 0xF: return -y - z;
     *
     * @param hash Hashcode
     * @param x Pixel x coordinate
     * @param y Pixel y coordinate
     * @param z Pixel z coordinate
     * @return value
     */
    private static double grad(int hash, double x, double y, double z) {
        // Take the first 4 bits of the hashed value (15 == 0b1111)
        int h = hash & 15;
        // If most significant bit is 0, set u = x. y otherwise.
        double u = h < 8 ? x : y;
        // If first and second significant bit are 0, set v = y
        // If first and second significant bit are 1, set v = x
        // If first and second significant bit are not equal, set v = z
        double v = h < 4 ? y : h == 12 || h == 14 ? x : z;
        // Use last 2 bits to decide if u and v are positive or negative
        // Return addition of absolute values of u and v
        return ((h & 1) == 0 ? u : -u) + ((h & 2) == 0 ? v : -v);
    }

    /**
     * Fractional Brownian motion
     * Modifies a noise value from Perlin Noise algorithm and returns it.
     * @param x Pixel x coordinate
     * @param y Pixel y coordinate
     * @param z Pixel z coordinate
     * @param octaves Iterations of noise
     * @param lacunarity Gap how fractals fill the space
     * @param h
     * @return
     */
    public double fBm(double x, double y, double z, int octaves, double lacunarity, double h) {
        double result = 0.0;

        for (int i = 0; i < octaves; i++) {
            result += noise(x, y, z) * Math.pow(lacunarity, -h * i);

            x *= lacunarity;
            y *= lacunarity;
            z *= lacunarity;
        }

        return result;
    }
}