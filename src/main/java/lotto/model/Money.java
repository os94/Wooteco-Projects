package lotto.model;

public class Money {
    private static final int PRICE_OF_LOTTO = 1_000;

    private final Positive money;

    public Money(int number) {
        this.money = new Positive(number);
    }

    public int getCountOfLotto() {
        return money.per(PRICE_OF_LOTTO);
    }
}
