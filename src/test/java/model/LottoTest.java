package model;

import lotto.model.Lotto;
import lotto.model.LottoNumber;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class LottoTest {
    @Test
    void constructor_with_null() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Lotto(null);
        });
    }

    @Test
    void constructor_with_mismatch_size() {
        List<LottoNumber> fiveNumbers = Arrays.asList(LottoNumber.of(1), LottoNumber.of(2),
                LottoNumber.of(3), LottoNumber.of(4), LottoNumber.of(5));

        assertThrows(IllegalArgumentException.class, () -> {
            new Lotto(fiveNumbers);
        });
    }

    @Test
    void constructor_with_duplicate_numbers() {
        List<LottoNumber> duplicateNumbers = Arrays.asList(LottoNumber.of(1), LottoNumber.of(2),
                LottoNumber.of(3), LottoNumber.of(4), LottoNumber.of(5), LottoNumber.of(1));

        assertThrows(IllegalArgumentException.class, () -> {
            new Lotto(duplicateNumbers);
        });
    }

    @Test
    void contains() {
        List<LottoNumber> numbers = Arrays.asList(LottoNumber.of(1), LottoNumber.of(2),
                LottoNumber.of(3), LottoNumber.of(4), LottoNumber.of(5), LottoNumber.of(6));
        Lotto lotto = new Lotto(numbers);

        assertThat(lotto.contains(LottoNumber.of(3))).isTrue();
        assertThat(lotto.contains(LottoNumber.of(7))).isFalse();
    }
}
