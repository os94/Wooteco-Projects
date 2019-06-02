package lotto;

import lotto.model.*;
import lotto.model.lottogenerator.LottoFactory;
import lotto.view.InputView;
import lotto.view.OutputView;

import java.util.ArrayList;
import java.util.List;

public class ConsoleUILottoApplication {
    private static final String MESSAGE_MONEY = "구입금액을 입력해 주세요.";
    private static final String MESSAGE_COUNT_OF_MANUAL_LOTTO = "수동으로 구매할 로또 수를 입력해 주세요.";
    private static final String MESSAGE_MANUAL_LOTTO = "수동으로 구매할 번호를 입력해 주세요.";
    private static final String MESSAGE_WINNING_LOTTO = "지난 주 당첨 번호를 입력해 주세요.";
    private static final String MESSAGE_BONUS_NO = "보너스 볼을 입력해 주세요.";

    public static void main(String[] args) {
        try {
            Money money = new Money(InputView.inputPositiveNumber(MESSAGE_MONEY));
            Positive countOfManualLotto = new Positive(InputView.inputPositiveNumber(MESSAGE_COUNT_OF_MANUAL_LOTTO));
            Positive countOfAutoLotto = money.getCountOfLotto().subtract(countOfManualLotto);
            Lottos lottos = LottoGame.buy(inputLotto(countOfManualLotto), countOfAutoLotto);
            OutputView.print(countOfManualLotto, countOfAutoLotto, lottos);

            WinningLotto winningLotto = inputWinningLotto();
            GameResult gameResult = LottoGame.match(lottos, winningLotto);
            OutputView.print(gameResult, money);
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    private static List<String> inputLotto(Positive countOfLotto) {
        List<String> lottos = new ArrayList<>();

        for (int i = 0; i < countOfLotto.get(); i++) {
            lottos.add(InputView.inputLotto(MESSAGE_MANUAL_LOTTO));
        }
        return lottos;
    }

    private static WinningLotto inputWinningLotto() {
        return new WinningLotto(
                LottoFactory.create(InputView.inputLotto(MESSAGE_WINNING_LOTTO)),
                LottoNumber.of(InputView.inputPositiveNumber(MESSAGE_BONUS_NO)));
    }
}
