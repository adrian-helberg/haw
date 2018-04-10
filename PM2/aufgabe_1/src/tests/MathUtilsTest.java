package tests;

import org.junit.Test;
import structures.ImmutableComplex;
import structures.MutableComplex;
import utils.MathUtils;

import static org.junit.Assert.*;

public class MathUtilsTest {
    // Expectation and assertion may me equal in the threshold of 0.001
    private double DELTA = 0.001;

    @Test
    public void testRound() {
        double n = MathUtils.round(24.123456789);
        assertEquals(24.123456, n, 0);
    }

    @Test
    public void testExp() {
        ImmutableComplex c1 = new ImmutableComplex(2, 3);
        MutableComplex c2 = new MutableComplex(2, 3);
        assertEquals(new ImmutableComplex(-7.315111, 1.042743).hash(), MathUtils.exp(c1).hash());
        assertEquals(new MutableComplex(-7.315111, 1.042743).hash(), MathUtils.exp(c2).hash());
    }

    @Test
    public void testSin() {
        ImmutableComplex c1 = new ImmutableComplex(2, 3);
        MutableComplex c2 = new MutableComplex(2, 3);

        assertEquals(new ImmutableComplex(9.15449914691143, -4.168906959966565).hash(), MathUtils.sin(c1).hash());
        assertEquals(new MutableComplex(9.15449914691143, -4.168906959966565).hash(), MathUtils.sin(c2).hash());
    }

    @Test
    public void testCos() {
        ImmutableComplex c1 = new ImmutableComplex(2, 3);
        MutableComplex c2 = new MutableComplex(2, 3);

        assertEquals(new ImmutableComplex(-4.189625690968807, -9.109227893755337).hash(), MathUtils.cos(c1).hash());
        assertEquals(new MutableComplex(-4.189625690968807, -9.109227893755337).hash(), MathUtils.cos(c2).hash());
    }
}
