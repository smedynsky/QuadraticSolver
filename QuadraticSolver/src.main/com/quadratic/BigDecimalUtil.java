package com.quadratic;

import static java.math.BigDecimal.ROUND_HALF_EVEN;

import java.math.BigDecimal;

public class BigDecimalUtil {
	
	final static int OUTPUT_SCALE = 6;
	final static int WORKING_SCALE = 25;
	final static BigDecimal FOUR = BigDecimal.valueOf(4).setScale(WORKING_SCALE);
	final static BigDecimal TWO = BigDecimal.valueOf(2).setScale(WORKING_SCALE);
	final static BigDecimal ZERO = BigDecimal.ZERO.setScale(WORKING_SCALE);

	/* Implementation of the Babylonian Method to approximate square root */
	static BigDecimal computeSquareRoot(BigDecimal S) {
	    BigDecimal x0 = ZERO;
	    
	    // initial estimate
	    BigDecimal xn = new BigDecimal(Math.sqrt(S.doubleValue()));
	    
	    while (! x0.equals(xn)) {
	        x0 = xn;
	        xn = S.divide(x0, WORKING_SCALE, ROUND_HALF_EVEN);
	        xn = xn.add(x0);
	        xn = xn.divide(TWO, WORKING_SCALE, ROUND_HALF_EVEN);
	    }
	    
	    return xn;
	}
	
	static boolean greaterOrEqualToZero(BigDecimal operand) {
		return operand.compareTo(BigDecimal.ZERO) >= 0;
	}
	
	static boolean isZero(BigDecimal operand) {
		return operand.compareTo(BigDecimal.ZERO) == 0;
	}
	
	static boolean lessThanZero(BigDecimal operand) {
		return operand.compareTo(BigDecimal.ZERO) < 0;
	}
	
	static BigDecimal getScaledBigDecimal(BigDecimal arg) {
		return arg.setScale(OUTPUT_SCALE, BigDecimal.ROUND_HALF_UP);
	}
	
}
