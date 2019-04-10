package domain;

import java.util.*;

public class LottoMachine {
    private static final String EXIT_MESSAGE_LACK_OF_MONEY = "금액이 부족합니다. 게임을 종료합니다.";
    private static final String PRINT_MESSAGE_BUY_LOTTO = "개를 구매했습니다.";
    private static final int PRICE_OF_LOTTO = 1000;
    private static final int STANDARD_NUMBER_OF_LOTTO = 6;
    private static final int MAX_OF_LOTTO_NUMBER = 45;

    private User user = new User();
    private int inputMoney;
    private WinningLotto winningLotto;
    private List<Lotto> lottos = new ArrayList<>();

    public void run() {
        setInputMoney();

        buyLotto(getNumberOfLotto(inputMoney));

        setWinningLotto();
    }

    private void setInputMoney() {
        this.inputMoney = user.inputMoney();
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

        System.out.println("\n" + numberOfLotto + PRINT_MESSAGE_BUY_LOTTO);
        printAllLotto();
        System.out.println();
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

    private void printAllLotto() {
        for (Lotto lotto : lottos) {
            lotto.printLotto();
        }
    }
}
