package model;

import lotto.model.Lotto;
import lotto.model.LottoNumber;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

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
        List<LottoNumber> fiveNumbers = Arrays.asList(new LottoNumber(1), new LottoNumber(2),
                new LottoNumber(3), new LottoNumber(4), new LottoNumber(5));

        assertThrows(IllegalArgumentException.class, () -> {
            new Lotto(fiveNumbers);
        });
    }

    @Test
    void constructor_with_duplicate_numbers() {
        List<LottoNumber> duplicateNumbers = Arrays.asList(new LottoNumber(1), new LottoNumber(2),
                new LottoNumber(3), new LottoNumber(4), new LottoNumber(5), new LottoNumber(1));

        assertThrows(IllegalArgumentException.class, () -> {
            new Lotto(duplicateNumbers);
        });
    }
}
