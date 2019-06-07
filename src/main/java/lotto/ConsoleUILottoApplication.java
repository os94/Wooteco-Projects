package lotto;

import lotto.model.*;
import lotto.model.lottogenerator.LottoFactory;
import lotto.view.InputView;
import lotto.view.OutputView;

import java.util.ArrayList;
import java.util.List;

public class ConsoleUILottoApplication {
    public static void main(String[] args) {
        try {
            Money money = new Money(InputView.inputMoney());
            PositiveNumber countOfManualLotto = new PositiveNumber(InputView.inputCountOfManualLotto());
            PositiveNumber countOfAutoLotto = money.countOfLotto().subtract(countOfManualLotto);
            Lottos lottos = LottoGame.buy(inputLotto(countOfManualLotto), countOfAutoLotto);
            OutputView.print(countOfManualLotto, countOfAutoLotto, lottos);

            WinningLotto winningLotto = inputWinningLotto();
            GameResult gameResult = LottoGame.match(lottos, winningLotto);
            OutputView.print(gameResult, money);
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    private static List<String> inputLotto(PositiveNumber countOfLotto) {
        List<String> lottos = new ArrayList<>();

        for (int i = 0; i < countOfLotto.get(); i++) {
            lottos.add(InputView.inputManualLotto());
        }
        return lottos;
    }

    private static WinningLotto inputWinningLotto() {
        return new WinningLotto(
                LottoFactory.create(InputView.inputWinningLotto()),
                LottoNumber.of(InputView.inputBonusNo()));
    }
}
