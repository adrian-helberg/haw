package applications;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Logger;

import structures.Complex;
import structures.ImmutableComplex;
import utils.FormatUtils;

public class Task2 {

	public static void main(String[] args) throws IOException {
		PrintWriter writer = new PrintWriter("Complex_numbers.txt");
		
		// Constant complex c = -0.7 + 0.2i
		final Complex c = new ImmutableComplex(-0.7, 0.2);
		
		Complex[] numbers = new ImmutableComplex[1000];
		Complex[] read_numbers = new ImmutableComplex[1000];
		
		// Add complex z_0 = 0 + 0i
		numbers[0] = new ImmutableComplex(0, 0);
		writer.println(numbers[0].formatCartesian());
		
		// Create 1000 complex numbers with formula
		// z_0 = 0
		// z_n+1 = z_n^2 + c
		for (int i = 0; i < 999; i++) {
			Complex z_n = numbers[i];
			numbers[i + 1] = z_n.multiply(z_n).add(c);
			
			writer.println(numbers[i+1].getRe() + " + " + numbers[i+1].getIm() + "i");
		}
		
		writer.close();
		
		int i = 0;
		try (BufferedReader br = new BufferedReader(new FileReader("Complex_numbers.txt"))) {
		    String line;
		    while ((line = br.readLine()) != null) {
		    	Complex z = FormatUtils.parseString(line);
		       read_numbers[i++] = z;
		       System.out.println(z.formatCartesian());
		    }
		}
	}
}
