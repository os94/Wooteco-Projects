package lotto.model;

public class Money {
    private static final Positive PRICE_OF_LOTTO = new Positive(1_000);

    private final Positive money;

    public Money(int number) {
        this.money = new Positive(number);
    }

    public Positive getCountOfLotto() {
        return money.divide(PRICE_OF_LOTTO);
    }
}
