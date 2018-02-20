package de.haw.wpcgar.math;

/**
 * Perlin Noise Algorithm.
 * @author Adrian Helberg
 */
public class PerlinNoise {
    private final int[] permutations;
    private final int[] noiseTable;

    public PerlinNoise(int seed) {
        Random r = new Random(seed);
        permutations = new int[512];
        noiseTable = new int[256];

        for (int i = 0; i < 256; i++) {
                noiseTable[i] = i;
        }

        for (int i = 0; i < 256; i++) {
            int j = r.randomInt() % 256;
            j = (j < 0) ? -j : j;

            int swap = noiseTable[i];
            noiseTable[i] = noiseTable[j];
            noiseTable[j] = swap;
        }

        for (int i = 0; i < 256; i++) {
            permutations[i] = permutations[i + 256] = noiseTable[i];
        }
    }

    public double noise(double x, double y, double z) {
        int _x = (int) Math.floor(x) & 255;
        int _y = (int) Math.floor(y) & 255;
        int _z = (int) Math.floor(z) & 255;

        x -= Math.floor(x);
        y -= Math.floor(y);
        z -= Math.floor(z);

        double u = fade(x), v = fade(y), w = fade(z);

        int A = permutations[_x] + _y, AA = permutations[A] + _z, AB = permutations[(A + 1)] + _z,
                B = permutations[(_x + 1)] + _y, BA = permutations[B] + _z, BB = permutations[(B + 1)] + _z;

        return lerp(w, lerp(v, lerp(u, grad(permutations[AA], x, y, z),
                grad(permutations[BA], x - 1, y, z)),
                lerp(u, grad(permutations[AB], x, y - 1, z),
                        grad(permutations[BB], x - 1, y - 1, z))),
                lerp(v, lerp(u, grad(permutations[(AA + 1)], x, y, z - 1),
                        grad(permutations[(BA + 1)], x - 1, y, z - 1)),
                        lerp(u, grad(permutations[(AB + 1)], x, y - 1, z - 1),
                                grad(permutations[(BB + 1)], x - 1, y - 1, z - 1))));
    }

    private static double fade(double t) {
        return t * t * t * (t * (t * 6 - 15) + 10);
    }

    private static double lerp(double t, double a, double b) {
        return a + t * (b - a);
    }

    private static double grad(int hash, double x, double y, double z) {
        int h = hash & 15;
        double u = h < 8 ? x : y, v = h < 4 ? y : h == 12 || h == 14 ? x : z;
        return ((h & 1) == 0 ? u : -u) + ((h & 2) == 0 ? v : -v);
    }

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
