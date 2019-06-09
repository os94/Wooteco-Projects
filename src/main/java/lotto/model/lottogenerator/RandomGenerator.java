package lotto.model.lottogenerator;

import lotto.model.Lotto;
import lotto.model.LottoNumber;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class RandomGenerator implements LottoGenerator {
    private final List<Integer> numbers;

    public RandomGenerator() {
        this.numbers = new ArrayList<>(LottoNumber.getAll());
    }

    @Override
    public Lotto generate() {
        Collections.shuffle(numbers);

        List<LottoNumber> lottoNumbers = IntStream
                .range(0, Lotto.NUMBER_OF_LOTTO_NUMBERS)
                .mapToObj(i -> LottoNumber.of(numbers.get(i)))
                .sorted()
                .collect(Collectors.toList());

        return new Lotto(lottoNumbers);
    }
}
