package lotto.model;

import java.util.Objects;

// this class means Positive & Zero Integer, not Negative.
public class PositiveNumber {
    private static final String ERROR_NOT_POSITIVE = "음수를 입력하셨습니다.";
    private static final int ZERO = 0;

    private final int number;

    public PositiveNumber(final int number) {
        if (number < ZERO) {
            throw new IllegalArgumentException(ERROR_NOT_POSITIVE);
        }
        this.number = number;
    }

    public int get() {
        return number;
    }

    public PositiveNumber subtract(PositiveNumber other) {
        return new PositiveNumber(number - other.number);
    }

    public PositiveNumber divide(PositiveNumber other) {
        return new PositiveNumber(number / other.number);
    }

    public boolean lessThan(PositiveNumber other) {
        return number < other.number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PositiveNumber positiveNumber = (PositiveNumber) o;
        return number == positiveNumber.number;
    }

    @Override
    public int hashCode() {
        return Objects.hash(number);
    }

    @Override
    public String toString() {
        return String.valueOf(number);
    }
}
