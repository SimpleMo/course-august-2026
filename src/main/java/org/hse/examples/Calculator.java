package org.hse.examples;

import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.IntStream;

interface Calculator {

    int calculate();
}

class CalculatorImpl<T extends Predicate<Integer>> implements Calculator {

    private final T checker;
    private final int digitsCount;

    CalculatorImpl(T checker, int digitsCount) {
        this.checker = checker;
        this.digitsCount = digitsCount;
    }

    CalculatorImpl(Function<Integer, T> supplier, int digitsCount) {
        this.checker = supplier.apply(digitsCount);
        this.digitsCount = digitsCount;
    }

    @Override
    public int calculate() {
        int count = 0;
        for(int i = 0; i < Math.pow(10, digitsCount); i++) {
            if (checker.test(i)) {
                count++;
            }
        }

        return count;
    }
}


class CalculatorStreamImpl<T extends Predicate<Integer>> implements Calculator {
    private final T checker;
    private final int digitsCount;

    CalculatorStreamImpl(T checker, int digitsCount) {
        this.checker = checker;
        this.digitsCount = digitsCount;
    }

    @Override
    public int calculate() {
        return (int) IntStream.range(0, (int) Math.pow(10, digitsCount)).parallel().filter(checker::test).count();
    }
}

