package utils;

import structures.Complex;
import structures.ImmutableComplex;
import structures.MutableComplex;

/**
 * Helper class with static methods for mathematics
 * @author Adrian Helberg
 */
public final class MathUtils {

    /**
     * Round a given number after 6 decimal places
     * @param number Given double
     * @return Round double
     */
    public static double round(double number) {
        return Math.floor(number * Math.pow(10, 6)) / Math.pow(10, 6);
    }
    
    /**
     * Sorts an array of complex numbers by their absolute value (length)
     * @param numbers Array of complex numbers
     * @return Sorted array of complex numbers 
     */
    public static Complex[] sortByLength(Complex[] numbers) {
    	
    	// TODO: Sort
    	
    	return numbers;
    }

    /**
     * Returns a complex number of complex exponential form
     * @param c Complex number
     * @return Complex exponential of complex number
     */
    public static Complex exp(Complex c) {

        if (c instanceof MutableComplex) {
            return new MutableComplex(
                    Math.exp(c.getRe()) * Math.cos(c.getIm()),
                    Math.exp(c.getRe()) * Math.sin(c.getIm())
            );
        }

        return new ImmutableComplex(
                Math.exp(c.getRe()) * Math.cos(c.getIm()),
                Math.exp(c.getRe()) * Math.sin(c.getIm())
        );
    }

    /**
     * Hyperbolic sine for calculating sin
     * @param theta Angle of complex number
     * @return Hyperbolic sine
     */
    private static double sinh(double theta) {
        return (Math.exp(theta) - Math.exp(-theta)) / 2;
    }

    /**
     * Hyperbolic cosine for calculating cos
     * @param theta Angle of complex number
     * @return Hyperbolic cosine
     */
    private static double cosh(double theta) {
        return (Math.exp(theta) + Math.exp(-theta)) / 2;
    }

    /**
     * Returns a complex number with complex sine
     * @param c Complex number
     * @return Complex number
     */
    public static Complex sin(Complex c) {

        if (c instanceof MutableComplex) {
            return new MutableComplex(
                    cosh(c.getIm()) * Math.sin(c.getRe()),
                    sinh(c.getIm()) * Math.cos(c.getRe())
            );
        }

        return new ImmutableComplex(
                cosh(c.getIm()) * Math.sin(c.getRe()),
                sinh(c.getIm()) * Math.cos(c.getRe())
        );
    }

    /**
     * Returns a complex number with complex cosine
     * @param c Complex number
     * @return Complex number
     */
    public static Complex cos(Complex c) {

        if (c instanceof MutableComplex) {
            return new MutableComplex(
                    cosh(c.getIm()) * Math.cos(c.getRe()),
                    -sinh(c.getIm()) * Math.sin(c.getRe())
            );
        }

        return new ImmutableComplex(
                cosh(c.getIm()) * Math.cos(c.getRe()),
                -sinh(c.getIm()) * Math.sin(c.getRe())
        );
    }
}
