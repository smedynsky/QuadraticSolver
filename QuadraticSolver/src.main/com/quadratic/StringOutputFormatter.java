package com.quadratic;

import java.util.Formatter;

public class StringOutputFormatter {
	
	static final String SINGLE_SOLUTION_FORMAT = "Solution{%s}";
	static final String TWO_SOLUTION_FORMAT = "Solution{%s,%s}";
	static final String ERROR_FORMAT = "Error{%s}";
	
	public static String formatSolution(Solution solution) {
		StringBuilder formattedSolution = new StringBuilder();
		Formatter formatter = new Formatter(formattedSolution);
		
		if (solution.secondRoot.isPresent()) {
			formatter.format(TWO_SOLUTION_FORMAT, solution.firstRoot, solution.secondRoot.get());
		} else {
			formatter.format(SINGLE_SOLUTION_FORMAT, solution.firstRoot);
		}
		
		formatter.close();
		
		return formattedSolution.toString();
	}
	
	public static String formatError(Throwable exception) {
		return String.format(ERROR_FORMAT, exception.getMessage());
	}

}
