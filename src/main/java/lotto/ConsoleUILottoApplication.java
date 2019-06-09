package lotto;

import lotto.model.*;
import lotto.model.lottogenerator.LottoFactory;
import lotto.view.InputView;
import lotto.view.OutputView;

public class ConsoleUILottoApplication {
    public static void main(String[] args) {
        try {
            Money money = new Money(InputView.inputMoney());
            PositiveNumber countOfManualLotto = new PositiveNumber(InputView.inputCountOfManualLotto());
            PositiveNumber countOfAutoLotto = money.countOfLotto().subtract(countOfManualLotto);
            Lottos lottos = LottoGame.buy(InputView.inputManualLottos(countOfManualLotto), countOfAutoLotto);
            OutputView.print(countOfManualLotto, countOfAutoLotto, lottos);

            WinningLotto winningLotto = new WinningLotto(
                    LottoFactory.createManualGenerator(InputView.inputWinningLotto()), LottoNumber.of(InputView.inputBonusNo()));
            GameResult gameResult = LottoGame.match(lottos, winningLotto);
            OutputView.print(gameResult, money);
        } catch (Exception e) {
            System.err.println(e);
        }
    }
}
