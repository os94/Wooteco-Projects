package lotto.model;

import java.util.*;

public class LottoGenerator {
    private static final String COMMA = ",";

    // generate Lotto auto (by random)
    public static Lotto generate() {
        Set<LottoNumber> uniqueLottoNumbers = new HashSet<>();
        while (uniqueLottoNumbers.size() != Lotto.NUMBER_OF_LOTTO_NUMBERS) {
            uniqueLottoNumbers.add(getLottoNumber(new Random()));
        }
        return new Lotto(setToOrderedList(uniqueLottoNumbers));
    }

    private static LottoNumber getLottoNumber(Random random) {
        return new LottoNumber(random.nextInt(LottoNumber.UPPER_LIMIT) + 1);
    }

    private static List<LottoNumber> setToOrderedList(Set<LottoNumber> uniqueLottoNumbers) {
        List<LottoNumber> lottoNumbers = new ArrayList<>(uniqueLottoNumbers);
        Collections.sort(lottoNumbers);
        return lottoNumbers;
    }

    // generate Lotto manually (by input values)
    public static Lotto generate(String inputNumbers) {
        inputNumbers = inputNumbers.replace(" ", "");
        String[] numbers = inputNumbers.split(COMMA);
        return new Lotto(getLottoNumber(numbers));
    }

    private static List<LottoNumber> getLottoNumber(String[] numbers) {
        List<LottoNumber> lottoNumbers = new ArrayList<>();
        for (String number : numbers) {
            lottoNumbers.add(new LottoNumber(Integer.parseInt(number)));
        }
        return lottoNumbers;
    }
}
