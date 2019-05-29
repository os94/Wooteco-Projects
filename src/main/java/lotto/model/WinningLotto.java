package lotto.model;

public class WinningLotto {
    private final Lotto lotto;
    private final LottoNumber bonusNo;

    public WinningLotto(Lotto lotto, LottoNumber bonusNo) {
        this.lotto = lotto;
        this.bonusNo = bonusNo;
    }
}
