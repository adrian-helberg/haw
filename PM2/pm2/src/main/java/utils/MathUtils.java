package utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import structures.Complex;
import structures.ImmutableComplex;
import structures.MutableComplex;
import structures.Tuple;

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
    	
    	Tuple[] tuples = new Tuple[numbers.length];
    	Complex[] sortedNumbers = new Complex[numbers.length];
    	
    	for (int i = 0; i < numbers.length; i++) {
    		tuples[i] = new Tuple(numbers[i], numbers[i].abs());
    	}
    	
    	Arrays.sort(tuples);
    	
    	for (int i = 0; i < numbers.length; i++) {
    		sortedNumbers[i] = tuples[i].complexNumber;
    	}
    	
    	return sortedNumbers;
    }
    
    /**
     * Sorts a list of complex numbers by their absolute value (length)
     * @param numbers List of complex numbers
     * @return Sorted List of complex numbers 
     */
    public static List<Complex> sortByLength(List<Complex> numbers) {
    	
    	List<Tuple> tuples = new ArrayList<Tuple>();
    	List<Complex> sortedNumbers = new ArrayList<Complex>();
    	
    	for (Complex complex : numbers) {
			tuples.add(
					new Tuple(complex, complex.abs())
			);
		}
    	
    	Collections.sort(tuples);
    	
    	for (Tuple tuple : tuples) {
			sortedNumbers.add(tuple.complexNumber);
		}
    	
    	return sortedNumbers;
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
