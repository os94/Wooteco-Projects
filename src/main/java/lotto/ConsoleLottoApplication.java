package lotto;

import lotto.model.Lotto;
import lotto.model.LottoGenerator;
import lotto.model.Money;
import lotto.view.InputView;

import java.util.List;

public class ConsoleLottoApplication {
    public static void main(String[] args) {
        Money money = InputView.inputMoney();
        int countOfLotto = money.getCountOfLotto();
        List<Lotto> lottos = LottoGenerator.generate(countOfLotto);
    }
}
