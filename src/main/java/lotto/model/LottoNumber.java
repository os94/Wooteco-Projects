package lotto.model;

public class LottoNumber {
    private static final String ERROR_NOT_LOTTO_NUMBER = "로또 숫자는 1~45사이 입니다.";
    private static final int LOWER_LIMIT = 1;
    private static final int UPPER_LIMIT = 45;

    private final int number;

    public LottoNumber(int number) {
        if (number < LOWER_LIMIT || UPPER_LIMIT < number) {
            throw new IllegalArgumentException(ERROR_NOT_LOTTO_NUMBER);
        }
        this.number = number;
    }
}
