package lotto.model;

import java.util.Objects;

public class Positive {
    private static final String ERROR_NOT_POSITIVE = "양의 값을 입력해주세요.";

    private final int number;

    public Positive(final int number) {
        if (number <= 0) {
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