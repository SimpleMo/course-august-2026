package org.hse.examples;

import org.hse.examples.business.ApplicationContext;
import org.hse.examples.business.Calculator;

import java.util.List;
import java.util.Optional;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    private static final ApplicationContext context = ApplicationContext.getContext();

    public static void main(String[] args) {
        var prefs = List.of("stream", "simple");
        var numbers = List.of(6, 8);

        prefs.forEach(pref -> numbers.forEach(num -> process(pref, num)));
    }

    private static void process(String prefix, Integer number) {
        long start = System.currentTimeMillis();

        String calculatorName = String.format("%s%dDigitsCalculator", prefix, number);
        Optional<Calculator> calculator = context.getInstance(calculatorName, Calculator.class);

        System.out.printf("Работает %s...\n", calculatorName);

        int count = calculator.map(Calculator::calculate).orElseThrow();

        long end = System.currentTimeMillis();

        String output = String.format("""
        Всего %d счастливых билетов.
        Расчёт продолжался %d мс.""", count, end - start);

        System.out.println(output);
    }
}