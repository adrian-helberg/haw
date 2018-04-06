package haw.pm2.task1;

import haw.pm2.structures.Complex;

/**
 * Representation of immutable complex number object
 */
public final class ImmutableComplex extends Complex {

    private double Re, Im;

    public ImmutableComplex() {
        this.Re = 0.0;
        this.Im = 0.0;
    }

    public ImmutableComplex(double re, double im) {
        this.Re = re;
        this.Im = im;
    }

    @Override
    public double getRe() {
        return this.Re;
    }

    @Override
    public double getIm() {
        return this.Im;
    }
}
