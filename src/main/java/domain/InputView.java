/*
 * @class       InputView class
 * @version     1.0.0
 * @date        19.04.11
 * @author      OHSANG SEO (tjdhtkd@gmail.com)
 * @brief       receive input from user.
 */

package domain;

import java.util.Scanner;

public class InputView {
    private final static String GUIDE_USER_INPUT_MONEY = "구입금액을 입력해 주세요.";
    private final static String GUIDE_USER_INPUT_WINNING_LOTTO = "지난 주 당첨 번호를 입력해 주세요.";
    private final static String GUIDE_USER_INPUT_BONUS_NO = "보너스 볼을 입력해 주세요.";
    private static Scanner sc = new Scanner(System.in);
    private static String input;

    public static int getMoney() {
        try {
            System.out.println(GUIDE_USER_INPUT_MONEY);
            input = sc.nextLine();
            input = input.replaceAll(" ", "");
            return Integer.parseInt(input);
        } catch (IllegalArgumentException e) {
            return getMoney();
        }
    }

    public static Lotto getWinningLotto() {
        do {
            System.out.println(GUIDE_USER_INPUT_WINNING_LOTTO);
            input = sc.nextLine();
            input = input.replaceAll(" ", "");
        } while (!Validator.isWinningLotto(input));

        return new Lotto(Validator.parseSixNumber(input));
    }

    public static int getBonusNo() {
        do {
            System.out.println(GUIDE_USER_INPUT_BONUS_NO);
            input = sc.nextLine();
            input = input.replaceAll(" ", "");
        } while (!Validator.isLottoNumber(input));

        return Integer.parseInt(input);
    }
}
