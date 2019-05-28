package model;

import lotto.model.Money;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MoneyTest {
    @Test
    void constructor_with_blank() {
        assertThatIllegalArgumentException().isThrownBy(() -> {
            new Money(null);
            new Money("");
            new Money(" ");
        });
    }

    @Test
    void constructor_with_negative() {
        assertThrows(IllegalArgumentException.class, () -> {
           new Money("-1000");
        });
    }

    @Test
    void constructor_with_character() {
        assertThrows(NumberFormatException.class, () -> {
            new Money("abc");
        });
    }

    @Test
    void constructor_normal() throws Exception {
        assertThat(new Money("1000")).isInstanceOfAny(Money.class);
    }
}
