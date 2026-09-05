package org.hse.examples.business;

public class CheckBySumm {
    private final int denominator;

    CheckBySumm(int denominator) {
        if (denominator < 0) {
            throw new IllegalArgumentException("Знаменатель меньше нуля!");
        }
        if (denominator % 10 != 0) {
            throw new IllegalArgumentException("Знаменатель должен быть кратен десяти!");
        }
        this.denominator = denominator;
    }

    public boolean check(int number) {
        int maxNumber = denominator * denominator;
        if (number < 0 || number >= maxNumber) {
            throw new IllegalArgumentException(String.format("Номер %d вне диапазона [0, %d)", number, maxNumber));
        }

        return get_digits_summ(number / denominator) == get_digits_summ(number % denominator);
    }

    private int get_digits_summ(int number) {
        int digitsSumm = 0;
        for(int i = number; i > 0; i /= 10) {
            digitsSumm += i % 10;
        }

        return digitsSumm;
    }
}
