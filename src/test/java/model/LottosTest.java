package model;

import lotto.model.Lotto;
import lotto.model.LottoNumber;
import lotto.model.Lottos;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class LottosTest {
    Lottos lottos;
    Lotto lotto;

    @BeforeEach
    void setUp() {
        lottos = new Lottos();

        List<LottoNumber> numbers = Arrays.asList(LottoNumber.of(1), LottoNumber.of(2),
                LottoNumber.of(3), LottoNumber.of(4), LottoNumber.of(5), LottoNumber.of(6));
        lotto = new Lotto(numbers);
    }

    @Test
    void add_lotto() {
        lottos.add(lotto);
        assertThat(lottos.getLottos().size()).isEqualTo(1);
    }

    @Test
    void add_other_lottos() {
        Lottos otherLottos = new Lottos();
        otherLottos.add(lotto);

        assertThat(otherLottos.getLottos().size()).isEqualTo(1);
    }
}
