package racing.view;

import java.util.Scanner;

public class InputView {
    private static Scanner scanner = new Scanner(System.in);

    public static String carName() {
        String carNames = scanner.nextLine();

        return carNames;
    }

    public static int gameCount() {
        int count = scanner.nextInt();

        return count;
    }
}
