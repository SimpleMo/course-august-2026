package org.hse.examples.infrastructure;

import org.hse.examples.application.Calculator;
import org.hse.examples.application.CalculatorImpl;
import org.hse.examples.application.CalculatorStreamImpl;
import org.hse.examples.domain.Check;
import org.hse.examples.domain.CheckBySumm;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.function.BiFunction;
import java.util.function.Function;

@Configuration
public class Config {

    @Bean
    CalculatorFactory simpleCalculatorConstructor() {
        BiFunction<Check, Integer, Calculator> simpleCalculatorConstructor = CalculatorImpl::new;
        return new GeneralCalculatorFactory(simpleCalculatorConstructor);
    }

    @Bean
    @Primary
    CalculatorFactory streamCalculatorFactory() {
        BiFunction<Check, Integer, Calculator> streamCalculatorConstructor = CalculatorStreamImpl::new;
        return new GeneralCalculatorFactory(streamCalculatorConstructor);
    }

    @Bean
    Calculator simple6DigitsCalculator(@Qualifier("simpleCalculatorConstructor") CalculatorFactory factory) {
        return factory.create(6);
    }

    @Bean
    Calculator simple8DigitsCalculator(CalculatorFactory simpleCalculatorFactory) {
        return simpleCalculatorFactory.create(8);
    }

    @Bean
    Calculator stream6DigitsCalculator(
            @Qualifier("StreamCalculatorFactory")
            CalculatorFactory streamCalculatorFactory
    ) {
        return streamCalculatorFactory.create(6);
    }

    @Bean
    Calculator stream8DigitsCalculator(
            @Qualifier("StreamCalculatorFactory")
            CalculatorFactory streamCalculatorFactory
    ) {
        return streamCalculatorFactory.create(8);
    }

    @Bean
    Function<Integer, Check> checkBySumm() {
        return CheckBySumm::new;
    }
}
