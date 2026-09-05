package org.hse.examples.business;

import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

/**
 * Тесты для {@link CheckBySumm}
 */
@DisplayName("Тесты для CheckBySumm")
class CheckBySummParametrizedTest {

    @Nested
    @DisplayName("Проверяем работу метода CheckBySumm.check для различных входных данных")
    class CheckTest {

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

        @CsvSource({
                "000000, true",
                "999999, true",
                "1001, true",
                "1011, false"
        })
        @DisplayName("Проверяет набор билетов")
        @ParameterizedTest(name = "Билет с номером {0} счастливый? {1}")
        void someOfTicketsAreLuckyButOthersAreNot(int number, boolean expected) {
            // given
            // when
            var actual = service.check(number);

            // then
            assertThat(actual).isEqualTo(expected);
        }

        @CsvSource({"1000000", "-1", "-1001"})
        @ParameterizedTest(name = "Билет с номером {0}")
        @DisplayName("Проверяет работу метода в случае, когда номер не входит в допустимый диапазон")
        void numbersShouldBeInPermissibleRange(int number) {
            // given
            ThrowableAssert.ThrowingCallable call = () -> service.check(number);

            // when
            // then
            assertThatThrownBy(call)
                    .hasMessageContaining("Номер")
                    .hasMessageContaining("вне")
                    .hasMessageContaining("диапазона");
        }
    }

    @Nested
    @DisplayName("Проверяет работу конструктора")
    class ConstructorTest {

        @CsvSource({"100", "1000", "10000"})
        @ParameterizedTest(name = "Знаменатель - {0}")
        @DisplayName("Можем создать экземпляр для положительного параметра")
        void constructorParamMayBePositive(int denominator) {
            // given
            // when
            var actual = new CheckBySumm(denominator);

            // then
            assertThat(actual).isNotNull();
        }

        @Test
        @DisplayName("Знаменатель обязательно должен быть положительным")
        void denominatorHasToBePositive() {
            // given
            ThrowableAssert.ThrowingCallable call = () -> new CheckBySumm(-10);

            // when
            // then
            assertThatThrownBy(call).hasMessageContaining("Знаменатель меньше нуля!");
        }

        @Test
        @DisplayName("Знаменатель должен быть кратен десяти")
        void denominatorShouldBeDividedByTen() {
            // given
            ThrowableAssert.ThrowingCallable call = () -> new CheckBySumm(55);

            // when
            // then
            assertThatThrownBy(call).hasMessageContaining("Знаменатель должен быть кратен десяти!");
        }
    }
}