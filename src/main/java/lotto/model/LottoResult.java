package lotto.model;

import java.util.ArrayList;
import java.util.List;

public class LottoResult {
    private static final String ERROR_LOTTO_NULL = "로또가 입력되지 않았습니다.";
    private static final String NEW_LINE = System.getProperty("line.separator");

    private static final List<Lotto> lottos = new ArrayList<>();

    public void add(Lotto lotto) {
        if (lotto == null) {
            throw new IllegalArgumentException(ERROR_LOTTO_NULL);
        }
        lottos.add(lotto);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Lotto lotto : lottos) {
            stringBuilder.append(lotto + NEW_LINE);
        }
        return stringBuilder.toString();
    }
}
