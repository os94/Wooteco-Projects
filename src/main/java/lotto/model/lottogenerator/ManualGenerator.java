package lotto.model.lottogenerator;

import lotto.model.Lotto;
import lotto.model.LottoNumber;

import java.util.ArrayList;
import java.util.List;

public class ManualGenerator implements LottoGenerator {
    private static final String COMMA = ",";

    @Override
    public Lotto generate(String lottoNumbers) {
        lottoNumbers = lottoNumbers.replace(" ", "");
        String[] numbers = lottoNumbers.split(COMMA);
        return new Lotto(getLottoNumber(numbers));
    }

    private List<LottoNumber> getLottoNumber(String[] numbers) {
        List<LottoNumber> lottoNumbers = new ArrayList<>();
        for (String number : numbers) {
            lottoNumbers.add(LottoNumber.of(Integer.parseInt(number)));
        }
        return lottoNumbers;
    }
}
