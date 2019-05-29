package lotto.model;

import org.apache.commons.lang3.StringUtils;

public class Money {
    private static final String ERROR_MONEY_BLANK = "금액이 입력되지 않았습니다.";
    private static final int PRICE_OF_LOTTO = 1_000;

    private final Positive money;

    public Money(String input) throws Exception {
        if (StringUtils.isBlank(input)) {
            throw new IllegalArgumentException(ERROR_MONEY_BLANK);
        }
        this.money = new Positive(Integer.parseInt(input));
    }

    public int getNumberOfLotto() {
        return money.per(PRICE_OF_LOTTO);
    }
}
