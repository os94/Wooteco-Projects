package lotto.model;

import java.util.Objects;

// this class means Positive & Zero Integer, not Negative.
public class Positive {
    private static final String ERROR_NOT_POSITIVE = "음수를 입력하셨습니다.";
    private static final int ZERO = 0;

    private final int number;

    public Positive(final int number) {
        if (number < ZERO) {
            throw new IllegalArgumentException(ERROR_NOT_POSITIVE);
        }
        this.number = number;
    }

    public int get() {
        return number;
    }

    public Positive subtract(Positive other) {
        return new Positive(number - other.number);
    }

    public Positive divide(Positive other) {
        return new Positive(number / other.number);
    }

    public boolean isSmallerThan(Positive other) {
        return number < other.number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Positive positive = (Positive) o;
        return number == positive.number;
    }

    @Override
    public int hashCode() {
        return Objects.hash(number);
    }
}
