package com.quadratic;

public class TextQuadraticEquationSolver {

	private final QuadraticEquationSolver solver;
	
	public TextQuadraticEquationSolver(QuadraticEquationSolver solver) {
		this.solver = solver;
	}
	
	String solve(String equationString) {
		String formattedOutput = null;
		
		try {
			QuadraticEquation parsedEquation = StringEquationReader.parseEquationFromString(equationString);
				
			solver.setEquation(parsedEquation);
			Solution solution = solver.solve();
			
			formattedOutput = StringOutputFormatter.formatSolution(solution);
		} catch (Throwable e) {
			formattedOutput = StringOutputFormatter.formatError(e);
		}
		
		return formattedOutput;
	}
	
}
