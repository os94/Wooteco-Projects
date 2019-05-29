package model;

import lotto.model.Lotto;
import lotto.model.LottoNumber;
import lotto.model.WinningLotto;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class WinningLottoTest {
    @Test
    void duplicate_winning_lotto() {
        List<LottoNumber> numbers = Arrays.asList(new LottoNumber(1), new LottoNumber(2),
                new LottoNumber(3), new LottoNumber(4), new LottoNumber(5), new LottoNumber(6));
        Lotto lotto = new Lotto(numbers);

        assertThrows(IllegalArgumentException.class, () -> {
            new WinningLotto(lotto, new LottoNumber(3));
        });
    }
}
