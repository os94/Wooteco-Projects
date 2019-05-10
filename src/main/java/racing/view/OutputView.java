package racing.view;

import racing.model.Car;
import racing.model.Winner;

public class OutputView {
    private final static String MSG_WIN = "가 최종 우승했습니다.";

    public static void print(String message) {
        System.out.println(message);
    }

    public static void print(Car car) {
        System.out.println(car.getCurrentLocation());
    }

    public static void print(Winner winner) {
        String winnerNames = String.join(", ", winner.getWinners());
        System.out.println(winnerNames + MSG_WIN);
    }
}
