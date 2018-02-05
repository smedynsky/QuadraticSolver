package com.quadratic;

import static org.junit.Assert.*;

import java.math.BigDecimal;

import org.junit.Test;

import static com.quadratic.BigDecimalUtil.*;

public class QuadraticEquationSolverTest {
	
	// compensate for some rounding errors when evaluation the equation
	static final BigDecimal EPSILON = new BigDecimal("0.000005");
	
	@Test
	public void testSolveWholeRoots() {
		QuadraticEquation equation = new QuadraticEquation(new BigDecimal(1), new BigDecimal(2), new BigDecimal(-15));
		QuadraticEquationSolver solver = initQuadraticEquationSolver();
		solver.setEquation(equation);
		Solution roots = solver.solve();
		validateRoots(equation, roots);
	}

	@Test
	public void testSolveFractionRoots() {
		QuadraticEquation equation = new QuadraticEquation(new BigDecimal(2), new BigDecimal(4), new BigDecimal(-10));
		QuadraticEquationSolver solver = initQuadraticEquationSolver();
		solver.setEquation(equation);
		Solution roots = solver.solve();
		validateRoots(equation, roots);
	}
	
	@Test
	public void testSolveIrrationalRoots() {
		QuadraticEquation equation = new QuadraticEquation(new BigDecimal(1), new BigDecimal(3), new BigDecimal(1));
		QuadraticEquationSolver solver = initQuadraticEquationSolver();
		solver.setEquation(equation);
		Solution roots = solver.solve();
		validateRoots(equation, roots);
	}
	
	@Test
	public void testSolveNegativeLinearCoefficient() {
		QuadraticEquation equation = new QuadraticEquation(new BigDecimal(4), new BigDecimal(-5), new BigDecimal(1));
		QuadraticEquationSolver solver = initQuadraticEquationSolver();
		solver.setEquation(equation);
		Solution roots = solver.solve();
		validateRoots(equation, roots);
	}

	@Test
	public void testSolveImaginaryRoots() {
		QuadraticEquation equation = new QuadraticEquation(new BigDecimal(1), new BigDecimal(1), new BigDecimal(1));
		QuadraticEquationSolver solver = initQuadraticEquationSolver();
		solver.setEquation(equation);
		
		try {
			solver.solve();
			fail();
		} catch (QuadraticEquationSolverException e) {
			assertTrue(e.getMessage().contains(StableQuadraticEquationSolver.NEGATIVE_DESCRIMINANT_ENCOUNTERED));
		}
	}

	@Test
	public void testSolveZeroQuadraticCoefficient() {
		QuadraticEquation equation = new QuadraticEquation(BigDecimal.ZERO, new BigDecimal(2), new BigDecimal(-10));
		QuadraticEquationSolver solver = initQuadraticEquationSolver();
		solver.setEquation(equation);
		Solution roots = solver.solve();
		validateSingleSolution(equation, roots);
	}
	
	@Test
	public void testSolveZeroFreeTerm() {
		QuadraticEquation equation = new QuadraticEquation(new BigDecimal(4), BigDecimal.ZERO, BigDecimal.ZERO);
		QuadraticEquationSolver solver = initQuadraticEquationSolver();
		solver.setEquation(equation);
		Solution roots = solver.solve();
		validateSingleZeroSolution(roots);
	}
	
	@Test
	public void testSolveUndefinedRoot() {
		QuadraticEquation equation = new QuadraticEquation(BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO);
		QuadraticEquationSolver solver = initQuadraticEquationSolver();
		solver.setEquation(equation);
		try {
			solver.solve();
			fail();
		} catch (QuadraticEquationSolverException e) { 
			assertTrue(e.getMessage().contains(StableQuadraticEquationSolver.ROOT_IS_UNDEFINED)); 
		}
	}
	
	@Test
	public void testSolveForCatastrophicCancellation() {
		QuadraticEquation equation = new QuadraticEquation(new BigDecimal("0.000001"), 
				new BigDecimal("-1000000.000001"), new BigDecimal("1"));
		QuadraticEquationSolver solver = initQuadraticEquationSolver();
		solver.setEquation(equation);
		Solution roots = solver.solve();
		validateRoots(equation, roots);
	}

	private void validateRoots(QuadraticEquation equation, Solution roots) {
		assertTrue(roots.secondRoot.isPresent());
		assertEquals(1, EPSILON.compareTo(evaluateEquation(equation, roots.firstRoot).abs()));
		assertEquals(1, EPSILON.compareTo(evaluateEquation(equation, roots.secondRoot.get()).abs()));
	}
	
	private BigDecimal evaluateEquation(QuadraticEquation equation, BigDecimal root) {
		BigDecimal quadraticTerm = equation.quadraticCoefficient.multiply(root.pow(2));
		BigDecimal linearTerm = equation.linearCoefficient.multiply(root);
		BigDecimal sum = quadraticTerm.add(linearTerm);
		sum = sum.add(equation.freeTerm);
		
		return getScaledBigDecimal(sum);
	}	
	
	private void validateSingleSolution(QuadraticEquation equation, Solution roots) {
		assertFalse(roots.secondRoot.isPresent());
		assertEquals(getScaledBigDecimal(equation.freeTerm.negate().divide(equation.linearCoefficient)), roots.firstRoot);
	}
	
	private void validateSingleZeroSolution(Solution roots) {
		assertFalse(roots.secondRoot.isPresent());
		assertEquals(getScaledBigDecimal(BigDecimal.ZERO), roots.firstRoot);
	}
	
	static QuadraticEquationSolver initQuadraticEquationSolver() {
		return new StableQuadraticEquationSolver();
	}
	
}
