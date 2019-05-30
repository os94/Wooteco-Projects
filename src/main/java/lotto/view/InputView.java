package lotto.view;

import org.apache.commons.lang3.StringUtils;

import java.util.Scanner;

public class InputView {
    private static final Scanner scanner = new Scanner(System.in);

    public static int inputPositiveNumber(String message) {
        System.out.println(message);
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (IllegalArgumentException e) {
            System.err.println(e);
            return inputPositiveNumber(message);
        }
    }

    public static String inputLotto(String message) {
        System.out.println(message);
        String input = scanner.nextLine();
        if (StringUtils.isBlank(input)) {
            return inputLotto(message);
        }
        return input;
    }
}
