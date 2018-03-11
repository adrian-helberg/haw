package edu.hawhamburg.shared.datastructures.project;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Main world generator.
 * Contains seed, environment and biomes.
 * @author Adrian Helberg
 */
public class WorldGenerator {
    private final Environment environment;
    private final Map<Class<? extends Biome>, Biome> biomes = new HashMap<>();
    private final List<Class<? extends Biome>> priority = new ArrayList<>();

    public WorldGenerator(String seed) {
        environment = new Environment(seed);
    }

    public <T extends Biome> void registerBiome(Class<T> c) {
        try {
            Constructor<T> constructor = c.getConstructor(WorldGenerator.class);
            T biome = constructor.newInstance(this);
            registerBiome(biome);
        }
        catch (Exception e)
        {
           e.printStackTrace();
        }
    }

    public <T extends Biome> void registerBiome(T biome) {
        biomes.put(biome.getClass(), biome);
        priority.add(biome.getClass());
    }

    public Biome getBiome(double x, double y) {
        Biome result = null;

        for (Class<? extends Biome> c : priority) {
            Biome biome = biomes.get(c);

            if (result == null) {
                if (biome.check(x, y)) {
                    result = biome;
                }
            }
        }

        return result;
    }

    public Map<Class<? extends Biome>, Biome> getBiomes()
    {
        return biomes;
    }

    public Environment getEnvironment() {
        return environment;
    }
}
