package lotto.model.lottogenerator;

import lotto.model.Lotto;

public class LottoFactory {
    public static Lotto create(String lottoNumbers) {
        return new ManualGenerator(lottoNumbers).generate();
    }

    public static Lotto create() {
        return new RandomGenerator().generate();
    }
}
