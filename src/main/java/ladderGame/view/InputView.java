package ladderGame.view;

import ladderGame.constant.MessageContants;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputView {
    private static final Scanner SCAN = new Scanner(System.in);
    private static final String REGEX = "^([a-zA-Zㄱ-ㅎ가-힣0-9]{1,5}){1}(,[a-zA-Zㄱ-ㅎ가-힣0-9]{1,5}){1,}$";

    public static String inputName() {
        System.out.println(MessageContants.INPUT_NAME);
        String name = SCAN.nextLine();

        if (!validName(name)) {
            System.err.println(MessageContants.ERROR_INPUT_NAME);
            return inputName();
        }

        return name;
    }

    private static boolean validName(String inputName) {
        Pattern pattern = Pattern.compile(REGEX);
        Matcher matcher = pattern.matcher(inputName);

        return matcher.matches();
    }


}
