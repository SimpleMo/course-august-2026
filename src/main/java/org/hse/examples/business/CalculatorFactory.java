package org.hse.examples.business;

/**
 * Создаёт экземпляры {@link Calculator}, готовые к работе
 */
public interface CalculatorFactory {

    /**
     * Создаёт экземпляр {@link Calculator}
     */
    Calculator create(int digitsCount);
}

