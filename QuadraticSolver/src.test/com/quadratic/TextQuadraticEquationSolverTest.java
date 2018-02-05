package com.quadratic;

import static org.junit.Assert.*;

import org.junit.Test;

public class TextQuadraticEquationSolverTest {

	@Test
	public void testSolveTwoRoots() {
		TextQuadraticEquationSolver textInterface = initTextInterface();
		String result = textInterface.solve("Equation{a=1,b=3,c=1}");
		assertEquals("Solution{-2.618034,-0.381966}", result);
	}

	@Test
	public void testSolveSingleRoot() {
		TextQuadraticEquationSolver textInterface = initTextInterface();
		String result = textInterface.solve("Equation{a=0,b=3,c=-3}");
		assertEquals("Solution{1.000000}", result);
	}
	
	@Test
	public void testSolveBadEquationFormat() {
		TextQuadraticEquationSolver textInterface = initTextInterface();

		String output = textInterface.solve("Equation{a=1,c=1}");
		
		assertTrue(output.contains(StringEquationReader.PARSING_ERROR_MESSAGE));
	}

	@Test
	public void testSolveInvalidBigDecimalA() {
		TextQuadraticEquationSolver textInterface = initTextInterface();
		
		String output = textInterface.solve("Equation{a=-1.23E,b=1,c=1}");
		assertTrue(output.contains(StringEquationReader.INVALID_COEFFICIENT_ERROR));
	}

	@Test
	public void testSolveInvalidBigDecimalB() {
		TextQuadraticEquationSolver textInterface = initTextInterface();
		
		String output = textInterface.solve("Equation{a=1,b=-1.23E,c=1}");
		assertTrue(output.contains(StringEquationReader.INVALID_COEFFICIENT_ERROR));
	}
	
	@Test
	public void testSolveInvalidBigDecimalC() {
		TextQuadraticEquationSolver textInterface = initTextInterface();
		
		String output = textInterface.solve("Equation{a=1,b=1,c=-1.23E}");
		assertTrue(output.contains(StringEquationReader.INVALID_COEFFICIENT_ERROR));
	}	
	
	@Test
	public void testSolveEmptyEquation() {
		TextQuadraticEquationSolver textInterface = initTextInterface();
		
		String output = textInterface.solve("");
		assertTrue(output.contains(StringEquationReader.PARSING_ERROR_MESSAGE));
	}	

	@Test
	public void testSolveNullEquation() {
		TextQuadraticEquationSolver textInterface = initTextInterface();
		
		String solution = textInterface.solve(null);
		assertTrue(solution.contains(StringEquationReader.NULL_MESSAGE));
	}	
	
	@Test
	public void testSolveWithSolverError() {
		TextQuadraticEquationSolver textInterface = initTextInterface();
		
		String solution = textInterface.solve("Equation{a=1,b=1,c=1}");
		assertTrue(solution.contains(StableQuadraticEquationSolver.NEGATIVE_DESCRIMINANT_ENCOUNTERED));
	}
	
	static TextQuadraticEquationSolver initTextInterface() {
		QuadraticEquationSolver solver = QuadraticEquationSolverTest.initQuadraticEquationSolver();
		return new TextQuadraticEquationSolver(solver);
	}

}
