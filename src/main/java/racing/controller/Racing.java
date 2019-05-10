package racing.controller;

import racing.model.Car;
import racing.view.OutputView;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Racing {
    private final static String MSG_GAME_RESULT = "실행 결과";
    private final static int RANDOM = 10;
    private final static int POSITIVE = 1;
    private final static String MSG_DUPLICATE_CAR_NAME = "중복된 차량 이름이 있습니다.";
    private final static String MSG_COUNT_MUST_POSITIVE = "게임횟수는 0보다 커야합니다.";

    final private List<Car> cars;
    final private int count;

    public Racing(List<Car> cars, int count) {
        if (hasDuplicateCarName(cars)) {
            throw new IllegalArgumentException(MSG_DUPLICATE_CAR_NAME);
        }
        if (count < POSITIVE) {
            throw new IllegalArgumentException(MSG_COUNT_MUST_POSITIVE);
        }
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
            car.move(getRandomNumber());
            OutputView.print(car);
        }
    }

    private static int getRandomNumber() {
        return (int) (Math.random() * RANDOM);
    }

    private boolean hasDuplicateCarName(List<Car> cars) {
        Set<String> carNames = new HashSet<>();
        for (Car car : cars) {
            carNames.add(car.getName());
        }
        return cars.size() != carNames.size();
    }
}
