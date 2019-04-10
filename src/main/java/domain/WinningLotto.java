package domain;

/**
 * 당첨 번호를 담당하는 객체
 */
public class WinningLotto {
    private final Lotto lotto;
    private final int bonusNo;

    public WinningLotto(Lotto lotto, int bonusNo) {
        this.lotto = lotto;
        this.bonusNo = bonusNo;
    }

    public Rank match(Lotto userLotto) {
        int countOfMatch = 0;
        boolean matchBonus = userLotto.hasNumber(bonusNo);

        for (int number : lotto.getNumbers()) {
            countOfMatch += matchLotto(userLotto, number);
        }

        return Rank.valueOf(countOfMatch, matchBonus);
    }

    private int matchLotto(Lotto userLotto, int number) {
        if(userLotto.hasNumber(number)) {
            return 1;
        }
        return 0;
    }
}
