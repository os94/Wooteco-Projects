package model;

import lotto.model.LottoNumber;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class LottoNumberTest {
    @Test
    void of_method_over_range() {
        assertThrows(IllegalArgumentException.class, () -> {
            LottoNumber.of(0);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            LottoNumber.of(46);
        });
    }

    @Test
    void of_method_normal() {
        assertThat(LottoNumber.of(1)).isInstanceOfAny(LottoNumber.class);
        assertThat(LottoNumber.of(45)).isInstanceOfAny(LottoNumber.class);
    }

    @Test
    void equal() {
        assertThat(LottoNumber.of(1)).isEqualByComparingTo(LottoNumber.of(1));
    }
}
