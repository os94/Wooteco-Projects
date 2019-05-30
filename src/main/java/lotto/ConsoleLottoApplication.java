package lotto;

import lotto.model.*;
import lotto.view.InputView;
import lotto.view.OutputView;

import java.util.List;

public class ConsoleLottoApplication {
    public static void main(String[] args) {
        Money money = new Money(InputView.inputMoney());
        Positive countOfManualLotto = new Positive(InputView.inputCountOfManualLotto());
        Positive countOfAutoLotto = money.getCountOfLotto().subtract(countOfManualLotto);
        List<String> lottos = inputManualLotto(countOfManualLotto);

        LottoResult lottoResult = new LottoResult();
        lottoResult.add(buyManual(lottos));
        lottoResult.add(buyAuto(countOfAutoLotto));

        OutputView.print(countOfManualLotto, countOfAutoLotto);
        OutputView.print(lottoResult);

        try {
            WinningLotto winningLotto = new WinningLotto(
                    LottoGenerator.generate(InputView.inputWinningLotto()),
                    new LottoNumber(InputView.inputBonusNo()));
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    private static LottoResult buyManual(List<String> lottos) {
        LottoResult lottoResult = new LottoResult();

        for (String lotto : lottos) {
            lottoResult.add(LottoGenerator.generate(lotto));
        }
        return lottoResult;
    }

    private static LottoResult buyAuto(Positive countOfLotto) {
        LottoResult lottoResult = new LottoResult();

        for (int i = 0; i < countOfLotto.get(); i++) {
            lottoResult.add(LottoGenerator.generate());
        }
        return lottoResult;
    }
}
