package org.hse.examples;

import java.util.function.Function;
import java.util.function.Predicate;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        int digitsCount = 6;
        int denominator = (int) Math.pow(10, (double) digitsCount / 2);

        long start = System.currentTimeMillis();
        CheckBySumm checker = new CheckBySumm(denominator);
        Calculator calculator = new CalculatorStreamImpl<>(checker::check, digitsCount);
        int count = calculator.calculate();

        long end = System.currentTimeMillis();

        String output = String.format("""
                Всего %d счастливых билетов.
                Расчёт продолжался %d мс.""", count, end - start);

        System.out.print(output);
    }
}