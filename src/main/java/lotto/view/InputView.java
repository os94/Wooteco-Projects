package lotto.view;

import org.apache.commons.lang3.StringUtils;

import java.util.Scanner;

public class InputView {
    private static final String MESSAGE_INPUT_MONEY = "구입금액을 입력해 주세요.";
    private static final String MESSAGE_INPUT_COUNT_OF_MANUAL_LOTTO = "수동으로 구매할 로또 수를 입력해 주세요.";
    private static final String MESSAGE_INPUT_MANUAL_LOTTO = "수동으로 구매할 번호를 입력해 주세요.";
    private static final String MESSAGE_INPUT_WINNING_LOTTO = "지난 주 당첨 번호를 입력해 주세요.";
    private static final String MESSAGE_INPUT_BONUS_NO = "보너스 볼을 입력해 주세요.";
    private static final Scanner scanner = new Scanner(System.in);

    public static int inputMoney() {
        System.out.println(MESSAGE_INPUT_MONEY);
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (IllegalArgumentException e) {
            System.err.println(e);
            return inputMoney();
        }
    }

    public static int inputCountOfManualLotto() {
        System.out.println(MESSAGE_INPUT_COUNT_OF_MANUAL_LOTTO);
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (IllegalArgumentException e) {
            System.err.println(e);
            return inputCountOfManualLotto();
        }
    }

    public static String inputManualLotto() {
        System.out.println(MESSAGE_INPUT_MANUAL_LOTTO);
        String input = scanner.nextLine();
        if (StringUtils.isBlank(input)) {
            return inputManualLotto();
        }
        return input;
    }

    public static String inputWinningLotto() {
        System.out.println(MESSAGE_INPUT_WINNING_LOTTO);
        String input = scanner.nextLine();
        if (StringUtils.isBlank(input)) {
            return inputWinningLotto();
        }
        return input;
    }

    public static int inputBonusNo() {
        System.out.println(MESSAGE_INPUT_BONUS_NO);
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (IllegalArgumentException e) {
            System.err.println(e);
            return inputBonusNo();
        }
    }
}
