package org.hse.examples;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.function.Predicate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

/**
 * Тесты для {@link CalculatorImpl}
 */
@DisplayName("Тесты для CalculatorImpl")
class CalculatorImplTest {
    private CheckBySumm check;

    @BeforeEach
    void setup() {
        // given
        check = mock(CheckBySumm.class);
        when(check.check(anyInt())).thenReturn(true);
    }

    @CsvSource({"2", "4", "6"})
    @ParameterizedTest(name = "Количество цифр - {0}")
    @DisplayName("Проверяет подсчёт счастливых билетов")
    void allTicketsShouldBeCounted(int digitsCount) {
        // given
        Calculator service = new CalculatorImpl<>(check::check, digitsCount);
        int expected = (int) Math.pow(10, digitsCount);

        // when
        int actual = service.calculate();

        // then
        assertThat(actual).isEqualTo(expected);
        verify(check, times(expected)).check(anyInt());
    }



}