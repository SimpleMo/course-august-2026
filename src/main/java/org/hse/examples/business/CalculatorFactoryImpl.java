package org.hse.examples.business;

/**
 * Реализация {@link CalculatorFactory}
 */
public class CalculatorFactoryImpl implements CalculatorFactory {

    @Override
    public Calculator create(int digitsCount) {
        var denominator = (int) Math.pow(10, digitsCount / 2);
        var checker = new CheckBySumm(denominator);

        return new CalculatorImpl<>(checker::check, digitsCount);
    }
}
