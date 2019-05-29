package lotto;

import lotto.model.*;
import lotto.view.InputView;
import lotto.view.OutputView;

public class ConsoleLottoApplication {
    public static void main(String[] args) {
        Money money = new Money(InputView.inputMoney());
        int countOfLotto = money.getCountOfLotto();

        LottoResult lottoResult = LottoGame.buy(countOfLotto);
        OutputView.print(countOfLotto);
        OutputView.print(lottoResult);

        try {
            WinningLotto winningLotto = new WinningLotto(
                    LottoGenerator.generate(InputView.inputWinningLotto()),
                    new LottoNumber(InputView.inputBonusNo()));
        } catch (Exception e) {
            System.err.println(e);
        }
    }
}
