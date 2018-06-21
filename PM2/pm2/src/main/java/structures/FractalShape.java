package structures;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public abstract class FractalShape {
    double cWidth, cHeight;

    GraphicsContext context;

    FractalShape(Canvas c) {
        context = c.getGraphicsContext2D();

        cWidth = c.getWidth();
        cHeight = c.getHeight();
    }

    private static int getConvergenceSteps(double ci, double c, double z, double zi, int convergenceSteps) {
        for (int i = 0; i < convergenceSteps; i++) {
            double ziT = 2 * (z * zi);
            double zT = z * z - (zi * zi);

            z = zT + c;
            zi = ziT + ci;

            if (z * z + zi * zi >= 4.0) {
                return i;
            }
        }

        return convergenceSteps;
    }

    public abstract void draw();

    void processCoordinates(double precision, int convergenceSteps, ImmutableComplex z, double zoom) {
        for (double c = z.getRe(), xR = 0; xR < cWidth; c += precision * zoom, xR++) {
            for (double ci = z.getIm(), yR = 0; yR < cHeight; ci += precision * zoom, yR++) {

                double convergence = getConvergenceSteps(ci, c, z.getRe(), z.getIm(), convergenceSteps);

                double t1 = convergence / convergenceSteps;
                double c1 = Math.min(255 * 2 * t1, 255);
                double c2 = Math.max(255 * (2 * t1 - 1), 0);

                if (convergence != convergenceSteps) {
                    context.setFill(Color.color(c2 / 255.0, c1 / 255.0, c2 / 255.0));
                } else {
                    context.setFill(Color.ORANGE); // Convergence Color
                }
                context.fillRect(xR, yR, 1, 1);
            }
        }
    }
}
