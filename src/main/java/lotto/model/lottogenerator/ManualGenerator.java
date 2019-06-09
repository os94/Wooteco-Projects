package lotto.model.lottogenerator;

import lotto.model.Lotto;
import lotto.model.LottoNumber;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ManualGenerator implements LottoGenerator {
    private static final String COMMA = ",";
    private final String[] numbers;

    public ManualGenerator(String input) {
        input = StringUtils.deleteWhitespace(input);
        this.numbers = input.split(COMMA);
    }

    @Override
    public Lotto generate() {
        List<LottoNumber> lottoNumbers = Arrays.stream(numbers)
                .map(number -> LottoNumber.of(Integer.parseInt(number)))
                .collect(Collectors.toList());

        return new Lotto(lottoNumbers);
    }
}
