package racing.view;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputView {
    private final static String MSG_INPUT_CAR_NAME = "경주할 자동차 이름을 입력하세요(이름은 쉼표(,)를 기준으로 구분).";
    private final static String MSG_INPUT_GAME_COUNT = "시도할 회수는 몇회인가요?";
    private final static String REGEX_CAR_NAME = "([a-zA-Z]{1,5})(,[a-zA-Z]{1,5}){0,}";

    private static Scanner scanner = new Scanner(System.in);

    public static String carName() {
        System.out.println(MSG_INPUT_CAR_NAME);
        String carNames = scanner.nextLine();
        if (isRightCarName(carNames)) {
            return carNames;
        }
        return carName();

    }

    public static int gameCount() {
        try {
            System.out.println(MSG_INPUT_GAME_COUNT);
            return Integer.parseInt(scanner.nextLine());
        } catch (Exception e) {
            return gameCount();
        }
    }

    private static boolean isRightCarName(String carNames) {
        Pattern pattern = Pattern.compile(REGEX_CAR_NAME);
        Matcher matcher = pattern.matcher(carNames);
        return matcher.matches();
    }
}
