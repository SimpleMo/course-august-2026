package org.hse.examples.rest;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hse.examples.application.Calculator;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

/**
 * Rest-контроллер для работы со счастливыми билетами
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/tickets")
public class CalculatorController {
    private final Collection<String> calculatorNames;
    private final ApplicationContext context;

    @GetMapping
    public Collection<GetTicketsResponse> getTickets() {
        return calculatorNames.stream().map(this::process).toList();
    }

    @GetMapping("/{calculatorName}")
    public GetTicketsResponse getTicket(@PathVariable String calculatorName) {
        return calculatorNames
                .stream()
                .filter(calculatorName::equals)
                .findFirst()
                .map(this::process)
                .orElseThrow(() -> new CalculatorNotFoundException(calculatorName));
    }

    private GetTicketsResponse process(String calculatorName) {
        var calc = context.getBean(calculatorName, Calculator.class);
        long start = System.currentTimeMillis();

        log.info(String.format("Работает %s из контроллера...", calculatorName));

        int count = calc.calculate();

        long end = System.currentTimeMillis();

        return new GetTicketsResponse(calculatorName, count, end - start);
    }

    @ExceptionHandler(CalculatorNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNoSuchElementException(CalculatorNotFoundException ex) {
        log.warn("Запрошен неизвестный калькулятор:", ex);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getName()));
    }

    record GetTicketsResponse(String name, Integer count, Long duration) { }

    record ErrorResponse(int status, String message) { }

    static class CalculatorNotFoundException extends RuntimeException {
        @Getter
        private final String name;

        CalculatorNotFoundException(String name) {
            super(String.format("Калькулятор %s не найден!", name));
            this.name = name;
        }
    }

}
