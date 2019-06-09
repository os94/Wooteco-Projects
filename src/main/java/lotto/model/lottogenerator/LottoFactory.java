package lotto.model.lottogenerator;

import lotto.model.Lotto;

public class LottoFactory {
    public static Lotto createManualGenerator(String lottoNumbers) {
        return new ManualGenerator(lottoNumbers).generate();
    }

    public static Lotto createRandomGenerator() {
        return new RandomGenerator().generate();
    }
}
