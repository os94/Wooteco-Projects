/*
 * @class       OutputView class
 * @version     1.1.0
 * @date        19.05.13
 * @author      OHSANG SEO (tjdhtkd@gmail.com)
 * @brief       print game message
 */

package racing.view;

import racing.controller.Racing;
import racing.model.Car;
import racing.model.Winner;

public class OutputView {
    private final static String MOVEMENT = "-";
    private final static String MSG_WIN = "가 최종 우승했습니다.";

    public static void print(String message) {
        System.out.println(message);
    }

    public static void print(Racing racing) {
        for (Car car : racing.getCars()) {
            print(car);
        }
    }

    public static void print(Winner winner) {
        String winnerNames = String.join(", ", winner.getWinnerNames());
        System.out.println(winnerNames + MSG_WIN);
    }

    private static void print(Car car) {
        StringBuilder currentLocation = new StringBuilder();
        currentLocation.append(String.format("%-5s : ", car.getName()));
        for (int i = 0; i < car.getPosition(); i++) {
            currentLocation.append(MOVEMENT);
        }

        System.out.println(currentLocation.toString());
    }
}
