package applications;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import structures.Complex;
import structures.ImmutableComplex;
import utils.FormatUtils;
import utils.MathUtils;

public class Task2 {

	public static void main(String[] args) throws IOException {
		
		// ---------- TASK 1 ---------- //
		
		// Writer for file that contains complex numbers
		PrintWriter writer = new PrintWriter("Complex_numbers.txt");
		
		// Constant complex c = -0.7 + 0.2i
		final Complex c = new ImmutableComplex(-0.7, 0.2);
		
		// Container for storing 1000 complex numbers
		Complex[] numbers = new ImmutableComplex[1000];
		// Container for storing 1000 complex numbers from file
		// Array and List
		Complex[] read_numbers_array = new ImmutableComplex[1000];
		List<Complex> read_numbers_list = new ArrayList<Complex>();
		
		// Add complex z_0 = 0 + 0i
		numbers[0] = new ImmutableComplex(0, 0);
		// Write first case z_0 into file
		writer.println(numbers[0].formatCartesian());
		
		// Create 1000 complex numbers with formula
		// z_0 = 0
		// z_n+1 = z_n^2 + c
		for (int i = 0; i < numbers.length - 1; i++) {
			Complex z_n = numbers[i];
			numbers[i + 1] = z_n.multiply(z_n).add(c);
			writer.println(numbers[i+1].formatCartesian());
		}
		
		// Garbage
		writer.close();
		
		int i = 0;
		// Read file with complex numbers
		try (BufferedReader br = new BufferedReader(new FileReader("Complex_numbers.txt"))) {
		    String line;
		    // Process file line by line
		    while ((line = br.readLine()) != null) {
		    	Complex z = FormatUtils.parseString(line);		    	
		    	read_numbers_array[i++] = z;
		    	read_numbers_list.add(z);
		    }
		} catch (Exception e) {
			// Error handling
			e.printStackTrace();
		}
		
		// Writer for file that contains complex numbers
		PrintWriter writer2 = new PrintWriter("Complex_numbers_sorted.txt");
		
		Complex[] sortedNumbers = new Complex[read_numbers_array.length];
		sortedNumbers = MathUtils.sortByLength(read_numbers_array);
		
		for (int j = 0; j < sortedNumbers.length; j++) {
			writer2.println(sortedNumbers[j].formatCartesian());
		}
		
		// Garbage
		writer2.close();
		
		// ---------- TASK 2 ---------- //
		
		long test = -1L;
		System.out.println(test + " isOdd? " + isOdd(-1L));
		
		System.out.println((int) (char) (byte) -1);
		
		int x = 1984;
		int y = 2001;
		x ^= y ^= x ^ y;
		System.out.println("x = " + x + "; y = " + y);
		
		System.out.print("Hell");
		System.out.println("o world");
		
		int j = 0;
		for (int a = 0; a < 100; a++)
			j = ++j;
		System.out.println(j);
	}
	
	public static boolean isOdd(long i) {
		
		//return (i % 2) == 1;
		return (i % 2 + 2) % 2 == 1;		
	}
}
