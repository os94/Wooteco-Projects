/*
 * @class       WinningLotto class
 * @version     1.0.0
 * @date        19.04.11
 * @author      OHSANG SEO (tjdhtkd@gmail.com)
 * @brief       include 6 winning number and bonus number.
 */

package domain;

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
        if (userLotto.hasNumber(number)) {
            return 1;
        }
        return 0;
    }
}
