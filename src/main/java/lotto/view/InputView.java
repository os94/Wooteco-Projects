package lotto.view;

import lotto.model.Money;

import java.util.Scanner;

public class InputView {
    private static final String MESSAGE_INPUT_MONEY = "구입금액을 입력해 주세요.";
    private static final Scanner scanner = new Scanner(System.in);

    public static Money inputMoney() {
        System.out.println(MESSAGE_INPUT_MONEY);
        try {
            return new Money(scanner.nextLine());
        } catch (Exception e) {
            System.err.println(e);
            return inputMoney();
        }
    }
}
