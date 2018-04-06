package haw.pm2.task1;

import haw.pm2.structures.Complex;

public class MutableComplex extends Complex {

    private double Re, Im;

    public MutableComplex() {
        this.Re = 0.0;
        this.Im = 0.0;
    }

    public MutableComplex(double re, double im) {
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

    public void setRe(double re) {
        Re = re;
    }

    public void setIm(double im) {
        Im = im;
    }
}
