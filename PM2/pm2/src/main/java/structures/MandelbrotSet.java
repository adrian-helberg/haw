package structures;

import javafx.scene.canvas.Canvas;

public class MandelbrotSet extends FractalShape {

    private ImmutableComplex min, max;
    private double zoom;
    private int convergenceSteps;

    public MandelbrotSet(ImmutableComplex min, ImmutableComplex max, int convergenceSteps, Canvas canvas, double zoom) {
        super(canvas);

        this.min = min;
        this.max = max;
        this.zoom = zoom;
        this.convergenceSteps = convergenceSteps;

        draw();
    }

    @Override
    public void draw() {
        double precision = Math.max((max.getRe() - min.getRe()) / cWidth, (max.getIm() - min.getIm()) / cHeight);
        processCoordinates(precision, convergenceSteps, new ImmutableComplex(0, 0), zoom);
    }
}
