package org.hse.examples.infrastructure;

import org.hse.examples.application.Calculator;
import org.hse.examples.domain.Check;
import org.hse.examples.domain.CheckBySumm;

import java.util.function.BiFunction;

/**
 * Универсальная фабрика {@link Calculator}
 */
public class GeneralCalculatorFactory implements CalculatorFactory{
    private final BiFunction<Check, Integer, Calculator> constructor;

    public GeneralCalculatorFactory(BiFunction<Check, Integer, Calculator> constructor) {
        this.constructor = constructor;
    }

    @Override
    public Calculator create(int digitsCount) {
        var denominator = (int) Math.pow(10, digitsCount / 2);
        var checker = new CheckBySumm(denominator);

        return constructor.apply(checker, digitsCount);
    }
}
