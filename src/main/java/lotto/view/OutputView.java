package lotto.view;

import lotto.model.GameResult;
import lotto.model.Lottos;
import lotto.model.Positive;
import lotto.model.Rank;

public class OutputView {
    private static final String MESSAGE_BUY_COMPLETE = "수동으로 %d장, 자동으로 %d개를 구매했습니다.\n";
    private static final String MESSAGE_RESULT = "당첨 통계";
    private static final String DIVISION_LINE = "----------";
    private static final String MESSAGE_RESULT_RANK_SECOND = "%d개 일치, 보너스 볼 일치 (%d원) - %d개";
    private static final String MESSAGE_RESULT_RANK = "%d개 일치 (%d원) - %d개";
    private static final String NEW_LINE = System.getProperty("line.separator");

    public static void print(Positive manual, Positive auto) {
        System.out.printf(NEW_LINE + MESSAGE_BUY_COMPLETE, manual.get(), auto.get());
    }

    public static void print(Lottos lottos) {
        System.out.println(lottos);
    }

    public static void print(GameResult gameResult) {
        System.out.println(NEW_LINE + MESSAGE_RESULT);
        System.out.println(DIVISION_LINE);

        Rank[] rankValues = Rank.values();
        for (int i = rankValues.length - 2; i >= 0; i--) {
            printMatchResult(gameResult, rankValues[i]);
        }
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
