package org.hse.examples.business;

/**
 * Реализация {@link CalculatorFactory} для калькулятора на стримах
 */
public class StreamCalculatorFactoryImpl implements CalculatorFactory{
    @Override
    public Calculator create(int digitsCount) {
        var denominator = (int) Math.pow(10, digitsCount / 2);
        var checker = new CheckBySumm(denominator);

        return new CalculatorStreamImpl<>(checker::check, digitsCount);
    }
}
