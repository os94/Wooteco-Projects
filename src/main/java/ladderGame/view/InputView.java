package ladderGame.view;

import ladderGame.constant.Contants;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputView {
    private static final String REGEX_NAME = "^([a-zA-Zㄱ-ㅎ가-힣0-9]{1,5}){1}(,[a-zA-Zㄱ-ㅎ가-힣0-9]{1,5}){1,}$";
    private static final String REGEX_HEIGHT = "([0-9]){1}";
    private static final String NEW_LINE = "\n";
    private static final Scanner SCAN = new Scanner(System.in);

    public static String inputNames() {
        System.out.println(Contants.INPUT_NAME);
        String names = SCAN.nextLine();

        if (!validName(names)) {
            System.err.println(Contants.ERROR_INPUT);
            return inputNames();
        }
        return names;
    }

    public static String inputGoals() {
        System.out.println(NEW_LINE + Contants.INPUT_GOAL);
        String goals = SCAN.nextLine();

        if (!validGoal(goals)) {
            return inputGoals();
        }
        return goals;
    }

    public static int inputHeight() {
        System.out.println(NEW_LINE + Contants.INPUT_HEIGHT);
        String height = SCAN.nextLine();

        if (!validHeight(height)) {
            System.err.println(Contants.ERROR_INPUT);
            return inputHeight();
        }
        return Integer.parseInt(height);
    }

    public static String inputTargetName() {
        System.out.println(NEW_LINE + Contants.INPUT_TARGET);
        String target = SCAN.nextLine();

        if (isBlank(target)) {
            return inputTargetName();
        }
        return target;
    }

    private static boolean validName(String name) {
        Pattern pattern = Pattern.compile(REGEX_NAME);
        Matcher matcher = pattern.matcher(name);

        return matcher.matches();
    }

    private static boolean validHeight(String height) {
        Pattern pattern = Pattern.compile(REGEX_HEIGHT);
        Matcher matcher = pattern.matcher(height);

        return matcher.matches();
    }

    private static boolean validGoal(String goals) {
        return !(isBlank(goals) || hasNotComma(goals));
    }

    private static boolean isBlank(String text) {
        if (text.isEmpty()) {
            System.err.println(Contants.ERROR_EMPTY);
            return true;
        }
        return false;
    }

    private static boolean hasNotComma(String text) {
        if (!text.contains(",")) {
            System.err.println(Contants.ERROR_COMMA);
            return true;
        }
        return false;
    }
}
