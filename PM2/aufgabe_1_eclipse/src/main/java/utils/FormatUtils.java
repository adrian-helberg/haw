package utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import structures.Complex;
import structures.ImmutableComplex;

/**
 * Helper class with static methods for formatting
 * @author Adrian Helberg
 */
public class FormatUtils {

	public static Complex parseString(String z) {
		Pattern complexPattern = Pattern.compile(
				"([+-]?\\d+)?" +	
				"([+-]?i\\d*)?"
		);
		
		// Remove spaces
		z = z.replace(" ", "");
		
		Matcher matches = complexPattern.matcher(z);
		
		if (matches.find()) {
			boolean reFound = false;
			double re = 0;
			if ((matches.group(1) != null) && (matches.group(1).length() > 0)) {
				reFound = true;
				
				try {
					re = Double.parseDouble(matches.group(1));
				} catch (Exception parseException) {
					parseException.printStackTrace();
				}
			}
			
			boolean imFound = false;
			double im = 0;
			if ((matches.group(2) != null) && (matches.group(2).length() > 0)){
				imFound = true;
				
				String imString = matches.group(2).replace("i", "");
				
				// Check if only "i" or "-i" is provided as input - add value of 1
				if (imString.length() <= 1) {
					imString = imString + "1";
				}
				
				try {
					im = Double.parseDouble(imString);
				} catch (Exception parseException) {
					parseException.printStackTrace();
				}
			}
			
			if(!reFound && !imFound) // Input is something like "asdasdi"
				return null;
			else
				return new ImmutableComplex(re, im);
		} else {
			return null;
		}
	}
}
