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
        numbers = Arrays.asList(LottoNumber.of(1), LottoNumber.of(2),
                LottoNumber.of(3), LottoNumber.of(4), LottoNumber.of(5), LottoNumber.of(6));
        lotto = new Lotto(numbers);
    }

    @Test
    void 당첨번호와_보너스볼이_겹치는_경우() {
        assertThrows(IllegalArgumentException.class, () -> {
            new WinningLotto(lotto, LottoNumber.of(3));
        });
    }

    @Test
    void 구입한로또와_당첨로또_매치() {
        numbers = Arrays.asList(LottoNumber.of(1), LottoNumber.of(2),
                LottoNumber.of(3), LottoNumber.of(4), LottoNumber.of(5), LottoNumber.of(45));
        Lotto winLotto = new Lotto(numbers);
        WinningLotto winningLotto = new WinningLotto(winLotto, LottoNumber.of(6));

        assertThat(winningLotto.match(lotto)).isEqualTo(Rank.SECOND);
    }

    @AfterEach
    void tearDown() {
        numbers = null;
        lotto = null;
    }
}
