package org.hse.examples.rest;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hse.examples.application.Calculator;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.function.Predicate;

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
    public Collection<ResponseWithMessage> getTickets() {
        return process(calculatorNames);
    }

    @PostMapping
    public Collection<ResponseWithMessage> getTickets(@RequestBody Collection<String> names) {
        return process(names);
    }

    private @NonNull List<ResponseWithMessage> process(Collection<String> names) {
        List<ResponseWithMessage> result = new ArrayList<>(names.size());
        Set<String> existedCalculators = new HashSet<>(List.of(context.getBeanNamesForType(Calculator.class)));
        names
                .stream()
                .filter(existedCalculators::contains)
                .map(this::process)
                .map(resp -> ResponseWithMessage.builder().message(String.format("Калькулятор %s успешно обработан", resp.name())).response(resp).build())
                .forEach(result::add);
        names
                .stream()
                .filter(Predicate.not(existedCalculators::contains))
                .map(name -> ResponseWithMessage.builder().message(String.format("Калькулятор %s не реализован", name)).build())
                .forEach(result::add);

        return result;
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
        var calc = getCalculator(calculatorName);
        long start = System.currentTimeMillis();

        log.info(String.format("Работает %s из контроллера...", calculatorName));

        int count = calc.calculate();

        long end = System.currentTimeMillis();

        return new GetTicketsResponse(calculatorName, count, end - start);
    }

    private @NonNull Calculator getCalculator(String calculatorName) {
        try {
            return context.getBean(calculatorName, Calculator.class);
        } catch (BeansException e) {
            throw new CalculatorNotExistsException(calculatorName);
        }
    }

    @ExceptionHandler(CalculatorNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNoSuchElementException(CalculatorNotFoundException ex) {
        log.warn("Запрошен неизвестный калькулятор:", ex);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getName()));
    }

    @ExceptionHandler(CalculatorNotExistsException.class)
    public ResponseEntity<ErrorResponse> handleCalculatorNotExistsException(CalculatorNotExistsException ex) {
       log.error("Калькулятор не существует!", ex);
       return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(new ErrorResponse(HttpStatus.NOT_IMPLEMENTED.value(), ex.getName()));
    }

    record GetTicketsResponse(String name, Integer count, Long duration) { }

    @Getter
    @Builder
    @AllArgsConstructor
    @JsonInclude(JsonInclude.Include.ALWAYS)
    static class ResponseWithMessage {
        private String message;

        @JsonInclude(JsonInclude.Include.ALWAYS)
        private GetTicketsResponse response;
    }

    record ErrorResponse(int status, String message) { }

    static class CalculatorNotFoundException extends RuntimeException {
        @Getter
        private final String name;

        CalculatorNotFoundException(String name) {
            super(String.format("Калькулятор %s не найден!", name));
            this.name = name;
        }
    }

    static class CalculatorNotExistsException extends RuntimeException {
        @Getter
        private final String name;

        CalculatorNotExistsException(String name) {
            super(String.format("Калькулятор %s не существует!", name));
            this.name = name;
        }
    }

}
