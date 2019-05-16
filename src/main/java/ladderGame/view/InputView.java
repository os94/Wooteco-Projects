package ladderGame.view;

import ladderGame.constant.MessageContants;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputView {
    private static final Scanner SCAN = new Scanner(System.in);
    private static final String REGEX_NAME = "^([a-zA-Zㄱ-ㅎ가-힣0-9]{1,5}){1}(,[a-zA-Zㄱ-ㅎ가-힣0-9]{1,5}){1,}$";
    private static final String REGEX_HEIGHT = "([0-9]){1}";

    public static String inputName() {
        System.out.println(MessageContants.INPUT_NAME);
        String name = SCAN.nextLine();

        if (!validName(name)) {
            System.err.println(MessageContants.ERROR_INPUT);
            return inputName();
        }

        return name;
    }

    public static int inputHeight() {
        System.out.println(MessageContants.INPUT_HEIGHT);
        String height = SCAN.nextLine();

        if (!validHeight(height)) {
            System.err.println(MessageContants.ERROR_INPUT);
            return inputHeight();
        }

        return Integer.parseInt(height);
    }

    public static String inputResult() {
        System.out.println(MessageContants.INPUT_RESULT);
        String result = SCAN.nextLine();

        if (validGameResult(result)) {
            return inputResult();
        }

        return result;
    }

    private static boolean validHeight(String height) {
        Pattern pattern = Pattern.compile(REGEX_HEIGHT);
        Matcher matcher = pattern.matcher(height);

        return matcher.matches();
    }

    private static boolean validName(String name) {
        Pattern pattern = Pattern.compile(REGEX_NAME);
        Matcher matcher = pattern.matcher(name);

        return matcher.matches();
    }

    private static boolean validGameResult(String result) {
        return isBlank(result) || hasNotComma(result);
    }

    private static boolean isBlank(String text) {
        if (text.isEmpty()) {
            System.err.println(MessageContants.ERROR_EMPTY);
            return true;
        }

        return false;
    }

    private static boolean hasNotComma(String text) {
        if (!text.contains(",")) {
            System.err.println(MessageContants.ERROR_COMMA);
            return true;
        }

        return false;
    }


}
