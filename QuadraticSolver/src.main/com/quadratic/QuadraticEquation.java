package com.quadratic;

import java.math.BigDecimal;

public class QuadraticEquation {
	
	final BigDecimal quadraticCoefficient;
	final BigDecimal linearCoefficient;
	final BigDecimal freeTerm;
	
	public QuadraticEquation(BigDecimal quadraticCoefficient, BigDecimal linearCoefficient, BigDecimal freeTerm) {
		this.quadraticCoefficient = quadraticCoefficient;
		this.linearCoefficient = linearCoefficient;
		this.freeTerm = freeTerm;
	}
}
