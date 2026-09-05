package org.hse.examples;

import org.hse.examples.business.Calculator;
import org.hse.examples.business.CalculatorFactory;
import org.hse.examples.business.CalculatorFactoryImpl;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    private static CalculatorFactory factory = new CalculatorFactoryImpl();

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        Calculator calculator = factory.create(8);
        int count = calculator.calculate();

        long end = System.currentTimeMillis();

        String output = String.format("""
                Всего %d счастливых билетов.
                Расчёт продолжался %d мс.""", count, end - start);

        System.out.print(output);
    }
}