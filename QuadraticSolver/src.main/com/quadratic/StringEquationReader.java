package com.quadratic;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringEquationReader {
	
	static final Pattern EQUATION_PATTERN = Pattern.compile("Equation\\{a=(.+),b=(.+),c=(.+)\\}");
	static final String EQUATION_FORMAT = "Equation\\{a=<arg1>,b=<arg2>,c=<arg3>\\}";
	
	static final String PARSING_ERROR_MESSAGE = "Failed to parse equation. Format: " + EQUATION_FORMAT;
	static final String INVALID_COEFFICIENT_ERROR = "Invalid coefficient passed.";
	static final String NULL_MESSAGE = "Equation string is null";
	
	public static QuadraticEquation parseEquationFromString(String stringEquation) {
		QuadraticEquation equation = null;
		
		checkForNull(stringEquation);
		
		Matcher matcher = EQUATION_PATTERN.matcher(stringEquation);
		
		if (matcher.find()) {
			String quadraticCoefficient = matcher.group(1); 
			String linearCoefficient = matcher.group(2);
			String freeTerm = matcher.group(3);
		
			equation = initEquation(quadraticCoefficient, linearCoefficient, freeTerm);
		}
		else {
			throw new TextQuadraticEquationSolverException(PARSING_ERROR_MESSAGE);
		}
		
		return equation;	
	}
	
	private static QuadraticEquation initEquation(String quadraticCoefficient, String linearCoefficient, String freeTerm) {
		QuadraticEquation equation = null;
		
		try {
			equation = new QuadraticEquation(
					new BigDecimal(quadraticCoefficient), 
					new BigDecimal(linearCoefficient), 
					new BigDecimal(freeTerm));
		} catch (NumberFormatException e) {
			throw new TextQuadraticEquationSolverException(INVALID_COEFFICIENT_ERROR);
		}
		
		return equation;
	}
	
	private static void checkForNull(String equationString) {
		if (equationString == null) {
			throw new TextQuadraticEquationSolverException(NULL_MESSAGE);
		}
	}
	
}
