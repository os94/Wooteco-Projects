package lotto;

import lotto.model.dao.RoundDAO;

import java.util.HashMap;
import java.util.Map;

public class LottoGameService {
    public Map<String, Object> getCurrentRound() {
        Map<String, Object> model = new HashMap<>();
        model.put("currentRound", new RoundDAO().findCurrentRound());
        return model;
    }

    public Map<String, Object> getResult(String input_round) {
        Map<String, Object> model = new HashMap<>();
        int round = Integer.parseInt(input_round);

        model.put("round", round);
        


        return model;
    }
}
