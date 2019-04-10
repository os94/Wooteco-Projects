package domain;

import java.util.Scanner;

public class User {
    private final static String GUIDE_USER_INPUT_MONEY = "구입금액을 입력해 주세요.";
    private Scanner sc = new Scanner(System.in);

    public int inputMoney() {
        String input;

        do {
            System.out.println(GUIDE_USER_INPUT_MONEY);
            input = sc.nextLine();
        } while (false);

        return Integer.parseInt(input);
    }
}
