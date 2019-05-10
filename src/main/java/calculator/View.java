/*
 * @class       View class
 * @version     1.0.0
 * @date        19.05.10
 * @author      OHSANG SEO (tjdhtkd@gmail.com)
 * @brief       input from User, print to User
 */

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
