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

    public Map<String, Object> result(String inputRound) {
        Map<String, Object> model = new HashMap<>();
        int round = Integer.parseInt(inputRound);
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

    public void buyLotto(String inputMoney, String[] inputManualLottos) {
        Money money = new Money(Integer.parseInt(inputMoney));
        PositiveNumber countOfManualLotto = new PositiveNumber(inputManualLottos.length);
        PositiveNumber countOfAutoLotto = money.countOfLotto().subtract(countOfManualLotto);
        Lottos lottos = LottoGame.buy(Arrays.asList(inputManualLottos), countOfAutoLotto);

        new RoundDAO().updateRoundWith(inputMoney);
        for (Lotto lotto : lottos.getLottos()) {
            new LottosDAO().addLotto(lotto.toString(), new RoundDAO().recentRound());
        }
    }

    public void registerWinningLotto(String inputWinningLotto, String inputBonus) {
        int bonus = Integer.parseInt(inputBonus);
        new WinningLotto(
                LottoFactory.createManualGenerator(inputWinningLotto),
                LottoNumber.of(bonus)
        );

        new WinningLottoDAO().addWinningLotto(inputWinningLotto, bonus, new RoundDAO().recentRound());
    }
}
