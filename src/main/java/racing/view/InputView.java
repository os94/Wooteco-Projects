package racing.view;

import java.util.Scanner;

public class InputView {
    private final static String MSG_INPUT_CAR_NAME = "경주할 자동차 이름을 입력하세요(이름은 쉼표(,)를 기준으로 구분).";
    private final static String MSG_INPUT_GAME_COUNT = "시도할 회수는 몇회인가요?";

    private static Scanner scanner = new Scanner(System.in);

    public static String carName() {
        System.out.println(MSG_INPUT_CAR_NAME);
        String carNames = scanner.nextLine();

        return carNames;
    }

    public static int gameCount() {
        System.out.println(MSG_INPUT_GAME_COUNT);
        int count = scanner.nextInt();

        return count;
    }
}
