import org.junit.Before;
import org.junit.Test;
import structures.ImmutableComplex;

import java.util.Objects;
import java.util.logging.Logger;

import static org.junit.Assert.*;

public class ImmutableComplexTest {
    // Expectation and assertion may me equal in the threshold of 0.001
    private double DELTA = 0.001;
    @SuppressWarnings("unused")
	private Logger log;
    
    @Before
    public void setUp() throws Exception {
    	log = Logger.getLogger(ImmutableComplexTest.class.getName());
    }

    @Test
    public void testConstructorWithPolar() {
        ImmutableComplex c1 = new ImmutableComplex(3, 0, true);
        assertEquals(new ImmutableComplex(3, 0), c1);

        ImmutableComplex c2 = new ImmutableComplex(3, Math.PI, true);
        assertEquals(new ImmutableComplex(-3, 3.6739403974420594E-16), c2);

        ImmutableComplex c3 = new ImmutableComplex(3, Math.PI / 2, true);
        assertEquals(new ImmutableComplex(1.8369701987210297E-16, 3.0), c3);
    }

    @Test
    public void testAccessors() {
        // Test default
        ImmutableComplex c1 = new ImmutableComplex();
        assertEquals(0.0, c1.getRe(), DELTA);
        assertEquals(0.0, c1.getIm(), DELTA);
        assertEquals(0.0, c1.getR(), DELTA);
        assertEquals(0.0, c1.getTheta(), DELTA);

        // Test parameterized
        ImmutableComplex c2 = new ImmutableComplex(1, 1);
        assertEquals(1.0, c2.getRe(), DELTA);
        assertEquals(1.0, c2.getIm(), DELTA);
        assertEquals(Math.sqrt(2), c2.getR(), DELTA);
        assertEquals(Math.atan2(1, 1), c2.getTheta(), DELTA);

        // Test parameterized more complex
        ImmutableComplex c3 = new ImmutableComplex(62.1234, 11.11);
        assertEquals(62.1234, c3.getRe(), DELTA);
        assertEquals(11.11, c3.getIm(), DELTA);
        assertEquals(Math.sqrt(3982.6490), c3.getR(), DELTA);
        assertEquals(Math.atan2(11.11, 62.1234), c3.getTheta(), DELTA);
    }

    @Test
    public void testMultiplication() {
        ImmutableComplex c1 = new ImmutableComplex(2, 3);
        ImmutableComplex c2 = new ImmutableComplex(2, 3);
        assertEquals(new ImmutableComplex(-5.0, 12.0), c1.multiply(c2));
        assertEquals(new ImmutableComplex(10.0, 15.0), c1.multiply(5));
    }

    @Test
    public void testAddition() {
        ImmutableComplex c1 = new ImmutableComplex(2, 3);
        ImmutableComplex c2 = new ImmutableComplex(2, 3);
        assertEquals(new ImmutableComplex(4.0, 6.0), c1.add(c2));
        assertEquals(new ImmutableComplex(7, 8), c1.add(5));
    }

    @Test
    public void testSubtract() {
        ImmutableComplex c1 = new ImmutableComplex(2, 3);
        ImmutableComplex c2 = new ImmutableComplex(2, 3);
        assertEquals(new ImmutableComplex(0, 0), c1.subtract(c2));
        assertEquals(new ImmutableComplex(-3, -2), c1.subtract(5));
    }

    @Test
    public void testReciprocal() {
        ImmutableComplex c = new ImmutableComplex(2, 3);
        assertEquals(new ImmutableComplex(2d/13, -3d/13), c.reciprocal());
    }

    @Test
    public void testNegate() {
        ImmutableComplex c = new ImmutableComplex(2, 3);
        assertEquals(new ImmutableComplex(-2, -3), c.negate());
    }

    @Test
    public void testDivision() {
        ImmutableComplex c1 = new ImmutableComplex(2, 3);
        ImmutableComplex c2 = new ImmutableComplex(2, 3);
        assertEquals(new ImmutableComplex(1, 0), c1.divide(c2));

        ImmutableComplex c3 = new ImmutableComplex(-2, 9);
        ImmutableComplex c4 = new ImmutableComplex(-9, 2);        
        assertEquals(new ImmutableComplex(36d/85, -77d/85).getRe(), c3.divide(c4).getRe(), DELTA);
        assertEquals(new ImmutableComplex(36d/85, -77d/85).getIm(), c3.divide(c4).getIm(), DELTA);
    }

    @Test
    public void testEquals() {
        ImmutableComplex c1 = new ImmutableComplex(2, 3);
        ImmutableComplex c2 = new ImmutableComplex(2, 3);
        ImmutableComplex c3 = new ImmutableComplex(2, -3);
        assertTrue(c1.equals(c2));
        assertFalse(c1.equals(c3));
    }

    @Test
    public void testAbs() {
        ImmutableComplex c = new ImmutableComplex(3, -4);
        assertEquals(5, c.abs(), DELTA);
    }

    @Test
    public void testAbs2() {
        ImmutableComplex c = new ImmutableComplex(3, -4);
        assertEquals(25, c.abs2(), DELTA);
    }

    @Test
    public void testConjugate() {
        ImmutableComplex c = new ImmutableComplex(1, 2);
        assertEquals(new ImmutableComplex(1, -2), c.conjugate());
    }

    @Test
    public void testHash() {
        ImmutableComplex c1 = new ImmutableComplex(2, 3);
        assertEquals(Objects.hash(2.0, 3.0), c1.hash());
    }

    @Test
    public void testFormatCartesian() {
        ImmutableComplex c = new ImmutableComplex(2, 3);
        assertEquals("2.0+3.0i", c.formatCartesian());
    }

    @Test
    public void testFormatTrigonometric() {
        ImmutableComplex c = new ImmutableComplex(2, 3);
        assertEquals("3.605551 * (cos(0.982793) + sin(0.982793))", c.formatTrigonometric());
    }

    @Test
    public void testFormatPolar() {
        ImmutableComplex c = new ImmutableComplex(2, 3);
        assertEquals("3.605551 * e^(i * 0.982793)", c.formatPolar());
    }
}