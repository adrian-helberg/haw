package utils;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import structures.Complex;
import structures.ImmutableComplex;

/**
 * Helper class with static methods for formatting
 * @author Adrian Helberg
 */
public class FormatUtils {

	/**
	 * Parse a complex number as string to intern Complex type
	 * @param z Passed string
	 * @return New parsed immutable complex 
	 * @throws IOException
	 */
	public static Complex parseString(String z) throws IOException {
		
		// RegEx pattern that matches on complex format a+bi
		Pattern complexPattern = Pattern.compile("(.*)([+-].*)i");
		
		double Re = 0.0, Im = 0.0;
		Matcher m = complexPattern.matcher(z);
		
		// Match found
		if (m.matches()) {
			// String -> double
			Re = Double.parseDouble(m.group(1));
			Im = Double.parseDouble(m.group(2));
			
			return new ImmutableComplex(Re, Im);
		}
		
		// No match found
		throw new IOException("Input file contains wrong format: " + z);
	}
}
