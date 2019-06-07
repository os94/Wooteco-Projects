package model;

import lotto.model.LottoNumber;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class LottoNumberTest {
    @Test
    void 로또숫자의_범위를_벗어나는_경우_예외처리() {
        assertThrows(IllegalArgumentException.class, () -> {
            LottoNumber.of(0);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            LottoNumber.of(46);
        });
    }

    @Test
    void 로또숫자의_범위에_들어가는_경우() {
        assertThat(LottoNumber.of(1)).isInstanceOfAny(LottoNumber.class);
        assertThat(LottoNumber.of(45)).isInstanceOfAny(LottoNumber.class);
    }

    @Test
    void 같은_로또숫자인지_비교() {
        assertThat(LottoNumber.of(1)).isEqualByComparingTo(LottoNumber.of(1));
    }
}
