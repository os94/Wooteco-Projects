package lotto.model;

import org.apache.commons.lang3.StringUtils;

public class Money {
    private final int money;

    public Money(String input) throws Exception {
        if (StringUtils.isBlank(input)) {
            throw new IllegalArgumentException("구입금액이 입력되지 않았습니다.");
        }
        int money = Integer.parseInt(input);
        if (isNegativeNumber(money)) {
            throw new IllegalArgumentException("구입금액은 0 또는 양의 값이어야 합니다.");
        }
        this.money = money;
    }

    private boolean isNegativeNumber(int money) {
        return money < 0;
    }
}
