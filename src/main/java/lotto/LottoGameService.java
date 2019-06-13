package lotto;

import lotto.model.*;
import lotto.model.dao.LottosDAO;
import lotto.model.dao.RoundDAO;
import lotto.model.dao.WinningLottoDAO;
import lotto.view.WebViewBuilder;

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

        Lottos lottos = new LottosDAO().findAllLottosByRound(round);
        model.put("lottos", WebViewBuilder.of(lottos));

        WinningLotto winningLotto = new WinningLottoDAO().findWinningLottoByRound(round);
        model.put("winningLotto", winningLotto.toString());

        GameResult gameResult = LottoGame.match(lottos, winningLotto);
        model.put("resultStatistics", WebViewBuilder.of(gameResult));

        model.put("prizeMoney", gameResult.totalPrizeMoney());

        Money money = new RoundDAO().findMoneyByRound(round);
        model.put("profitRate", money.rateOfProfit(gameResult.totalPrizeMoney()));

        return model;
    }
}
