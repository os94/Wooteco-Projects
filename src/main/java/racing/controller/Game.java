package racing.controller;

import racing.model.Car;
import racing.model.Winner;
import racing.view.InputView;
import racing.view.OutputView;

import java.util.ArrayList;
import java.util.List;

public class Game {
    public static void main(String[] args) {
        List<Car> cars = makeCarList(InputView.carName());
        int count = InputView.gameCount();

        try {
            Racing racing = new Racing(cars, count);
            racing.run();
        } catch (IllegalArgumentException e) {
            System.exit(-1);
        }

        Winner winner = new Winner(cars);
        OutputView.print(winner);
    }

    private static List<Car> makeCarList(String carNameInputs) {
        String[] carNames = carNameInputs.split(",");
        List<Car> cars = new ArrayList<>();
        for (int i = 0; i < carNames.length; i++) {
            cars.add(new Car(carNames[i]));
        }
        return cars;
    }
}
