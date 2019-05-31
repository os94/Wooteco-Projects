package lotto.model;

public class Money {
    private static final String ERROR_LACK_OF_MONEY = "금액이 부족합니다.";
    private static final Positive PRICE_OF_LOTTO = new Positive(1_000);
    private static final int PERCENTAGE = 100;

    private final Positive money;

    public Money(int number) {
        this.money = new Positive(number);
    }

    public Positive getCountOfLotto() {
        if (money.isSmallerThan(PRICE_OF_LOTTO)) {
            throw new IllegalArgumentException(ERROR_LACK_OF_MONEY);
        }
        return money.divide(PRICE_OF_LOTTO);
    }

    public double getRateOfProfit(int prizeMoney) {
        return ((double) prizeMoney / money.get()) * PERCENTAGE;
    }
}
