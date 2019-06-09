package lotto.model;

import java.util.ArrayList;
import java.util.List;

public class Lottos {
    private static final String ERROR_LOTTO_NULL = "로또가 입력되지 않았습니다.";

    private final List<Lotto> lottos;

    public Lottos() {
        lottos = new ArrayList<>();
    }

    public List<Lotto> getLottos() {
        return lottos;
    }

    public void add(Lotto lotto) {
        if (lotto == null) {
            throw new IllegalArgumentException(ERROR_LOTTO_NULL);
        }
        lottos.add(lotto);
    }

    public void add(Lottos other) {
        if (other == null) {
            throw new IllegalArgumentException(ERROR_LOTTO_NULL);
        }
        lottos.addAll(other.getLottos());
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Lotto lotto : lottos) {
            stringBuilder.append(lotto).append("\n");
        }
        return stringBuilder.toString();
    }
}
