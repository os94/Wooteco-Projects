package racing.view;

import racing.model.Car;
import racing.model.Winner;

public class OutputView {
    public static void print(Winner winner) {
    }

    public static void print(String message) {
        System.out.println(message);
    }

    public static void print(Car car) {
        System.out.printf("%-5s : ", car.getName());
        for (int i = 0; i < car.getPosition(); i++) {
            System.out.print("-");
        }
        System.out.println();
    }
}
