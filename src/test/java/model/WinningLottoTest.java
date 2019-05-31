package model;

import lotto.model.Lotto;
import lotto.model.LottoNumber;
import lotto.model.Rank;
import lotto.model.WinningLotto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class WinningLottoTest {
    List<LottoNumber> numbers;
    Lotto lotto;

    @BeforeEach
    void setUp() {
        numbers = Arrays.asList(new LottoNumber(1), new LottoNumber(2), new LottoNumber(3)
                , new LottoNumber(4), new LottoNumber(5), new LottoNumber(6));
        lotto = new Lotto(numbers);
    }

    @Test
    void duplicate_winning_lotto() {
        assertThrows(IllegalArgumentException.class, () -> {
            new WinningLotto(lotto, new LottoNumber(3));
        });
    }

    @Test
    void match() {
        numbers = Arrays.asList(new LottoNumber(1), new LottoNumber(2), new LottoNumber(3)
                , new LottoNumber(4), new LottoNumber(5), new LottoNumber(45));
        Lotto winLotto = new Lotto(numbers);
        WinningLotto winningLotto = new WinningLotto(winLotto, new LottoNumber(6));

        assertThat(winningLotto.match(lotto)).isEqualTo(Rank.SECOND);
    }

    @AfterEach
    void tearDown() {
        numbers = null;
        lotto = null;
    }
}
