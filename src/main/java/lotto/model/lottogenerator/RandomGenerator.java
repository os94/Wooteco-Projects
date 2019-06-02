package lotto.model.lottogenerator;

import lotto.model.Lotto;
import lotto.model.LottoNumber;

import java.util.*;

public class RandomGenerator implements LottoGenerator {
    @Override
    public Lotto generate(String lottoNumbers) {
        Set<LottoNumber> uniqueLottoNumbers = new HashSet<>();
        while (uniqueLottoNumbers.size() != Lotto.NUMBER_OF_LOTTO_NUMBERS) {
            uniqueLottoNumbers.add(getLottoNumber(new Random()));
        }
        return new Lotto(setToOrderedList(uniqueLottoNumbers));
    }

    private LottoNumber getLottoNumber(Random random) {
        return LottoNumber.of(random.nextInt(LottoNumber.UPPER_LIMIT) + 1);
    }

    private List<LottoNumber> setToOrderedList(Set<LottoNumber> uniqueLottoNumbers) {
        List<LottoNumber> lottoNumbers = new ArrayList<>(uniqueLottoNumbers);
        Collections.sort(lottoNumbers);
        return lottoNumbers;
    }
}
