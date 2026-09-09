package org.hse.examples.application;

import lombok.AllArgsConstructor;
import org.hse.examples.domain.Check;

import java.util.stream.IntStream;

@AllArgsConstructor
public class CalculatorStreamImpl implements Calculator {
    private final Check checker;
    private final int digitsCount;

    @Override
    public int calculate() {
        return (int) IntStream.range(0, (int) Math.pow(10, digitsCount)).parallel().filter(checker::check).count();
    }
}
