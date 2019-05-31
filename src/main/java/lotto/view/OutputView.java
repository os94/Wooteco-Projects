package lotto.view;

import lotto.model.GameResult;
import lotto.model.Lottos;
import lotto.model.Positive;

public class OutputView {
    private static final String MESSAGE_BUY_COMPLETE = "수동으로 %d장, 자동으로 %d개를 구매했습니다.\n";

    public static void print(Positive manual, Positive auto) {
        System.out.printf(MESSAGE_BUY_COMPLETE, manual.get(), auto.get());
    }

    public static void print(Lottos lottos) {
        System.out.println(lottos);
    }

    public static void print(GameResult gameResult) {

    }
}
