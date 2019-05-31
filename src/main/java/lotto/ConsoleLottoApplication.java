package lotto;

import lotto.model.*;
import lotto.view.InputView;
import lotto.view.OutputView;

import java.util.ArrayList;
import java.util.List;

public class ConsoleLottoApplication {
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
            List<String> lottos = inputLotto(countOfManualLotto);

            Lottos lottoResult = new Lottos();
            lottoResult.add(buyManual(lottos));
            lottoResult.add(buyAuto(countOfAutoLotto));

            OutputView.print(countOfManualLotto, countOfAutoLotto);
            OutputView.print(lottoResult);

            WinningLotto winningLotto = new WinningLotto(
                    LottoGenerator.generate(InputView.inputLotto(MESSAGE_WINNING_LOTTO)),
                    new LottoNumber(InputView.inputPositiveNumber(MESSAGE_BONUS_NO)));
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

    private static Lottos buyManual(List<String> lottos) {
        Lottos lottoResult = new Lottos();

        for (String lotto : lottos) {
            lottoResult.add(LottoGenerator.generate(lotto));
        }
        return lottoResult;
    }

    private static Lottos buyAuto(Positive countOfLotto) {
        Lottos lottos = new Lottos();

        for (int i = 0; i < countOfLotto.get(); i++) {
            lottos.add(LottoGenerator.generate());
        }
        return lottos;
    }
}
