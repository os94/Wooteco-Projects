package lotto.model;

import java.util.*;

public class LottoGenerator {
    public static List<Lotto> generate(int countOfLotto) {
        List<Lotto> lottos = new ArrayList<>();

        for (int i = 0; i < countOfLotto; i++) {
            lottos.add(getRandomLotto());
        }
        return lottos;
    }

    private static Lotto getRandomLotto() {
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
