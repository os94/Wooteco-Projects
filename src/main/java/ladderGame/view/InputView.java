package ladderGame.view;

import ladderGame.constant.Contants;
import org.apache.commons.lang3.StringUtils;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputView {
    private static final String REGEX_MEMBER = "^([a-zA-Zㄱ-ㅎ가-힣0-9]{1,5}){1}(,[a-zA-Zㄱ-ㅎ가-힣0-9]{1,5}){1,}$";
    private static final String REGEX_HEIGHT = "([0-9]){1,}";
    private static final String NEW_LINE = "\n";
    private static final Scanner SCAN = new Scanner(System.in);

    public static String inputMembers() {
        System.out.println(Contants.INPUT_MEMBER);
        String members = SCAN.nextLine();

        if (!isValidMember(members)) {
            System.err.println(Contants.ERROR_INPUT);
            return inputMembers();
        }
        return members;
    }

    public static String inputGoals() {
        System.out.println(NEW_LINE + Contants.INPUT_GOAL);
        String goals = SCAN.nextLine();

        if (!isValidGoal(goals)) {
            return inputGoals();
        }
        return goals;
    }

    public static int inputHeight() {
        System.out.println(NEW_LINE + Contants.INPUT_HEIGHT);
        String height = SCAN.nextLine();

        if (!isValidHeight(height)) {
            System.err.println(Contants.ERROR_INPUT);
            return inputHeight();
        }
        return Integer.parseInt(height);
    }

    public static String inputTarget() {
        System.out.println(NEW_LINE + Contants.INPUT_TARGET);
        String target = SCAN.nextLine();

        if (StringUtils.isBlank(target)) {
            return inputTarget();
        }
        return target;
    }

    private static boolean isValidMember(String name) {
        Pattern pattern = Pattern.compile(REGEX_MEMBER);
        Matcher matcher = pattern.matcher(name);

        return matcher.matches();
    }

    private static boolean isValidGoal(String goals) {
        return StringUtils.isNotBlank(goals) && hasComma(goals);
    }

    private static boolean hasComma(String text) {
        if (!text.contains(",")) {
            System.err.println(Contants.ERROR_COMMA);
            return false;
        }
        return true;
    }

    private static boolean isValidHeight(String height) {
        Pattern pattern = Pattern.compile(REGEX_HEIGHT);
        Matcher matcher = pattern.matcher(height);

        return matcher.matches();
    }
}
