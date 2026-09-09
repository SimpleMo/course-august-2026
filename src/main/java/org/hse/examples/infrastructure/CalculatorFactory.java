package org.hse.examples.infrastructure;

import org.hse.examples.application.Calculator;

/**
 * Создаёт экземпляры {@link Calculator}, готовые к работе
 */
public interface CalculatorFactory {

    /**
     * Создаёт экземпляр {@link Calculator}
     */
    Calculator create(int digitsCount);
}

