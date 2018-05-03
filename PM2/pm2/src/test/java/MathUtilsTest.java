import org.junit.Before;
import org.junit.Test;

import structures.Complex;
import structures.ImmutableComplex;
import structures.MutableComplex;
import utils.FormatUtils;
import utils.MathUtils;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
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
    
    @Test
    public void testSortByLengthArray() {
    	Complex[] notSorted = new Complex[5];
    	Complex[] sorted = new Complex[5];
    	
    	for (int i = 0; i < notSorted.length; i++) {
			notSorted[i] = new ImmutableComplex(Math.random(), Math.random());
		}
    	
    	sorted = MathUtils.sortByLength(notSorted);
    	
    	for (int i = 0; i < sorted.length - 1; i++) {
			assertTrue(sorted[i].abs() < sorted[i+1].abs());
		}
    }
    
    @Test
    public void testSortByLengthList() {
    	List<Complex> notSorted = new ArrayList<Complex>();
    	List<Complex> sorted = new ArrayList<Complex>();
    	
    	for (int i = 0; i < 5; i++) {
			notSorted.add(new ImmutableComplex(Math.random(), Math.random()));
		}
    	
    	sorted = MathUtils.sortByLength(notSorted);
    	
    	for (int i = 0; i < 5 - 1; i++) {
			assertTrue(sorted.get(i).abs() < sorted.get(i+1).abs());
		}
    }
}
