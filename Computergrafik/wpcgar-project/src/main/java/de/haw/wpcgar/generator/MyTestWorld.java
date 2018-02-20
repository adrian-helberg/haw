package de.haw.wpcgar.generator;

import de.haw.wpcgar.Config;
import de.haw.wpcgar.math.Random;
import de.haw.wpcgar.structure.Biome;
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
    private WorldGenerator generator;

    private static BufferedImage image;
    private static WorldComponent renderer;

    @SuppressWarnings("UnusedAssignment")
    public MyTestWorld() {
        String seed = Config.DEFAULT_SEED;
        if(Config.USE_DEFAULT_SEED) {
            // We want a string with 13 characters to deal with
            // Resulting string has lower and upper case letters
            seed = new Random().getRandomString(13);
            System.out.println("seed: " + seed);
        }

        // Initialize world generator
        generator = new WorldGenerator(seed);

        // Register biomes
        generator.registerBiome(Forest.class);

        // Register parameter


        // Create image for output
        image = createImage();

        try
        {
            ImageIO.write(image, "png", new File("render.png"));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        
        showWindow();
    }

    private void showWindow() {
        final Frame frame = new Frame("test");
        renderer = new WorldComponent(image);
        frame.setPreferredSize(new Dimension(Config.WINDOW_WIDTH, Config.WINDOW_WIDTH));
        frame.setLayout(new BorderLayout());
        frame.setResizable(false);
        frame.add(renderer, "Center");
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
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

    private BufferedImage createImage() {
        long start = System.currentTimeMillis();
        int width = Config.WIDTH;
        int height = Config.HEIGHT;

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = image.createGraphics();

        int count = 0;
        int level = 10;
        int pixelCount = width * height;

        // Iterate over all pixels
        for (int x = -(width / 2); x < (width / 2); x++) {
            for (int y = -(height / 2); y < (height / 2); y++) {
                Biome biome = generator.getBiome(x * Config.ZOOM + Config.START_X, y * Config.ZOOM + Config.START_Y);
                Color color = Color.BLACK;

                if (biome != null) {
                    color = biome.getColor(x * Config.ZOOM + Config.START_X, y * Config.ZOOM + Config.START_Y);
                }

                g.setColor(color);
                g.fillRect(x + (width / 2), y + (height / 2), 1, 1);

                count++;

                if ((count * 100 / pixelCount) >= level) {
                    System.out.print(level + "%   ");
                    level += 10;
                }
            }
        }

        System.out.print("\n");

        long end = System.currentTimeMillis();
        double diff = (end - start) / 1000;
        double speed = diff / (width * height) * (64 * 64);
        System.out.println("Process time: " + diff + " s");

        return image;
    }
}
