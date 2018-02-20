package de.haw.wpcgar;

/**
 * Configuration class.
 * Contains parameters for world generation.
 * @author Adrian Helberg
 */
public class Config {
    // Distance of view
    private static double ZOOM = 1.0;
    private static int WIDTH = 800;
    private static int HEIGHT = 800;
    private static double X = 0.0;
    private static double Y = 0.0;

    // Window dimensions
    public final static int WINDOW_WIDTH = 800;
    public final static int WINDOW_HEIGHT = 800;

    // Density through height map
    private static boolean SHOW_DENSITY = false;
    private static String DENSITY_NAME = "heightmap";

    // Seed
    private static boolean USE_DEFAULT_SEED = true;
    private static String DEFAULT_SEED = "";
}
