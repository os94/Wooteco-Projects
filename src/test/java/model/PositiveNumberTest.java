package model;

import lotto.model.PositiveNumber;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PositiveNumberTest {
    PositiveNumber number4;
    PositiveNumber number2;

    @Test
    void 음수로_생성시_예외처리() {
        assertThrows(IllegalArgumentException.class, () -> {
            new PositiveNumber(-1);
        });
    }

    @Test
    void 정상적으로_생성하는_경우() {
        assertThat(new PositiveNumber(1)).isInstanceOfAny(PositiveNumber.class);
    }

    @BeforeEach
    void setUp() {
        number4 = new PositiveNumber(4);
        number2 = new PositiveNumber(2);
    }

    @Test
    void 뺄셈_계산() {
        assertThat(number4.subtract(number2)).isEqualTo(number2);
    }

    @Test
    void 나눗셈_계산() {
        assertThat(number4.divide(number2)).isEqualTo(number2);
    }

    @Test
    void 부등호_비교_계산() {
        assertThat(number2.lessThan(number4)).isTrue();
        assertThat(number4.lessThan(number2)).isFalse();
    }

    @AfterEach
    void tearDown() {
        number4 = null;
        number2 = null;
    }
}
