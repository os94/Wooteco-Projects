package domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class User {
    private final static String GUIDE_USER_INPUT_MONEY = "구입금액을 입력해 주세요.";
    private final static String GUIDE_USER_INPUT_WINNING_LOTTO = "지난 주 당첨 번호를 입력해 주세요.";
    private final static String GUIDE_USER_INPUT_BONUS_NO = "보너스 볼을 입력해 주세요.";
    private Scanner sc = new Scanner(System.in);

    public int inputMoney() {
        String input;

        do {
            System.out.println(GUIDE_USER_INPUT_MONEY);
            input = sc.nextLine();
        } while (false);

        return Integer.parseInt(input);
    }

    public Lotto inputWinningLotto() {
        String input;

        do {
            System.out.println(GUIDE_USER_INPUT_WINNING_LOTTO);
            input = sc.nextLine();
        } while (false);

        return parseLotto(input);
    }

    private Lotto parseLotto(String input) {
        List<Integer> numberList = new ArrayList<>();

        input = input.replace(" ", "");
        for (String number : input.split(",")) {
            numberList.add(Integer.parseInt(number));
        }

        return new Lotto(numberList);
    }

    public int inputBonusNo() {
        String input;

        do {
            System.out.println(GUIDE_USER_INPUT_BONUS_NO);
            input = sc.nextLine();
        } while (false);

        return Integer.parseInt(input);
    }
}
