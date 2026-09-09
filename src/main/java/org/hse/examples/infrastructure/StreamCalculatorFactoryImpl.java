package org.hse.examples.infrastructure;

import org.hse.examples.application.Calculator;
import org.hse.examples.application.CalculatorStreamImpl;
import org.hse.examples.domain.CheckBySumm;

/**
 * Реализация {@link CalculatorFactory} для калькулятора на стримах
 */
public class StreamCalculatorFactoryImpl implements CalculatorFactory{
    @Override
    public Calculator create(int digitsCount) {
        var denominator = (int) Math.pow(10, digitsCount / 2);
        var checker = new CheckBySumm(denominator);

        return new CalculatorStreamImpl(checker, digitsCount);
    }
}
