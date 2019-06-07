package lotto.model;

import lotto.model.lottogenerator.LottoFactory;

import java.util.List;

public class LottoGame {
    public static Lottos buy(List<String> inputLottos, PositiveNumber countOfLotto) {
        Lottos lottos = new Lottos();
        lottos.add(buyManual(inputLottos));
        lottos.add(buyAuto(countOfLotto));

        return lottos;
    }

    private static Lottos buyManual(List<String> inputLottos) {
        Lottos lottos = new Lottos();

        for (String lotto : inputLottos) {
            lottos.add(LottoFactory.create(lotto));
        }
        return lottos;
    }

    private static Lottos buyAuto(PositiveNumber countOfLotto) {
        Lottos lottos = new Lottos();

        for (int i = 0; i < countOfLotto.get(); i++) {
            lottos.add(LottoFactory.create(null));
        }
        return lottos;
    }

    public static GameResult match(Lottos lottos, WinningLotto winningLotto) {
        GameResult gameResult = new GameResult();

        for (Lotto lotto : lottos.getLottos()) {
            gameResult.add(winningLotto.match(lotto));
        }
        return gameResult;
    }
}
