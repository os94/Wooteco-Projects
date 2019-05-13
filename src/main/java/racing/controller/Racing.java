/*
 * @class       Racing class
 * @version     1.0.0
 * @date        19.05.10
 * @author      OHSANG SEO (tjdhtkd@gmail.com)
 * @brief       racing game main business logic
 */

package racing.controller;

import racing.model.Car;
import racing.model.Winner;
import racing.view.OutputView;

import java.util.*;

public class Racing {
    private final static String MSG_GAME_RESULT = "실행 결과";
    private final static String MSG_DUPLICATE_CAR_NAME = "중복된 차량 이름이 있습니다.";
    private final static String MSG_COUNT_MUST_POSITIVE = "게임횟수는 0보다 커야합니다.";
    private final static int RANGE_OF_RANDOM = 10;
    private final static int CONDITION_TO_POSITIVE = 1;

    private final List<Car> cars;
    private final int count;

    public Racing(String[] carNames, int count) {
        if (hasDuplicateCarName(carNames)) {
            throw new IllegalArgumentException(MSG_DUPLICATE_CAR_NAME);
        }
        if (count < CONDITION_TO_POSITIVE) {
            throw new IllegalArgumentException(MSG_COUNT_MUST_POSITIVE);
        }
        this.cars = bindCars(carNames);
        this.count = count;
    }

    public Winner run() {
        OutputView.print(MSG_GAME_RESULT);
        for (int i = 0; i < count; i++) {
            moveCars();
            OutputView.print("");
        }
        return new Winner(cars);
    }

    private boolean hasDuplicateCarName(String[] carNames) {
        return carNames.length != new HashSet<>(Arrays.asList(carNames)).size();
    }

    private List<Car> bindCars(String[] carNames) {
        List<Car> cars = new ArrayList<>();
        for (int i = 0; i < carNames.length; i++) {
            cars.add(new Car(carNames[i]));
        }
        return cars;
    }

    private void moveCars() {
        for (Car car : cars) {
            car.move(getRandomNumber());
            OutputView.print(car);
        }
    }

    private static int getRandomNumber() {
        return (int) (Math.random() * RANGE_OF_RANDOM);
    }
}
