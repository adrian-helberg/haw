package structures;

import javafx.scene.canvas.Canvas;

public class KochCurve extends FractalShape {

    private final int maxDepth;
    private int depth;
    private double x1, y1, x2, y2;

    public KochCurve(int depth, int maxDepth, Canvas canvas) {
        super(canvas);

        this.maxDepth = maxDepth;
        this.depth = depth;
        x1 = 10;
        y1 = cHeight / 3 * 2;
        x2 = cWidth - 10;
        y2 = y1;

        draw();
    }

    @Override
    public void draw() {
        if (depth == 0) {
            context.strokeLine(x1, y1, x2, y2);
        } else if (depth > maxDepth) {
            drawKochCurve(maxDepth, x1, y1, x2, y2);
        }
        else {
            drawKochCurve(depth, x1, y1, x2, y2);
        }
    }

    private void drawKochCurve(int n, double x1, double  y1, double x5, double y5) {
        double deltaX, deltaY, x2, y2, x3, y3, x4, y4;

        if(n > 0) {
            deltaX = x5 - x1;
            deltaY = y5 - y1;

            //Calculate new dots
            x2 = x1 + deltaX / 3;
            y2 = y1 + deltaY / 3;

            x3 = (int) (0.5 * (x1+x5) - (Math.sqrt(3)/6) * (y1-y5));
            y3 = (int) (0.5 * (y1+y5) - (Math.sqrt(3)/6) * (x5-x1));

            x4 = x1 + 2 * deltaX /3;
            y4 = y1 + 2 * deltaY /3;

            drawKochCurve(n-1, x1, y1, x2, y2);
            drawKochCurve(n-1, x2, y2, x3, y3);
            drawKochCurve(n-1, x3, y3, x4, y4);
            drawKochCurve(n-1, x4, y4, x5, y5);
        }
        else{
            context.strokeLine(x1, y1, x5, y5);
        }
    }
}
