package org.hse.examples.application;

import org.hse.examples.domain.Check;

import java.util.stream.IntStream;

public class CalculatorStreamImpl implements Calculator {
    private final Check checker;
    private final int digitsCount;

    public CalculatorStreamImpl(Check checker, int digitsCount) {
        this.checker = checker;
        this.digitsCount = digitsCount;
    }

    @Override
    public int calculate() {
        return (int) IntStream.range(0, (int) Math.pow(10, digitsCount)).parallel().filter(checker::check).count();
    }
}
