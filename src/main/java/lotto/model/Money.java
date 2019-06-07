package lotto.model;

public class Money {
    private static final String ERROR_LACK_OF_MONEY = "금액이 부족합니다.";
    private static final PositiveNumber PRICE_OF_LOTTO = new PositiveNumber(1_000);
    private static final int PERCENTAGE = 100;

    private final PositiveNumber money;

    public Money(int number) {
        this.money = new PositiveNumber(number);
    }

    public PositiveNumber countOfLotto() {
        if (money.lessThan(PRICE_OF_LOTTO)) {
            throw new IllegalArgumentException(ERROR_LACK_OF_MONEY);
        }
        return money.divide(PRICE_OF_LOTTO);
    }

    public double rateOfProfit(int prizeMoney) {
        return ((double) prizeMoney / money.get()) * PERCENTAGE;
    }
}
