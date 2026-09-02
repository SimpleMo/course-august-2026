package org.hse.examples;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * Тесты для {@link CheckBySumm}
 */
@DisplayName("Тесты для CheckBySumm")
class CheckBySummTest {

    private CheckBySumm service;

    @BeforeEach
    void setup() {
        // given
        service = new CheckBySumm(1000);
    }

    @AfterEach
    void tearDown() {
        service = null;
    }

    @Test
    @DisplayName("Проверяет, что билет 001001 счастливый")
    void test001001IsLucky() {
        // given
        // when
        var actual = service.check(1001);

        // then
        assertThat(actual).isTrue();
    }

    @Test
    @DisplayName("Проверяет, что билет 001011 не счастливый")
    void test001011IsNotLucky() {
        // given
        // when
        var actual = service.check(1011);

        // then
        assertThat(actual).isFalse();
    }

    @Test
    @DisplayName("Проверяет, что билет 000000 счастливый")
    void test000000IsLucky() {
        // given
        // when
        var actual = service.check(0);

        // then
        assertThat(actual).isTrue();
    }

    @Test
    @DisplayName("Проверяет, что билет 999999 счастливый")
    void test999999IsLucky() {
        // given
        // when
        var actual = service.check(999999);

        // then
        assertThat(actual).isTrue();
    }

}