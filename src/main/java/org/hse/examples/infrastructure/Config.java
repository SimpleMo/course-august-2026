package org.hse.examples.infrastructure;

import lombok.extern.slf4j.Slf4j;
import org.hse.examples.application.Calculator;
import org.hse.examples.application.CalculatorImpl;
import org.hse.examples.application.CalculatorStreamImpl;
import org.hse.examples.domain.Check;
import org.hse.examples.domain.CheckBySumm;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;

import java.util.function.BiFunction;
import java.util.function.Function;

@Slf4j
@Configuration
public class Config {

    @Bean
    CalculatorFactory simpleCalculatorConstructor() {
        log.debug("Создан simpleCalculatorConstructor");
        BiFunction<Check, Integer, Calculator> simpleCalculatorConstructor = CalculatorImpl::new;
        return new GeneralCalculatorFactory(simpleCalculatorConstructor);
    }

    @Bean
    @Primary
    CalculatorFactory streamCalculatorFactory() {
        log.debug("Создан streamCalculatorFactory");
        BiFunction<Check, Integer, Calculator> streamCalculatorConstructor = CalculatorStreamImpl::new;
        return new GeneralCalculatorFactory(streamCalculatorConstructor);
    }

    @Bean
    @Scope("prototype")
    Calculator simple6DigitsCalculator(@Qualifier("simpleCalculatorConstructor") CalculatorFactory factory) {
        log.debug("Создан simple6DigitsCalculator");
        return factory.create(6);
    }

    @Bean
    @Scope("prototype")
    Calculator simple8DigitsCalculator(CalculatorFactory simpleCalculatorFactory) {
        log.debug("Создан simple8DigitsCalculator");
        return simpleCalculatorFactory.create(8);
    }

    @Bean
    @Scope("prototype")
    Calculator stream6DigitsCalculator(
            @Qualifier("StreamCalculatorFactory")
            CalculatorFactory streamCalculatorFactory
    ) {
        log.debug("Создан stream6DigitsCalculator");
        return streamCalculatorFactory.create(6);
    }

    @Bean
    @Scope("prototype")
    Calculator stream8DigitsCalculator(
            @Qualifier("StreamCalculatorFactory")
            CalculatorFactory streamCalculatorFactory
    ) {
        log.debug("Создан stream8DigitsCalculator");
        return streamCalculatorFactory.create(8);
    }

    @Bean
    Function<Integer, Check> checkBySumm() {
        log.debug("Создан checkBySumm");
        return CheckBySumm::new;
    }
}
