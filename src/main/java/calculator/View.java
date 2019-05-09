package calculator;

import java.util.Scanner;

public class View {
    public static String input() {
        Scanner scanner = new Scanner(System.in);
        String value = scanner.nextLine();
        return value;
    }

    public static void output(int result) {
        System.out.println(result);
    }
}
