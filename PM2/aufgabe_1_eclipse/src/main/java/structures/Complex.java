package structures;

import utils.MathUtils;
import java.util.Objects;

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

    /**
     * Get the length of the complex number
     * @return Length r
     */
    default double getR() {
        return this.abs();
    }

    /**
     * Get the angle of the complex number
     * @return Angle theta
     */
    default double getTheta() {
        return Math.atan2(this.getIm(), this.getRe());
    }

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
     * @param x floating point number
     * @return Complex number
     */
    default Complex multiply(double x) {
        return new ImmutableComplex(
                this.getRe() * x,
                this.getIm() * x
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
     * Multiply a complex number with a number
     * (a + b * i) + x = (a + x) + (b + x) * i
     * @param x floating point number
     * @return Complex number
     */
    default Complex add(double x) {
        return new ImmutableComplex(
                this.getRe() + x,
                this.getIm() + x
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
     * Performs subtraction
     * (a + b * i) - x = (a - x) + (b - x) * i
     * @param x Complex number (a + b * i)
     * @return Complex number
     */
    default Complex subtract(double x) {
        return new ImmutableComplex(
                this.getRe() - x,
                this.getIm() - x
        );
    }

    /**
     * Performs calculation of the multiplicative inverse
     * (1 / z) = (z' / z * z')
     * @return Reciprocal complex number
     */
    default Complex reciprocal() {
        double tmp = this.getRe() * this.getRe() + this.getIm() * this.getIm();
        return new ImmutableComplex(
                this.getRe() / tmp,
                -this.getIm() / tmp
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
     * z = (a + b * i) / (c + d * i)
     * @param c Complex number (a + b * i)
     * @return Quotient complex number
     */
    default Complex divide(Complex c) {
        return this.multiply(c.reciprocal());
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
     * |z|^{2} = z * z'
     * @return absolute part
     */
    default double abs2() {
        return Math.pow(this.abs(), 2);
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
     * Perform Hashing of complex number
     * @return Hash
     */
    default int hash() {
        return Objects.hash(
                this.getRe(),
                this.getIm()
        );
    }

    /**
     * Format complex number to Cartesian form as string
     * @return String
     */
    default String formatCartesian() {
        if (this.getIm() == 0) return this.getRe() + "+0i";
        if (this.getRe() == 0) return  this.getIm() + "i";
        if (this.getIm() < 0) return  this.getRe()
                + "-"
                +  (-this.getIm())
                + "i";
        return  this.getRe()
                + "+"
                +  this.getIm()
                + "i";
    }

    /**
     * Format complex number to trigonometric form as string
     * @return String
     */
    default String formatTrigonometric() {
        return MathUtils.round(this.getR())
                + " * (cos("
                + MathUtils.round(this.getTheta())
                + ") + sin("
                + MathUtils.round(this.getTheta())
                + "))";
    }

    /**
     * Format complex number to polar form as string
     * @return String
     */
    default String formatPolar() {
        return MathUtils.round(this.getR())
                + " * e^(i * "
                + MathUtils.round(this.getTheta())
                + ")";
    }
}
