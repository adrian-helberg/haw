package structures;

import java.util.Objects;

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
    
    /**
     * Returns true if complex number equals complex number numerically
     * No class check because comparison of immutable and mutable complex
     * Overrides Objects.equals for assertEquals-method from JUnit
     * @param o Object to compare with
     * @return true or false (equality or inequality)
     */
    @Override
    public boolean equals(Object o) {
    	if (this == o) {
    		return true;
    	}
    	
    	if (o == null) {
    		return false;
    	}
    	
    	ImmutableComplex i = (ImmutableComplex) o;  
    	
    	return Objects.equals(this.Re, i.Re) && 
    			Objects.equals(this.Im, i.Im);
    }
}
