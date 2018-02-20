package de.haw.wpcgar;

/**
 * Configuration class.
 * Contains parameters for world generation.
 * @author Adrian Helberg
 */
public class Config {
    // Distance of view
    public static double ZOOM = 1.0;
    public static int WIDTH = 800;
    public static int HEIGHT = 800;
    public static double START_X = 0.0;
    public static double START_Y = 0.0;

    // Window dimensions
    public final static int WINDOW_WIDTH = 800;
    public final static int WINDOW_HEIGHT = 800;

    // Density through height map
    private static boolean SHOW_DENSITY = false;
    private static String DENSITY_NAME = "heightmap";

    // Seed
    public static boolean USE_DEFAULT_SEED = true;
    public static String DEFAULT_SEED = "";
}
