package racing.controller;

import racing.model.Car;
import racing.view.OutputView;

import java.util.List;

public class Racing {
    final static String MSG_GAME_RESULT = "실행 결과";

    final private List<Car> cars;
    final private int count;

    public Racing(List<Car> cars, int count) {
        this.cars = cars;
        this.count = count;
    }

    public void run() {
        OutputView.print(MSG_GAME_RESULT);

        for (int i = 0; i < count; i++) {
            moveCars();
            OutputView.print("");
        }
    }

    private void moveCars() {
        for (Car car : cars) {
            car.move();
            OutputView.print(car);
        }
    }
}
