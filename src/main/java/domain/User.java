package domain;

import java.util.Scanner;

public class User {
    private final static String GUIDE_USER_INPUT_MONEY = "구입금액을 입력해 주세요.";
    private final static String GUIDE_USER_INPUT_WINNING_LOTTO = "지난 주 당첨 번호를 입력해 주세요.";
    private final static String GUIDE_USER_INPUT_BONUS_NO = "보너스 볼을 입력해 주세요.";
    private Scanner sc = new Scanner(System.in);

    public int inputMoney() {
        int input;

        do {
            System.out.println(GUIDE_USER_INPUT_MONEY);
            input = sc.nextInt();
            sc.nextLine();
        } while (!Validator.isPositiveNumber(input));

        return input;
    }

    public Lotto inputWinningLotto() {
        String input;

        do {
            System.out.println(GUIDE_USER_INPUT_WINNING_LOTTO);
            input = sc.nextLine();
            input = input.replaceAll(" ", "");
        } while (!Validator.isWinningLotto(input));

        return new Lotto(Validator.parseSixNumber(input));
    }

    public int inputBonusNo() {
        int input;

        do {
            System.out.println(GUIDE_USER_INPUT_BONUS_NO);
            input = sc.nextInt();
            sc.nextLine();
        } while (!Validator.isLottoNumber(input));

        return input;
    }
}
