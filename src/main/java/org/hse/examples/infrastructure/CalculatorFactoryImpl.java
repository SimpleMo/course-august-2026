package org.hse.examples.infrastructure;

import org.hse.examples.application.Calculator;
import org.hse.examples.application.CalculatorImpl;
import org.hse.examples.domain.CheckBySumm;

/**
 * Реализация {@link CalculatorFactory}
 */
public class CalculatorFactoryImpl implements CalculatorFactory {

    @Override
    public Calculator create(int digitsCount) {
        var denominator = (int) Math.pow(10, digitsCount / 2);
        var checker = new CheckBySumm(denominator);

        return new CalculatorImpl(checker, digitsCount);
    }
}
