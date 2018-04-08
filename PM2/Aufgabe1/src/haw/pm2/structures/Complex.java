package haw.pm2.structures;

import haw.pm2.task1.ImmutableComplex;

/**
 * Interface Complex
 * Abstract implementation to represent complex numbers
 * Containing all basic operations on complex numbers with read-only access
 * Mutable implementations of Complex may override default methods
 * @see <a href="https://en.wikipedia.org/wiki/Complex_number">Complex number - Wikipedia</a>
 * @author Adrian Helberg
 */
public interface Complex {

    // Accessors
    /**
     * Get the real part of the complex number
     * @return Real part
     */
    double getRe();

    /**
     * Get the imaginary part of the complex number
     * @return Imaginary part
     */
    double getIm();

    // Public Instance Methods
    /**
     * Multiply two given complex numbers
     * (a + b * i) * (c + d * i) = (a * c - b * d) + (b * c + a * d) * i
     * @param c Complex number (a + b * i)
     * @return Complex number
     */
    default Complex multiply(Complex c) {
        return new ImmutableComplex(
                this.getRe() * c.getRe() - this.getIm() * c.getIm(),
                this.getIm() * c.getRe() + this.getRe() * c.getIm()
        );
    }

    /**
     * Multiply a complex number with a number
     * (a + b * i) * x = (a * x) + (b * x) * i
     * @param n floating point number
     * @return Complex number
     */
    default Complex multiply(double n) {
        return new ImmutableComplex(
                this.getRe() * n,
                this.getIm() * n
        );
    }

    /**
     * Performs addition
     * (a + b * i) + (c + d * i) = (a + c) + (b + d) * i
     * @param c Complex number (a + b * i)
     * @return New immutable complex number
     */
    default Complex add(Complex c) {
        return new ImmutableComplex(
                this.getRe() + c.getRe(),
                this.getIm() + c.getIm()
        );
    }

    /**
     * Performs subtraction
     * (a + b * i) - (c + d * i) = (a - c) + (b - d) * i
     * @param c Complex number (a + b * i)
     * @return Complex number
     */
    default Complex subtract(Complex c) {
        return new ImmutableComplex(
                this.getRe() - c.getRe(),
                this.getIm() - c.getIm()
        );
    }

    /**
     * Returns negation of the value.
     * @return Complex number
     */
    default Complex negate() {
        return new ImmutableComplex(
                -this.getRe(),
                -this.getIm()
        );
    }

    /**
     * Performs division
     * @param c Complex number (a + b * i)
     * @return Quotient complex number
     */
    default Complex divide(Complex c) {
        // TODO
        return null;
    }

    /**
     * Returns true if cmp equals object numerically
     * @param c Complex number to compare with
     * @return true or false
     */
    default boolean cmp(Complex c) {
        return this.getRe() == c.getRe() && this.getIm() == c.getIm();
    }

    /**
     * Returns the absolute part of its polar form
     * |a + b * i| = SQRT(a^{2} + b^{2})
     * @return Modulus
     */
    default double abs() {
        return Math.sqrt(Math.pow(this.getRe(), 2) + Math.pow(this.getIm(), 2));
    }

    /**
     * Returns square of the absolute value
     * @return absolute part
     */
    default double abs2() {
        // TODO
        return 0.0;
    }

    /**
     * Returns the angle part of its polar form
     * @return angle
     */
    default double angle() {
        // TODO
        return 0.0;
    }

    /**
     * Returns the complex conjugate
     * z' = a - b * i
     * @return Complex conjugate
     */
    default Complex conjugate() {
        return new ImmutableComplex(
                this.getRe(),
                -this.getIm()
        );
    }

    /**
     * Returns a complex object which denotes the given polar form
     * z = r * e^{i * theta} = r * (cos(theta) + i * sin(theta))
     * @param r Radius
     * @param theta Angle
     * @return Complex number
     */
    default Complex polar(double r, double theta) {
        // TODO
        return null;
    }

    /**
     * Returns a complex object which denotes the given rectangular form
     * @param c Complex number
     * @return Complex number in rectangular form
     */
    default Complex rectangular(Complex c) {
        // TODO
        return null;
    }
}
