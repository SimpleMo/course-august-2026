package org.hse.examples.business;

import java.util.function.BiFunction;
import java.util.function.Predicate;

/**
 * Универсальная фабрика {@link Calculator}
 */
public class GeneralCalculatorFactory implements CalculatorFactory{
    private final BiFunction<Predicate<Integer>, Integer, Calculator> constructor;

    public GeneralCalculatorFactory(BiFunction<Predicate<Integer>, Integer, Calculator> constructor) {
        this.constructor = constructor;
    }

    @Override
    public Calculator create(int digitsCount) {
        var denominator = (int) Math.pow(10, digitsCount / 2);
        var checker = new CheckBySumm(denominator);

        return constructor.apply(checker::check, digitsCount);
    }
}
