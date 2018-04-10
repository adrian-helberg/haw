package structures;

/**
 * Representation of immutable complex numbers
 * @author Adrian Helberg
 */
public class ImmutableComplex implements Complex {

    // Variables
    /**
     * Real and imaginary part of a complex number
     * z with z = a + b * i
     * a = Re(z), b = Im(z)
     */
    private final double Re, Im;

    // Default constructor
    public ImmutableComplex() {
        this.Re = 0.0;
        this.Im = 0.0;
    }

    // Constructor with initial parameters
    public ImmutableComplex(double re, double im) {
        this.Re = re;
        this.Im = im;
    }

    // Constructor with only real part
    public ImmutableComplex(double re) {
        this.Re = re;
        this.Im = 0.0;
    }

    // Constructor with polar coordinates
    public ImmutableComplex(double r, double theta, boolean fromPolar) {
        this.Re = r * Math.cos(theta);
        this.Im = r * Math.sin(theta);
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
