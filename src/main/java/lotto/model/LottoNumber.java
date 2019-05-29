package lotto.model;

import java.util.Objects;

public class LottoNumber {
    private static final String ERROR_NOT_LOTTO_NUMBER = "로또 숫자는 1~45사이 입니다.";
    private static final int LOWER_LIMIT = 1;
    public static final int UPPER_LIMIT = 45;

    private final int number;

    public LottoNumber(int number) {
        if (number < LOWER_LIMIT || UPPER_LIMIT < number) {
            throw new IllegalArgumentException(ERROR_NOT_LOTTO_NUMBER);
        }
        this.number = number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LottoNumber that = (LottoNumber) o;
        return number == that.number;
    }

    @Override
    public int hashCode() {
        return Objects.hash(number);
    }
}
