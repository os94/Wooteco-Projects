package lotto.model;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;

public class Lotto {
    private static final String ERROR_LOTTO_NUMBER_NULL = "로또 숫자 값들이 입력되지 않았습니다.";
    private static final String ERROR_NUMBER_OF_LOTTO_NUMBERS = "로또 숫자는 총 6개이어야 합니다.";
    public static final int NUMBER_OF_LOTTO_NUMBERS = 6;

    private final List<LottoNumber> lottoNumbers;

    public Lotto(List<LottoNumber> lottoNumbers) {
        if (lottoNumbers == null) {
            throw new IllegalArgumentException(ERROR_LOTTO_NUMBER_NULL);
        }
        if (new HashSet<>(lottoNumbers).size() != NUMBER_OF_LOTTO_NUMBERS) {
            throw new IllegalArgumentException(ERROR_NUMBER_OF_LOTTO_NUMBERS);
        }
        this.lottoNumbers = lottoNumbers;
    }

    public List<LottoNumber> getLottoNumbers() {
        return lottoNumbers;
    }

    public boolean contains(LottoNumber number) {
        return lottoNumbers.contains(number);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Lotto lotto = (Lotto) o;
        return Objects.equals(lottoNumbers, lotto.lottoNumbers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lottoNumbers);
    }

    @Override
    public String toString() {
        return String.valueOf(lottoNumbers);
    }
}
