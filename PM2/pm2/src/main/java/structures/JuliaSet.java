package structures;

import javafx.scene.canvas.Canvas;

public class JuliaSet extends FractalShape {

    private ImmutableComplex min, max, z;
    private double zoom;
    private int convergenceSteps;

    public JuliaSet(ImmutableComplex min, ImmutableComplex max, int convergenceSteps, Canvas canvas, ImmutableComplex z, double zoom) {
        super(canvas);

        this.min = min;
        this.max = max;
        this.z = z;
        this.zoom = zoom;
        this.convergenceSteps = convergenceSteps;

        draw();
    }

    @Override
    public void draw() {
        double precision = Math.max((max.getRe() - min.getRe()) / cWidth, (max.getIm() - min.getIm()) / cHeight);
        processCoordinates(precision, convergenceSteps, z, zoom);
    }
}
