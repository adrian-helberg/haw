/**
 * Diese Datei gehört zum Android/Java Framework zur Veranstaltung "Computergrafik für
 * Augmented Reality" von Prof. Dr. Philipp Jenke an der Hochschule für Angewandte
 * Wissenschaften (HAW) Hamburg. Weder Teile der Software noch das Framework als Ganzes dürfen
 * ohne die Einwilligung von Philipp Jenke außerhalb von Forschungs- und Lehrprojekten an der HAW
 * Hamburg verwendet werden.
 * <p>
 * This file is part of the Android/Java framework for the course "Computer graphics for augmented
 * reality" by Prof. Dr. Philipp Jenke at the University of Applied (UAS) Sciences Hamburg. Neither
 * parts of the framework nor the complete framework may be used outside of research or student
 * projects at the UAS Hamburg.
 */
package edu.hawhamburg.shared.misc;

import edu.hawhamburg.shared.datastructures.mesh.Vertex;
import edu.hawhamburg.shared.datastructures.project.Biome;
import edu.hawhamburg.shared.datastructures.project.Parameter;
import edu.hawhamburg.shared.datastructures.project.WorldGenerator;
import edu.hawhamburg.shared.datastructures.project.parameter.HeightMap;
import edu.hawhamburg.shared.math.MeshGenerator;
import edu.hawhamburg.shared.math.Random;
import edu.hawhamburg.shared.math.Vector;
import edu.hawhamburg.shared.scenegraph.INode;
import edu.hawhamburg.shared.scenegraph.InnerNode;
import edu.hawhamburg.shared.scenegraph.TriangleMeshNode;
import platform.vuforia.VuforiaMarkerNode;

public class ProjectDefaultScene extends Scene {

    private VuforiaMarkerNode watcherMarker;

    private WorldGenerator generator;
    private static final Vector COLOR_Black = new Vector(0. / 255, 0. / 255, 0. / 255, 1);

    // World generation properties ------------------- //
    //private double zoom           = 5.0;
    private int resolutionX      = 800;
    private int resolutionY      = 800;
    private double width            = 0.5;
    private double length           = 0.5;
    private boolean useDefaultSeed  = true;
    private String defaultSeed      = "asdsadasdsadadwqe214324356324werxvc";
    // ---------------------------------------------- //

    // Storage for data points to render
    private Vertex[][] pointCloud = new Vertex[resolutionX][resolutionY];

    public ProjectDefaultScene() {
        super(100, INode.RenderMode.REGULAR);
    }

    @Override
    public void onSetup(InnerNode rootNode) {

        // marker
        watcherMarker = new VuforiaMarkerNode("elphi");
        rootNode.addChild(watcherMarker);

        String seed = defaultSeed;
        if (!useDefaultSeed) {
            seed = new Random().randomCharacterString(13);
        }

        generator = new WorldGenerator(seed);

        // Register parameter
        generator.getEnvironment().registerParameter(HeightMap.class);

        Vector pointPosition;
        Vertex point;
        Parameter height = generator.getEnvironment().getParameter(HeightMap.class);

        // Iterate over all "pixels"
        for (int x = -(resolutionX / 2); x < (resolutionX / 2); x++)
        {
            for (int y = -(resolutionY / 2); y < (resolutionY / 2); y++)
            {
                // Get the biom corresponding to current pixel coordinates
                Biome biome = generator.getBiome(x, y);
                Vector color = COLOR_Black;

                if (biome != null)
                {
                    // Get biome color
                    color = biome.getColor(x, y);
                }

                // Spread all points uniformly to the area (resolutionX * resolutionY)
                // Use floating division instead of integer division in case if 0 -> double cast
                pointPosition = new Vector(x / ((double)(resolutionX / 2) / width), height.getValue(x, y), y / ((double)(resolutionY / 2) / length));
                point = new Vertex(pointPosition);
                point.setColor(color);

                // Add vertex to point cloud
                pointCloud[x + (resolutionX / 2)][y + (resolutionY / 2)] = point;

                for (Parameter param : generator.getEnvironment()
                        .getParameters().values())
                {
                    param.reset();
                }
            }
        }

        MeshGenerator meshGenerator = new MeshGenerator(pointCloud, 10);
        TriangleMeshNode meshNode = new TriangleMeshNode(meshGenerator.getTriangleMesh());
        watcherMarker.addChild(meshNode);
    }

    @Override
    public void onTimerTick(int counter) {
        // Timer tick event
    }

    @Override
    public void onSceneRedraw() {
        // Scene redraw
    }
}
