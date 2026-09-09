package org.hse.examples.domain;

public class CheckBySumm implements Check {
    private final int denominator;

    public CheckBySumm(int denominator) {
        if (denominator < 0) {
            throw new IllegalArgumentException("Знаменатель меньше нуля!");
        }
        if (denominator % 10 != 0) {
            throw new IllegalArgumentException("Знаменатель должен быть кратен десяти!");
        }
        this.denominator = denominator;
    }

    @Override
    public boolean check(int number) {
        int maxNumber = denominator * denominator;
        if (number < 0 || number >= maxNumber) {
            throw new IllegalArgumentException(String.format("Номер %d вне диапазона [0, %d)", number, maxNumber));
        }

        return getDigitsSumm(number / denominator) == getDigitsSumm(number % denominator);
    }

    private int getDigitsSumm(int number) {
        int digitsSumm = 0;
        for(int i = number; i > 0; i /= 10) {
            digitsSumm += i % 10;
        }

        return digitsSumm;
    }
}
