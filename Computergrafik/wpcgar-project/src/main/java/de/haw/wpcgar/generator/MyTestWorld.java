package de.haw.wpcgar.generator;

import de.haw.wpcgar.math.Random;
import de.haw.wpcgar.structure.Biome;
import de.haw.wpcgar.structure.Parameter;
import de.haw.wpcgar.structure.biomes.*;
import de.haw.wpcgar.structure.params.HeightMap;
import de.haw.wpcgar.structure.params.Snowy;
import de.haw.wpcgar.structure.params.Temperature;
import edu.hawhamburg.shared.datastructures.mesh.Vertex;
import edu.hawhamburg.shared.math.MeshGenerator;
import edu.hawhamburg.shared.math.Vector;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

/**
 * Create a new world instance.
 * Contains world properties as member variables.
 * @author Adrian Helberg
 */
public class MyTestWorld {

    private WorldGenerator        generator;

    // World generation properties ------------------- //
    //private static double         zoom           = 5.0;
    private static int            resolutionX    = 800;
    private static int            resolutionY    = 800;
    final static int              windowWidth    = 800;
    final static int              windowHeight   = 800;
    private static boolean        useDefaultSeed = true;
    private static String         defaultSeed    = "akjsndoisanjdoias";
    // ---------------------------------------------- //

    private static BufferedImage  renderingImage;
    private Vertex[][] pointCloud = new Vertex[resolutionX][resolutionY];

    public MyTestWorld()
    {
        String seed = defaultSeed;
        if (!useDefaultSeed)
        {
            seed = new Random().randomCharacterString(13);
        }

        generator = new WorldGenerator(seed);

        // Register biomes
        generator.registerBiome(Ocean.class);
        generator.registerBiome(Snow.class);
        //generator.registerBiome(River.class);
        //generator.registerBiome(Lake.class);
        generator.registerBiome(Desert.class);
        generator.registerBiome(Forest.class);
        generator.registerBiome(Plain.class);
        generator.registerBiome(Mountain.class);

        // Register parameter
        generator.getEnvironment().registerParameter(HeightMap.class);
        //generator.getEnvironment().registerParameter(Rivers.class);
        generator.getEnvironment().registerParameter(Temperature.class);
        generator.getEnvironment().registerParameter(Snowy.class);

        BufferedImage image = createImage();
        try
        {
            ImageIO.write(image, "png", new File("render.png"));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        renderingImage = image;
        showWindow();
        exportOBJ();
    }

    private void exportOBJ() {
        File file = new File("./world.obj").getAbsoluteFile();
        System.out.println("Writing OBJ to: " + file);
        try (Writer writer = new FileWriter(file)) {
            new MeshGenerator(pointCloud, 10).writeOBJ(writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private BufferedImage createImage()
    {
        long start = System.currentTimeMillis();

        BufferedImage image = new BufferedImage(resolutionX, resolutionY,
                BufferedImage.TYPE_INT_RGB);
        Graphics2D g = image.createGraphics();

        int count = 0;
        int level = 10;
        double max = resolutionX * resolutionY;

        Color color = Color.black;

        Parameter heightParam = generator.getEnvironment().getParameter(HeightMap.class);

        Vertex point;
        Vector pointPosition;

        // Iterate over all pixels
        for (int x = -(resolutionX / 2); x < (resolutionX / 2); x++)
        {
            for (int y = -(resolutionY / 2); y < (resolutionY / 2); y++)
            {
                // Get the biome corresponding to current pixel coordinates
                Biome biome = generator.getBiome(x, y);

                if (biome != null)
                {
                    // Get biome color
                    color = biome.getColor(x, y);
                }

                g.setColor(color);
                g.fillRect(x + (resolutionX / 2), y + (resolutionY / 2), 1, 1);

                count++;

                pointPosition = new Vector(
                        x / ((double)(resolutionX / 2) / windowWidth),
                        heightParam.getValue(x, y),
                        y / ((double)(resolutionY / 2) / windowHeight)
                );

                point = new Vertex(pointPosition);
                pointCloud[x + (resolutionX / 2)][y + (resolutionY / 2)] = point;

                if ((count * 100 / max) >= level)
                {
                    System.out.println(level + "%");
                    level += 10;
                }

                for (Parameter param : generator.getEnvironment()
                        .getParameters().values())
                {
                    param.reset();
                }
            }
        }

        long end = System.currentTimeMillis();
        double diff = (end - start) / 1000;
        System.out.println("Process time: " + diff + " s");

        return image;
    }

    private void showWindow()
    {
        final Frame frame = new Frame("test");
        WorldComponent renderer = new WorldComponent(renderingImage);
        frame.setPreferredSize(new Dimension(windowWidth, windowHeight));
        frame.setLayout(new BorderLayout());
        frame.setResizable(false);
        frame.add(renderer, "Center");
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e)
            {
                frame.dispose();
                System.exit(0);
            }
        });
        frame.pack();
        frame.setVisible(true);
    }
}

