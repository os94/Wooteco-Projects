package lotto.model;

public class WinningLotto {
    private static final String ERROR_DUPLICATE_WINNING_LOTTO = "잘못된 입력입니다. 로또번호와 보너스볼에 중복이 있습니다.";

    private final Lotto winningLotto;
    private final LottoNumber bonusNo;

    public WinningLotto(Lotto winningLotto, LottoNumber bonusNo) {
        if (winningLotto.contains(bonusNo)) {
            throw new IllegalArgumentException(ERROR_DUPLICATE_WINNING_LOTTO);
        }
        this.winningLotto = winningLotto;
        this.bonusNo = bonusNo;
    }

    public Rank match(Lotto userLotto) {
        int countOfMatch = winningLotto.countOfMatch(userLotto);
        boolean matchBonus = userLotto.contains(bonusNo);
        return Rank.valueOf(countOfMatch, matchBonus);
    }

    @Override
    public String toString() {
        return winningLotto + " + " + bonusNo;
    }
}
