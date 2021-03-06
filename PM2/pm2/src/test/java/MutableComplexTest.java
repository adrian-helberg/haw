import org.junit.Before;
import org.junit.Test;
import structures.MutableComplex;

import java.util.Objects;
import java.util.logging.Logger;

import static org.junit.Assert.*;
/**
 * Test class MutableComplex
 * @author Adrian Helberg, Rodrigo Ehlers
 */
public class MutableComplexTest {
	// Expectation and assertion may me equal in the threshold of 0.001
    private double DELTA = 0.001;
    @SuppressWarnings("unused")
	private Logger log;
    
    @Before
    public void setUp() throws Exception {
    	log = Logger.getLogger(MutableComplex.class.getName());
    }

    @Test
    public void testConstructorWithPolar() {
    	MutableComplex c1 = new MutableComplex(3, 0, true);
        assertEquals(new MutableComplex(3, 0), c1);

        MutableComplex c2 = new MutableComplex(3, Math.PI, true);
        assertEquals(new MutableComplex(-3, 3.6739403974420594E-16), c2);

        MutableComplex c3 = new MutableComplex(3, Math.PI / 2, true);
        assertEquals(new MutableComplex(1.8369701987210297E-16, 3.0), c3);
    }

    @Test
    public void testAccessors() {
        // Test default
    	MutableComplex c1 = new MutableComplex();
        assertEquals(0.0, c1.getRe(), DELTA);
        assertEquals(0.0, c1.getIm(), DELTA);
        assertEquals(0.0, c1.getR(), DELTA);
        assertEquals(0.0, c1.getTheta(), DELTA);

        // Test parameterized
        MutableComplex c2 = new MutableComplex(1, 1);
        assertEquals(1.0, c2.getRe(), DELTA);
        assertEquals(1.0, c2.getIm(), DELTA);
        assertEquals(Math.sqrt(2), c2.getR(), DELTA);
        assertEquals(Math.atan2(1, 1), c2.getTheta(), DELTA);

        // Test parameterized more complex
        MutableComplex c3 = new MutableComplex(62.1234, 11.11);
        assertEquals(62.1234, c3.getRe(), DELTA);
        assertEquals(11.11, c3.getIm(), DELTA);
        assertEquals(Math.sqrt(3982.6490), c3.getR(), DELTA);
        assertEquals(Math.atan2(11.11, 62.1234), c3.getTheta(), DELTA);
    }

    @Test
    public void testMultiplication() {
    	MutableComplex c1 = new MutableComplex(2, 3);
    	MutableComplex c2 = new MutableComplex(2, 3);
        assertEquals(new MutableComplex(-5.0, 12.0), c1.multiply(c2));
        assertEquals(new MutableComplex(-25.0, 60.0), c1.multiply(5));
    }

    @Test
    public void testAddition() {
    	MutableComplex c1 = new MutableComplex(2, 3);
    	MutableComplex c2 = new MutableComplex(2, 3);
        assertEquals(new MutableComplex(4.0, 6.0), c1.add(c2));
        assertEquals(new MutableComplex(9, 11), c1.add(5));
    }

    @Test
    public void testSubtract() {
    	MutableComplex c1 = new MutableComplex(2, 3);
    	MutableComplex c2 = new MutableComplex(2, 3);
        assertEquals(new MutableComplex(0, 0), c1.subtract(c2));        
        assertEquals(new MutableComplex(-5, -5), c1.subtract(5));
    }

    @Test
    public void testReciprocal() {
    	MutableComplex c = new MutableComplex(2, 3);
        assertEquals(new MutableComplex(2d/13, -3d/13), c.reciprocal());
    }

    @Test
    public void testNegate() {
    	MutableComplex c = new MutableComplex(2, 3);
        assertEquals(new MutableComplex(-2, -3), c.negate());
    }

    @Test
    public void testDivision() {
    	MutableComplex c1 = new MutableComplex(2, 3);
    	MutableComplex c2 = new MutableComplex(2, 3);
        assertEquals(new MutableComplex(1, 0), c1.divide(c2));
    }

    @Test
    public void testEquals() {
    	MutableComplex c1 = new MutableComplex(2, 3);
    	MutableComplex c2 = new MutableComplex(2, 3);
    	MutableComplex c3 = new MutableComplex(2, -3);
        assertTrue(c1.equals(c2));
        assertFalse(c1.equals(c3));
    }

    @Test
    public void testAbs() {
    	MutableComplex c = new MutableComplex(3, -4);
        assertEquals(5, c.abs(), DELTA);
    }

    @Test
    public void testAbs2() {
    	MutableComplex c = new MutableComplex(3, -4);
        assertEquals(25, c.abs2(), DELTA);
    }

    @Test
    public void testConjugate() {
    	MutableComplex c = new MutableComplex(1, 2);
        assertEquals(new MutableComplex(1, -2), c.conjugate());
    }

    @Test
    public void testHash() {
    	MutableComplex c1 = new MutableComplex(2, 3);
        assertEquals(Objects.hash(2.0, 3.0), c1.hash());
    }

    @Test
    public void testFormatCartesian() {
    	MutableComplex c = new MutableComplex(2, 3);
        assertEquals("2.0+3.0i", c.formatCartesian());
    }

    @Test
    public void testFormatTrigonometric() {
    	MutableComplex c = new MutableComplex(2, 3);
        assertEquals("3.605551 * (cos(0.982793) + sin(0.982793))", c.formatTrigonometric());
    }

    @Test
    public void testFormatPolar() {
    	MutableComplex c = new MutableComplex(2, 3);
        assertEquals("3.605551 * e^(i * 0.982793)", c.formatPolar());
    }
}