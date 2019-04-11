/*
 * @class       LottoMachine class
 * @version     1.0.0
 * @date        19.04.11
 * @author      OHSANG SEO (tjdhtkd@gmail.com)
 * @brief       include most of methods about Lotto Game.
 */

package domain;

import java.util.*;

public class LottoMachine {
    private static final int STANDARD_NUMBER_OF_LOTTO = 6;
    private static final int MAX_OF_LOTTO_NUMBER = 45;
    private WinningLotto winningLotto;
    private List<Lotto> lottos = new ArrayList<>();
    private List<Rank> ranks = new ArrayList<>();
    private Money money;

    public void run() {
        money = new Money(InputView.getMoney());

        buyLotto(money.getNumberOfLotto());
        ResultView.printCurrentLotto(lottos, money.getNumberOfLotto());

        setWinningLotto();

        matchAllLotto();
        ResultView.printResult(ranks, money);
    }

    private void setWinningLotto() {
        this.winningLotto = new WinningLotto(InputView.getWinningLotto(), InputView.getBonusNo());
    }

    private void buyLotto(int numberOfLotto) {
        for (int loop = 0; loop < numberOfLotto; loop++) {
            lottos.add(generateLotto());
        }
    }

    private Lotto generateLotto() {
        Set<Integer> numberSet = new HashSet<>();
        List<Integer> numberList;

        do {
            numberSet.add(generateRandomNumber());
        } while (numberSet.size() < STANDARD_NUMBER_OF_LOTTO);
        numberList = new ArrayList<>(numberSet);
        Collections.sort(numberList);

        return new Lotto(numberList);
    }

    private Integer generateRandomNumber() {
        return (int) (Math.random() * MAX_OF_LOTTO_NUMBER + 1);
    }

    private void matchAllLotto() {
        for (Lotto lotto : lottos) {
            ranks.add(winningLotto.match(lotto));
        }
    }
}
