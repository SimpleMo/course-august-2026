package org.hse.examples.application;

import org.hse.examples.domain.Check;

public class CalculatorImpl implements Calculator {

    private final Check checker;
    private final int digitsCount;

    public CalculatorImpl(Check checker, int digitsCount) {
        this.checker = checker;
        this.digitsCount = digitsCount;
    }

    @Override
    public int calculate() {
        int count = 0;
        for(int i = 0; i < Math.pow(10, digitsCount); i++) {
            if (checker.check(i)) {
                count++;
            }
        }

        return count;
    }
}
