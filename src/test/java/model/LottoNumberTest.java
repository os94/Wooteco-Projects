package model;

import lotto.model.LottoNumber;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class LottoNumberTest {
    @Test
    void constructor_out_of_range() {
        assertThrows(IllegalArgumentException.class, () -> {
            new LottoNumber(0);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            new LottoNumber(46);
        });
    }

    @Test
    void constructor_normal() {
        assertThat(new LottoNumber(1)).isInstanceOfAny(LottoNumber.class);
        assertThat(new LottoNumber(45)).isInstanceOfAny(LottoNumber.class);
    }
}
