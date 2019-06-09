package lotto.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class LottoNumber implements Comparable<LottoNumber> {
    private static final String ERROR_NOT_LOTTO_NUMBER = "로또 숫자는 1~45사이 입니다.";
    private static final int LOWER_LIMIT = 1;
    public static final int UPPER_LIMIT = 45;

    private static final Map<Integer, LottoNumber> lottoNumbers = new HashMap<>();
    private final Integer number;

    static {
        for (int i = LOWER_LIMIT; i <= UPPER_LIMIT; i++) {
            lottoNumbers.put(i, new LottoNumber(i));
        }
    }

    private LottoNumber(int number) {
        this.number = number;
    }

    public static LottoNumber of(int number) {
        if (number < LOWER_LIMIT || UPPER_LIMIT < number) {
            throw new IllegalArgumentException(ERROR_NOT_LOTTO_NUMBER);
        }
        return lottoNumbers.get(number);
    }

    public static Set<Integer> getAll() {
        return lottoNumbers.keySet();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LottoNumber that = (LottoNumber) o;
        return Objects.equals(number, that.number);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number);
    }

    @Override
    public String toString() {
        return String.valueOf(number);
    }

    @Override
    public int compareTo(LottoNumber other) {
        return number.compareTo(other.number);
    }
}
