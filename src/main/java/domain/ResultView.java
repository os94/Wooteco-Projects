/*
 * @class       ResultView class
 * @version     1.0.0
 * @date        19.04.11
 * @author      OHSANG SEO (tjdhtkd@gmail.com)
 * @brief       include print methods.
 */

package domain;

import java.util.Collections;
import java.util.List;

public class ResultView {
    private static final String PRINT_MESSAGE_BUY_LOTTO = "개를 구매했습니다.";
    private static final String PRINT_MESSAGE_MATCH_RESULT = "\n당첨 통계\n----------";
    private static final String PRINT_MESSAGE_PROFIT = "총 수익률은 %.3f입니다.\n";
    private static final String PRINT_MESSAGE_EACH_RESULT = "%d개 일치 (%d원)- %d개\n";
    private static final String PRINT_MESSAGE_EACH_RESULT_SECOND = "%d개 일치, 보너스볼 일치(%d원)- %d개\n";

    public static void printCurrentLotto(List<Lotto> lottos, int numberOfLotto) {
        System.out.println("\n" + numberOfLotto + PRINT_MESSAGE_BUY_LOTTO);
        for (Lotto lotto : lottos) {
            System.out.println(lotto.toString());
        }
        System.out.println();
    }

    public static void printResult(LottoResult lottoResult, Money money) {
        Rank[] rankValues = Rank.values();

        System.out.println(PRINT_MESSAGE_MATCH_RESULT);
        for (int i = rankValues.length - 2; i >= 0; i--) {
            printEachResult(lottoResult, rankValues[i]);
        }
        System.out.printf(PRINT_MESSAGE_PROFIT, money.getProfit(lottoResult));
    }

    private static void printEachResult(LottoResult lottoResult, Rank rank) {
        if (rank == Rank.SECOND) {
            System.out.printf(PRINT_MESSAGE_EACH_RESULT_SECOND, rank.getCountOfMatch()
                    , rank.getWinningMoney(), lottoResult.getNumber(rank));
            return;
        }
        System.out.printf(PRINT_MESSAGE_EACH_RESULT, rank.getCountOfMatch()
                , rank.getWinningMoney(), lottoResult.getNumber(rank));
    }
}
