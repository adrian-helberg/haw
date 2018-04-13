package tests;

import org.junit.Before;
import org.junit.Test;
import structures.ImmutableComplex;
import structures.MutableComplex;
import utils.MathUtils;

import static org.junit.Assert.*;

import java.util.logging.Logger;

public class MathUtilsTest {
	@SuppressWarnings("unused")
	private Logger log;
	
	@Before
    public void setUp() throws Exception {
    	log = Logger.getLogger(MathUtilsTest.class.getName());
    }

    @Test
    public void testRound() {
        double n = MathUtils.round(24.123456789);
        assertEquals(24.123456, n, 0);
    }

    @Test
    public void testExp() {
        ImmutableComplex c1 = new ImmutableComplex(2, 3);
        MutableComplex c2 = new MutableComplex(2, 3);
        
        double a = Math.exp(c1.getRe()) * Math.cos(c1.getIm());
        double b = Math.exp(c1.getRe()) * Math.sin(c1.getIm());
                
        assertEquals(new ImmutableComplex(a, b), MathUtils.exp(c1));
        assertEquals(new MutableComplex(a, b), MathUtils.exp(c2));
    }

    @Test
    public void testSin() {
        ImmutableComplex c1 = new ImmutableComplex(2, 3);
        MutableComplex c2 = new MutableComplex(2, 3);

        assertEquals(new ImmutableComplex(9.15449914691143, -4.168906959966565), MathUtils.sin(c1));
        assertEquals(new MutableComplex(9.15449914691143, -4.168906959966565), MathUtils.sin(c2));
    }

    @Test
    public void testCos() {
        ImmutableComplex c1 = new ImmutableComplex(2, 3);
        MutableComplex c2 = new MutableComplex(2, 3);

        assertEquals(new ImmutableComplex(-4.189625690968807, -9.109227893755337), MathUtils.cos(c1));
        assertEquals(new MutableComplex(-4.189625690968807, -9.109227893755337), MathUtils.cos(c2));
    }
}
