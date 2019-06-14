package lotto;

import lotto.model.*;
import lotto.model.dao.LottosDAO;
import lotto.model.dao.RoundDAO;
import lotto.model.dao.WinningLottoDAO;
import lotto.model.lottogenerator.LottoFactory;
import lotto.view.WebViewBuilder;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class LottoGameService {
    public int recentRound() {
        return new RoundDAO().recentRound();
    }

    public Map<String, Object> result(String input_round) {
        Map<String, Object> model = new HashMap<>();
        int round = Integer.parseInt(input_round);
        Lottos lottos = new LottosDAO().findAllLottosByRound(round);
        WinningLotto winningLotto = new WinningLottoDAO().findWinningLottoByRound(round);
        GameResult gameResult = LottoGame.match(lottos, winningLotto);
        Money money = new RoundDAO().findMoneyByRound(round);

        model.put("round", round);
        model.put("lottos", WebViewBuilder.of(lottos));
        model.put("winningLotto", winningLotto.toString());
        model.put("resultStatistics", WebViewBuilder.of(gameResult));
        model.put("prizeMoney", gameResult.totalPrizeMoney());
        model.put("profitRate", money.rateOfProfit(gameResult.totalPrizeMoney()));
        return model;
    }

    public void buyLotto(String input_money, String[] input_manual_lottos) {
        Money money = new Money(Integer.parseInt(input_money));
        PositiveNumber countOfManualLotto = new PositiveNumber(input_manual_lottos.length);
        PositiveNumber countOfAutoLotto = money.countOfLotto().subtract(countOfManualLotto);
        Lottos lottos = LottoGame.buy(Arrays.asList(input_manual_lottos), countOfAutoLotto);

        new RoundDAO().updateRoundWith(input_money);
        for (Lotto lotto : lottos.getLottos()) {
            new LottosDAO().addLotto(lotto.toString(), new RoundDAO().recentRound());
        }
    }

    public void registerWinningLotto(String input_winning_lotto, String input_bonus) {
        int bonus = Integer.parseInt(input_bonus);
        new WinningLotto(
                LottoFactory.createManualGenerator(input_winning_lotto),
                LottoNumber.of(bonus)
        );

        new WinningLottoDAO().addWinningLotto(input_winning_lotto, bonus, new RoundDAO().recentRound());
    }
}
