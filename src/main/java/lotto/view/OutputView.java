package lotto.view;

import lotto.model.*;

public class OutputView {
    private static final String MESSAGE_BUY_COMPLETE = "수동으로 %d장, 자동으로 %d개를 구매했습니다.";
    private static final String MESSAGE_RESULT = "당첨 통계";
    private static final String DIVISION_LINE = "----------";
    private static final String MESSAGE_RESULT_RANK_SECOND = "%d개 일치, 보너스 볼 일치 (%d원) - %d개";
    private static final String MESSAGE_RESULT_RANK = "%d개 일치 (%d원) - %d개";
    private static final String NEW_LINE = System.getProperty("line.separator");
    private static final String MESSAGE_RATE_OF_PROFIT = "총 수익률은 %.1f%%입니다.";

    public static void print(PositiveNumber countOfManualLotto, PositiveNumber countOfAutoLotto, Lottos lottos) {
        System.out.printf(NEW_LINE + MESSAGE_BUY_COMPLETE + NEW_LINE
                , countOfManualLotto.get(), countOfAutoLotto.get());

        System.out.println(lottos);
    }

    public static void print(GameResult gameResult, Money money) {
        System.out.println(NEW_LINE + MESSAGE_RESULT);
        System.out.println(DIVISION_LINE);

        Rank[] rankValues = Rank.values();
        for (int i = rankValues.length - 2; i >= 0; i--) {
            printMatchResult(gameResult, rankValues[i]);
        }

        System.out.printf(MESSAGE_RATE_OF_PROFIT + NEW_LINE
                , money.getRateOfProfit(gameResult.getTotalPrizeMoney()));
    }

    private static void printMatchResult(GameResult gameResult, Rank rank) {
        if (Rank.SECOND == rank) {
            System.out.printf(MESSAGE_RESULT_RANK_SECOND + NEW_LINE,
                    rank.getCountOfMatch(), rank.getPrizeMoney(), gameResult.get(rank));
            return;
        }
        System.out.printf(MESSAGE_RESULT_RANK + NEW_LINE,
                rank.getCountOfMatch(), rank.getPrizeMoney(), gameResult.get(rank));
    }
}
