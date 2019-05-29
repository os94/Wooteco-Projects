package lotto.view;

import lotto.model.LottoResult;

public class OutputView {
    private static final String MESSAGE_BUY_COMPLETE = "개를 구매했습니다.";

    public static void print(int countOfLotto) {
        System.out.println(countOfLotto + MESSAGE_BUY_COMPLETE);
    }

    public static void print(LottoResult lottoResult) {
        System.out.println(lottoResult);
    }
}
