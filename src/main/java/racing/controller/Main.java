package racing.controller;

import racing.model.Car;
import racing.model.Winner;
import racing.view.InputView;
import racing.view.OutputView;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Car> cars = InputView.carName();
        int count = InputView.gameCount();

        Racing racing = new Racing(cars, count);
        racing.run();

        Winner winner = new Winner(cars);
        OutputView.print(winner);
    }
}
