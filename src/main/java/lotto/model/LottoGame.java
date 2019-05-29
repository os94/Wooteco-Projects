package lotto.model;

public class LottoGame {
    public static LottoResult buy(int countOfLotto) {
        LottoResult lottoResult = new LottoResult();

        for (int i = 0; i < countOfLotto; i++) {
            lottoResult.add(LottoGenerator.generate());
        }
        return lottoResult;
    }
}
