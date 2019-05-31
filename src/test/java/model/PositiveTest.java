package model;

import lotto.model.Positive;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PositiveTest {
    Positive number4;
    Positive number2;

    @Test
    void constructor_with_zero() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Positive(0);
        });
    }

    @Test
    void constructor_with_negative() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Positive(-1);
        });
    }

    @Test
    void constructor_normal() {
        assertThat(new Positive(1)).isInstanceOfAny(Positive.class);
    }

    @BeforeEach
    void setUp() {
        number4 = new Positive(4);
        number2 = new Positive(2);
    }

    @Test
    void subtract() {
        assertThat(number4.subtract(number2)).isEqualTo(number2);
    }

    @Test
    void divide() {
        assertThat(number4.divide(number2)).isEqualTo(number2);
    }

    @Test
    void divide_result_is_zero() {
        assertThrows(IllegalArgumentException.class, () -> {
            number2.divide(number4);
        });
    }

    @Test
    void is_smaller_than() {
        assertThat(number2.isSmallerThan(number4)).isTrue();
        assertThat(number4.isSmallerThan(number2)).isFalse();
    }
}
