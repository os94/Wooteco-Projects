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
    private static final String EXIT_MESSAGE_LACK_OF_MONEY = "금액이 부족합니다. 게임을 종료합니다.";
    private static final int PRICE_OF_LOTTO = 1_000;
    private static final int STANDARD_NUMBER_OF_LOTTO = 6;
    private static final int MAX_OF_LOTTO_NUMBER = 45;

    private User user = new User();
    private WinningLotto winningLotto;
    private List<Lotto> lottos = new ArrayList<>();
    private List<Rank> ranks = new ArrayList<>();

    public void run() {
        user.inputMoney();

        buyLotto(getNumberOfLotto(user.getInputMoney()));
        ResultView.printCurrentLotto(lottos, getNumberOfLotto(user.getInputMoney()));

        setWinningLotto();

        matchAllLotto();
        ResultView.printResult(ranks, getProfit());
    }

    private void setWinningLotto() {
        this.winningLotto = new WinningLotto(user.inputWinningLotto(), user.inputBonusNo());
    }

    private int getNumberOfLotto(int inputMoney) {
        if (inputMoney / PRICE_OF_LOTTO == 0) {
            System.out.println(EXIT_MESSAGE_LACK_OF_MONEY);
            System.exit(-1);
        }
        return inputMoney / PRICE_OF_LOTTO;
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

    public static int getNumberOfRank(List<Rank> ranks, Rank rank) {
        return Collections.frequency(ranks, rank);
    }

    private double getProfit() {
        double sum = 0;

        for (Rank rank : ranks) {
            sum += rank.getWinningMoney();
        }

        return sum / user.getInputMoney();
    }
}
