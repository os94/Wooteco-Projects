package model;

import lotto.model.Lotto;
import lotto.model.LottoGenerator;
import lotto.model.LottoNumber;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class LottoGeneratorTest {
    @Test
    void generate_with_input_numbers() {
        List<LottoNumber> numbers = Arrays.asList(new LottoNumber(1), new LottoNumber(2),
                new LottoNumber(3), new LottoNumber(4), new LottoNumber(5), new LottoNumber(6));
        Lotto lotto = new Lotto(numbers);

        assertThat(LottoGenerator.generate("1, 2, 3, 4, 5, 6")).isEqualTo(lotto);
    }
}
