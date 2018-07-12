/**
 * Test class Task2
 * @author Adrian Helberg, Rodrigo Ehlers
 */
import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.lang.reflect.Method;

import org.junit.Test;

import structures.Complex;
import utils.FormatUtils;

public class Task2Test {

	@Test
    public void testSortByLength() {
    	
    	try (BufferedReader br = new BufferedReader(new FileReader("..\\..\\Complex_numbers_sorted.txt"))) {
			// Process file line by line
			String line = br.readLine();			
			if (line == null) return;
			
			Complex last;
			Complex current = FormatUtils.parseString(line);
		    		    
			while ((line = br.readLine()) != null) {
				last = current;
				current = FormatUtils.parseString(line);
				
				assertTrue(last.abs() < current.abs());
			}
			
		} catch (Exception e) {
			// Error handling
			e.printStackTrace();
		}
    	
    }

    public static void main(String[] args) {

		Method[] methods = ArrayDequeTest.class.getMethods();
		for (Method m : methods) {
			System.out.println(m);
		}
	}

}
