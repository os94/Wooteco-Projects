package lotto.model;

public class Positive {
    private final int number;

    public Positive(final int number) {
        if (number <= 0) {
            throw new IllegalArgumentException("양의 값을 입력해주세요.");
        }
        this.number = number;
    }

    public int per(int other) {
        return number / other;
    }
}
