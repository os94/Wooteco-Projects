package lotto;

import lotto.model.LottoGame;
import lotto.model.LottoResult;
import lotto.model.Money;
import lotto.view.InputView;

public class ConsoleLottoApplication {
    public static void main(String[] args) {
        Money money = InputView.inputMoney();
        int countOfLotto = money.getCountOfLotto();

        LottoResult lottoResult = LottoGame.buy(countOfLotto);

    }
}
