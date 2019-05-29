package lotto.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class LottoGenerator {
    public static Lotto generate() {
        Set<LottoNumber> lottoNumbers = new HashSet<>();

        while (lottoNumbers.size() != Lotto.NUMBER_OF_LOTTO_NUMBERS) {
            lottoNumbers.add(getLottoNumber(new Random()));
        }
        return new Lotto(new ArrayList<>(lottoNumbers));
    }

    private static LottoNumber getLottoNumber(Random random) {
        return new LottoNumber(random.nextInt(LottoNumber.UPPER_LIMIT) + 1);
    }
}
