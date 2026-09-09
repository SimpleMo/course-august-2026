package org.hse.examples.application;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hse.examples.domain.Check;

@Data
@Setter(AccessLevel.NONE)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CalculatorImpl implements Calculator {

    @ToString.Exclude
    Check checker;
    int digitsCount;

    @Override
    public int calculate() {
        int count = 0;
        for (int i = 0; i < Math.pow(10, digitsCount); i++) {
            if (checker.check(i)) {
                count++;
            }
        }

        return count;
    }
}
