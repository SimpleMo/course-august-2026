package org.hse.examples;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hse.examples.application.Calculator;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.Collection;
import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
@Slf4j
@SpringBootApplication
@RequiredArgsConstructor
public class Main implements CommandLineRunner {

    private final Collection<String> calculatorNames =
            List.of("stream6DigitsCalculator", "simple8DigitsCalculator", "simple6DigitsCalculator","stream8DigitsCalculator");

    private final ApplicationContext context;

    public static void main(String[] args) {
        SpringApplication.run(Main.class);
    }

    @Override
    public void run(String... args) throws Exception {
        calculatorNames.forEach(this::process);
    }

    private void process(String name) {
        var calc = context.getBean(name, Calculator.class);
        Main.process(name, calc);
    }

    private static void process(Calculator calculator) {
        long start = System.currentTimeMillis();

        log.info(String.format("Работает %s...", calculator.toString()));

        int count = calculator.calculate();

        long end = System.currentTimeMillis();

        String output = String.format("""
        Всего %d счастливых билетов. Расчёт продолжался %d мс.""", count, end - start);

        log.info(output);
    }

    private static void process(String calculatorName, Calculator calculator) {
        long start = System.currentTimeMillis();

        log.info(String.format("Работает %s...", calculatorName));

        int count = calculator.calculate();

        long end = System.currentTimeMillis();

        String output = String.format("""
        Всего %d счастливых билетов. Расчёт продолжался %d мс.""", count, end - start);

        log.info(output);
    }
}