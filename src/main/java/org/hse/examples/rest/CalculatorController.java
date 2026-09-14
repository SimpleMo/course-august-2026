package org.hse.examples.rest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hse.examples.application.Calculator;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;

/**
 * Rest-контроллер для работы со счастливыми билетами
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/tickets")
public class CalculatorController {
    private final Collection<String> calculatorNames =
            List.of("stream6DigitsCalculator", "simple8DigitsCalculator", "simple6DigitsCalculator","stream8DigitsCalculator");
    private final ApplicationContext context;

    @GetMapping
    public Collection<GetTicketsResponse> getTickets() {
        return calculatorNames.stream().map(this::process).toList();
    }

    @GetMapping("/{calculatorName}")
    public GetTicketsResponse getTicket(@PathVariable String calculatorName) {
        return calculatorNames.stream().filter(calculatorName::equals).findFirst().map(this::process).orElseThrow();
    }

    private GetTicketsResponse process(String calculatorName) {
        var calc = context.getBean(calculatorName, Calculator.class);
        long start = System.currentTimeMillis();

        log.info(String.format("Работает %s из контроллера...", calculatorName));

        int count = calc.calculate();

        long end = System.currentTimeMillis();

        return new GetTicketsResponse(calculatorName, count, end - start);
    }

    record GetTicketsResponse(String name, Integer count, Long duration) { }

}
