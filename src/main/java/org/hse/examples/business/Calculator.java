package org.hse.examples.business;

import java.util.stream.IntStream;

public interface Calculator {

    int calculate();
}

class CalculatorImpl implements Calculator {

    private final Check checker;
    private final int digitsCount;

    CalculatorImpl(Check checker, int digitsCount) {
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


class CalculatorStreamImpl implements Calculator {
    private final Check checker;
    private final int digitsCount;

    CalculatorStreamImpl(Check checker, int digitsCount) {
        this.checker = checker;
        this.digitsCount = digitsCount;
    }

    @Override
    public int calculate() {
        return (int) IntStream.range(0, (int) Math.pow(10, digitsCount)).parallel().filter(checker::check).count();
    }
}

