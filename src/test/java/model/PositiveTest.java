package model;

import lotto.model.Positive;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PositiveTest {
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

    @Test
    void subtract() {
        Positive number1 = new Positive(4);
        Positive number2 = new Positive(2);
        assertThat(number1.subtract(number2)).isEqualTo(number2);
    }

    @Test
    void divide() {
        Positive number1 = new Positive(4);
        Positive number2 = new Positive(2);
        assertThat(number1.divide(number2)).isEqualTo(number2);
    }
}
