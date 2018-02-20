package de.haw.wpcgar.generator;

import de.haw.wpcgar.math.Random;
import de.haw.wpcgar.structure.Biome;
import de.haw.wpcgar.structure.Parameter;
import de.haw.wpcgar.structure.biomes.Forest;
import de.haw.wpcgar.structure.params.HeightMap;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MyTestWorld {
    private WorldGenerator        generator;

    private static double         zoom           = 1.0;
    private static int            width          = 800;
    private static int            height         = 800;

    private static double         startX         = 0.0;
    private static double         startY         = 0.0;

    public final static int       windowWidth    = 800;
    public final static int       windowHeight   = 800;

    private static boolean        showDensity    = false;
    private static String         densityName    = "heightmap";

    private static boolean        useDefaultSeed = true;
    private static String         defaultSeed    = "kjdsnbdsk;bhjbjfdh";

    private static BufferedImage  renderingImage;
    private static WorldComponent renderer;

    public MyTestWorld()
    {
        String seed = defaultSeed;
        if (useDefaultSeed)
        {
            seed = new Random().randomCharacterString(13);
        }

        // On initialise le generateur
        generator = new WorldGenerator(seed);

        // On inscrit les biomes
        generator.registerBiome(Forest.class);

        // On inscrit les variables d'environnement
        generator.getEnvironment().registerParameter(HeightMap.class);

        // On cree l'image
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
    }

    public BufferedImage createImage()
    {
        long start = System.currentTimeMillis();

        BufferedImage image = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_RGB);
        Graphics2D g = image.createGraphics();

        int count = 0;
        int level = 10;
        int max = width * height;

        for (int x = -(width / 2); x < (width / 2); x++)
        {
            for (int y = -(height / 2); y < (height / 2); y++)
            {
                Biome biome = generator.getBiome(x * zoom + startX, y * zoom
                        + startY);
                Color color = Color.black;

                if (biome != null)
                {
                    color = biome
                            .getColor(x * zoom + startX, y * zoom + startY);
                }

                g.setColor(color);
                g.fillRect(x + (width / 2), y + (height / 2), 1, 1);

                count++;

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
        double speed = diff / (width * height) * (64 * 64);
        System.out.println("Process time: " + diff + " s");
        System.out.println("Speed: " + speed + " s per chunks of 64*64 blocs");

        return image;
    }

    public void showWindow()
    {
        final Frame frame = new Frame("test");
        renderer = new WorldComponent(renderingImage);
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

        while (true)
        {
            frame.repaint();
            renderer.repaint();
            try
            {
                Thread.sleep(1000L);
            }
            catch (InterruptedException e1)
            {
                e1.printStackTrace();
            }
        }
    }

    public static void main(String[] args)
    {
        new MyTestWorld();
    }
}
