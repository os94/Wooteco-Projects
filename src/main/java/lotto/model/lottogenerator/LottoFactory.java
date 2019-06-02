package lotto.model.lottogenerator;

import lotto.model.Lotto;
import org.apache.commons.lang3.StringUtils;

public class LottoFactory {
    public static Lotto create(String lottoNumbers) {
        if (StringUtils.isBlank(lottoNumbers)) {
            return new RandomGenerator().generate(lottoNumbers);
        }
        return new ManualGenerator().generate(lottoNumbers);
    }
}
