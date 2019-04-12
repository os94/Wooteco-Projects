/*
 * @class       Validator class
 * @version     1.0.0
 * @date        19.04.11
 * @author      OHSANG SEO (tjdhtkd@gmail.com)
 * @brief       include validate methods.
 */

package domain;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {
    private static final int MIN_OF_LOTTO_NUMBER = 1;
    private static final int MAX_OF_LOTTO_NUMBER = 45;
    private static final String REGEX_1_45 = "(0?[1-9]|[1-3][0-9]|4[0-5])";
    private static final String REGEX_SIX_LOTTO_NUMBER = REGEX_1_45 + "(," + REGEX_1_45 + "){5}";
    private static final String ERROR_MESSAGE = "잘못된 입력입니다.\n";
    private static final String ERROR_MESSAGE_DUPLICATE = "값이 중복되었습니다.\n";
    private static final String ERROR_MESSAGE_EXCEED_RANGE = "1~45사이의 값을 입력해주세요.\n";

    public static boolean isLottoNumber(String input) {
        if (MIN_OF_LOTTO_NUMBER <= Integer.parseInt(input)
                && Integer.parseInt(input) <= MAX_OF_LOTTO_NUMBER) {
            return true;
        }
        System.out.println(ERROR_MESSAGE_EXCEED_RANGE);
        return false;
    }

    public static boolean isWinningLotto(String input) {
        if (!matchFormat(input)) {
            return false;
        }

        List<Integer> numberList = parseSixNumber(input);
        if (hasDuplicate(numberList)) {
            return false;
        }
        return true;
    }

    private static boolean matchFormat(String input) {
        Pattern pattern = Pattern.compile(REGEX_SIX_LOTTO_NUMBER);
        Matcher matcher = pattern.matcher(input);

        if (matcher.matches()) {
            return true;
        }
        System.out.println(ERROR_MESSAGE);
        return false;
    }

    private static boolean hasDuplicate(List<Integer> numberList) {
        Set<Integer> numberSet = new HashSet<>(numberList);

        if (numberList.size() == numberSet.size()) {
            return false;
        }
        System.out.println(ERROR_MESSAGE_DUPLICATE);
        return true;
    }

    public static List<Integer> parseSixNumber(String input) {
        List<Integer> numberList = new ArrayList<>();

        for (String number : input.split(",")) {
            numberList.add(Integer.parseInt(number));
        }
        return numberList;
    }
}
