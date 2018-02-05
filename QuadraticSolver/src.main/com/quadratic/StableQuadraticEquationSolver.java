package com.quadratic;

import java.math.BigDecimal;
import java.util.Optional;

import static java.math.BigDecimal.ROUND_HALF_EVEN;
import static com.quadratic.BigDecimalUtil.*;

/*
 * StableQuadraticEquationSolver uses fixed precision and scale (BigDecimal) arithmetic and a numerically stable
 * version of the quadratic formula to find the roots of a quadratic equation.
 * Please note, it does not support non-real roots.
 */
public class StableQuadraticEquationSolver implements QuadraticEquationSolver {
	
	static final String ROOT_IS_UNDEFINED = "Root is undefined";
	static final String NEGATIVE_DESCRIMINANT_ENCOUNTERED = "Negative descriminant encountered: ";
	
	private QuadraticEquation equation;
	
	public void setEquation(QuadraticEquation equation) {
		this.equation = new QuadraticEquation(
				equation.quadraticCoefficient.setScale(WORKING_SCALE), 
				equation.linearCoefficient.setScale(WORKING_SCALE), 
				equation.freeTerm.setScale(WORKING_SCALE));
	}
	
	public Solution solve() {
		Solution roots;
		
		if (!isZero(equation.quadraticCoefficient) && isZero(equation.freeTerm)) {
			roots = returnSingleZeroRoot();
		}
		else if (isZero(equation.quadraticCoefficient)) {
			roots = computeSolutionForLinearEquation();
		} 
		else {
			roots = computeRootsOfQuadraticEquation();
		}
		
		return roots;
	}

	private Solution returnSingleZeroRoot() {
		return new Solution(getScaledBigDecimal(ZERO), Optional.empty());
	}
	
	private Solution computeSolutionForLinearEquation() {
		if (isZero(equation.linearCoefficient)) {
			throw new QuadraticEquationSolverException(ROOT_IS_UNDEFINED);
		}
		
		BigDecimal onlyRoot = equation.freeTerm.negate().divide(equation.linearCoefficient, WORKING_SCALE, ROUND_HALF_EVEN);
		
		return new Solution(getScaledBigDecimal(onlyRoot), Optional.empty());
	}
	
	private Solution computeRootsOfQuadraticEquation() {
		// use (+/-) depending on sign of linear coefficient in order to avoid subtracting two quantities that
		// may be very close to each other
		BigDecimal signChange = greaterOrEqualToZero(equation.linearCoefficient) ? BigDecimal.ONE.negate() : BigDecimal.ONE;
		signChange = signChange.setScale(WORKING_SCALE, ROUND_HALF_EVEN);
				
		BigDecimal firstRoot = equation.linearCoefficient.negate().add(signChange.multiply(computeSquareRootOfDiscriminant()));
		firstRoot = firstRoot.divide(TWO.multiply(equation.quadraticCoefficient), WORKING_SCALE, ROUND_HALF_EVEN);
		BigDecimal secondRoot = computeSecondRoot(firstRoot);
		
		return new Solution(getScaledBigDecimal(firstRoot), Optional.of(getScaledBigDecimal(secondRoot)));		
	}
	
	private BigDecimal computeSquareRootOfDiscriminant() {
		BigDecimal descriminant = equation.linearCoefficient.pow(2);
		descriminant = descriminant.subtract(FOUR.multiply(equation.quadraticCoefficient).multiply(equation.freeTerm)); 
		
		if (lessThanZero(descriminant)) {
			throw new QuadraticEquationSolverException(NEGATIVE_DESCRIMINANT_ENCOUNTERED + getScaledBigDecimal(descriminant));
		}
		
		return computeSquareRoot(descriminant);
	}
	
	private BigDecimal computeSecondRoot(BigDecimal firstRoot) {
		return equation.freeTerm.divide(equation.quadraticCoefficient.multiply(firstRoot), WORKING_SCALE, ROUND_HALF_EVEN);
	}
	
}
