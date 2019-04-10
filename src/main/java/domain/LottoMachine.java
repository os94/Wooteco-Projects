package domain;

import java.util.*;

public class LottoMachine {
    private static final String EXIT_MESSAGE_LACK_OF_MONEY = "금액이 부족합니다. 게임을 종료합니다.";
    private static final String PRINT_MESSAGE_BUY_LOTTO = "개를 구매했습니다.";
    private static final String PRINT_MESSAGE_MATCH_RESULT = "\n당첨 통계\n----------";
    private static final String PRINT_MESSAGE_EACH_RESULT = "%d개 일치 (%d원)- %d개\n";
    private static final String PRINT_MESSAGE_EACH_RESULT_SECOND = "%d개 일치, 보너스볼 일치(%d원)- %d개\n";
    private static final String PRINT_MESSAGE_PROFIT = "총 수익률은 %.3f입니다.\n";
    private static final int PRICE_OF_LOTTO = 1000;
    private static final int STANDARD_NUMBER_OF_LOTTO = 6;
    private static final int MAX_OF_LOTTO_NUMBER = 45;

    private User user = new User();
    private int inputMoney;
    private WinningLotto winningLotto;
    private List<Lotto> lottos = new ArrayList<>();
    private List<Rank> ranks = new ArrayList<>();

    public void run() {
        setInputMoney();

        buyLotto(getNumberOfLotto(inputMoney));

        setWinningLotto();

        matchAllLotto();
        printResult();
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

    private void matchAllLotto() {
        for (Lotto lotto : lottos) {
            ranks.add(winningLotto.match(lotto));
        }
    }

    private void printResult() {
        Rank[] rankValues = Rank.values();

        System.out.println(PRINT_MESSAGE_MATCH_RESULT);
        for (int i = rankValues.length - 2; i >= 0; i--) {
            printEachResult(rankValues[i]);
        }
        System.out.printf(PRINT_MESSAGE_PROFIT, getProfit());
    }

    private void printEachResult(Rank rank) {
        if (rank == Rank.SECOND) {
            System.out.printf(PRINT_MESSAGE_EACH_RESULT_SECOND
                    , rank.getCountOfMatch(), rank.getWinningMoney(), getNumberOfRank(rank));
            return;
        }
        System.out.printf(PRINT_MESSAGE_EACH_RESULT
                , rank.getCountOfMatch(), rank.getWinningMoney(), getNumberOfRank(rank));
    }

    private int getNumberOfRank(Rank findingRank) {
        return Collections.frequency(ranks, findingRank);
    }

    private double getProfit() {
        double sum = 0;

        for (Rank rank : ranks) {
            sum += rank.getWinningMoney();
        }

        return sum / inputMoney;
    }
}
