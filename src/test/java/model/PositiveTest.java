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
    void calculate_per() {
        Positive number = new Positive(2000);
        assertThat(number.per(1000)).isEqualTo(2);
    }
}
