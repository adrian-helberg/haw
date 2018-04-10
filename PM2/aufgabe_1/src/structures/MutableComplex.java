package structures;

/**
 * Representation of mutable complex numbers
 * @author Adrian Helberg
 */
public class MutableComplex implements Complex {

    // Variables
    /**
     * Real and imaginary part of a complex number
     * z with z = a + b * i
     * a = Re(z), b = Im(z)
     */
    private double Re, Im;

    // Default constructor
    public MutableComplex() {
        this.Re = 0.0;
        this.Im = 0.0;
    }

    // Constructor with initial parameters
    public MutableComplex(double re, double im) {
        this.Re = re;
        this.Im = im;
    }

    // Constructor with only real part
    public MutableComplex(double re) {
        this.Re = re;
        this.Im = 0.0;
    }

    public MutableComplex(double r, double theta, boolean fromPolar) {
        this.Re = r * Math.cos(theta);
        this.Im = r * Math.sin(theta);
    }

    @Override
    public double getRe() {
        return this.Re;
    }

    @Override
    public double getIm() { return this.Im; }

    public void setRe(double re) {
        Re = re;
    }

    public void setIm(double im) {
        Im = im;
    }

    @Override
    public Complex multiply(Complex c) {
        double re = this.getRe() * c.getRe() - this.getIm() * c.getIm();
        double im = this.getIm() * c.getRe() + this.getRe() * c.getIm();

        this.Re = re;
        this.Im = im;

        return this;
    }

    @Override
    public Complex multiply(double x) {
        double re = this.getRe() * x;
        double im = this.getIm() * x;

        this.Re = re;
        this.Im = im;

        return this;
    }

    @Override
    public Complex add(Complex c) {
        this.Re = this.getRe() + c.getRe();
        this.Im = this.getIm() + c.getIm();

        return this;
    }

    @Override
    public Complex add(double x) {
        this.Re = this.getRe() + x;
        this.Im = this.getIm() + x;

        return this;
    }

    @Override
    public Complex subtract(Complex c) {
        this.Re = this.getRe() - c.getRe();
        this.Im = this.getIm() - c.getIm();

        return this;
    }

    @Override
    public Complex subtract(double x) {
        this.Re = this.getRe() - x;
        this.Im = this.getIm() - x;

        return this;
    }


    @Override
    public Complex reciprocal() {
        double tmp = this.getRe() * this.getRe() + this.getIm() * this.getIm();
        this.Re = this.getRe() / tmp;
        this.Im = -this.getIm() / tmp;

        return this;
    }

    @Override
    public Complex negate() {
        this.Re = -this.getRe();
        this.Im = -this.getIm();

        return this;
    }

    @Override
    public Complex conjugate() {
        this.Im = -this.getIm();

        return this;
    }
}
