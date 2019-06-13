package lotto;

import lotto.model.dao.RoundDAO;

import java.util.HashMap;
import java.util.Map;

public class LottoGameService {
    public Map<String, Object> currentRound() {
        Map<String, Object> model = new HashMap<>();
        model.put("currentRound", new RoundDAO().findCurrentRound());
        return model;
    }
}
