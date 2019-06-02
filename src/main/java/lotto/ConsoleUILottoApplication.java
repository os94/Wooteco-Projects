package lotto;

import lotto.model.*;
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
            List<String> inputLottos = inputLotto(countOfManualLotto);

            Lottos lottos = new Lottos();
            lottos.add(buyManual(inputLottos));
            lottos.add(buyAuto(countOfAutoLotto));

            OutputView.print(countOfManualLotto, countOfAutoLotto);
            OutputView.print(lottos);

            WinningLotto winningLotto = new WinningLotto(
                    LottoGenerator.generate(InputView.inputLotto(MESSAGE_WINNING_LOTTO)),
                    LottoNumber.of(InputView.inputPositiveNumber(MESSAGE_BONUS_NO)));

            GameResult gameResult = matchLotto(lottos, winningLotto);
            OutputView.print(gameResult);
            OutputView.print(money.getRateOfProfit(gameResult.getTotalPrizeMoney()));
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

    private static Lottos buyManual(List<String> inputLottos) {
        Lottos lottos = new Lottos();

        for (String lotto : inputLottos) {
            lottos.add(LottoGenerator.generate(lotto));
        }
        return lottos;
    }

    private static Lottos buyAuto(Positive countOfLotto) {
        Lottos lottos = new Lottos();

        for (int i = 0; i < countOfLotto.get(); i++) {
            lottos.add(LottoGenerator.generate());
        }
        return lottos;
    }

    private static GameResult matchLotto(Lottos lottos, WinningLotto winningLotto) {
        GameResult gameResult = new GameResult();
        for (Lotto lotto : lottos.getLottos()) {
            gameResult.add(winningLotto.match(lotto));
        }
        return gameResult;
    }
}
