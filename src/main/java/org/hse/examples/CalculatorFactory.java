package org.hse.examples;

/**
 * Создаёт экземпляры {@link Calculator}, готовые к работе
 */
public interface CalculatorFactory {

    /**
     * Создаёт экземпляр {@link Calculator}
     */
    Calculator create(int digitsCount);
}

/**
 * Реализация {@link Calculator}
 */
class CalculatorFactoryImpl implements CalculatorFactory {

    @Override
    public Calculator create(int digitsCount) {
        var denominator = (int) Math.pow(10, digitsCount / 2);
        var checker = new CheckBySumm(denominator);

        return new CalculatorImpl<>(checker::check, digitsCount);
    }
}
