package lotto.model;

public class WinningLotto {
    private static final String ERROR_DUPLICATE_WINNING_LOTTO = "잘못된 입력입니다. 로또번호와 보너스볼에 중복이 있습니다.";

    private final Lotto lotto;
    private final LottoNumber bonusNo;

    public WinningLotto(Lotto lotto, LottoNumber bonusNo) {
        if (lotto.contains(bonusNo)) {
            throw new IllegalArgumentException(ERROR_DUPLICATE_WINNING_LOTTO);
        }
        this.lotto = lotto;
        this.bonusNo = bonusNo;
    }
}
