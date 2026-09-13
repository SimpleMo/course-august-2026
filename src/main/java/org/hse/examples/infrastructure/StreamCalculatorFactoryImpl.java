package org.hse.examples.infrastructure;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hse.examples.application.Calculator;
import org.hse.examples.application.CalculatorStreamImpl;
import org.hse.examples.domain.Check;
import org.springframework.stereotype.Service;

import java.util.function.Function;

/**
 * Реализация {@link CalculatorFactory} для калькулятора на стримах
 */
@Slf4j
@RequiredArgsConstructor
@Service("StreamCalculatorFactory")
public class StreamCalculatorFactoryImpl implements CalculatorFactory{
    private final Function<Integer, Check> checkBySumm;

    @PostConstruct
    void postConstruct() {
        log.debug("Создан StreamCalculatorFactory");
    }

    @Override
    public Calculator create(int digitsCount) {
        var denominator = (int) Math.pow(10, digitsCount / 2);
        var checker = checkBySumm.apply(denominator);

        return new CalculatorStreamImpl(checker, digitsCount);
    }
}
