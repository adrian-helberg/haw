package de.haw.wpcgar.generator;

import de.haw.wpcgar.MyTestWorld;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Graphical interface for generated world.
 * Contains the buffered image.
 * @author Adrian Helberg
 */
public class WorldComponent extends Component {
    private BufferedImage image;

    public WorldComponent(BufferedImage image) {
        this.image = image;
    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(
                image,
                0,
                0,
                MyTestWorld.windowWidth,
                MyTestWorld.windowHeight,
                0,
                0,
                image.getWidth(),
                image.getHeight(),
                null
        );
    }

    @Override
    public void update(Graphics g)
    {
        paint(g);
    }
}
