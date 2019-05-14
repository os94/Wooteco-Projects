/*
 * @class       Racing class
 * @version     1.1.0
 * @date        19.05.13
 * @author      OHSANG SEO (tjdhtkd@gmail.com)
 * @brief       racing game main business logic
 */

package racing.controller;

import racing.model.Car;
import racing.model.Winner;

import java.util.*;
import java.util.stream.Collectors;

public class Racing {
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

    public List<Car> getCars() {
        return cars;
    }

    public int getCount() {
        return count;
    }

    public void moveCars() {
        for (Car car : cars) {
            car.move(getRandomNumber());
        }
    }

    public Winner getWinner() {
        return new Winner(cars);
    }

    private boolean hasDuplicateCarName(String[] carNames) {
        return carNames.length != new HashSet<>(Arrays.asList(carNames)).size();
    }

    private List<Car> bindCars(String[] carNames) {
        return Arrays.stream(carNames).map(carName -> new Car(carName)).collect(Collectors.toList());
    }

    private static int getRandomNumber() {
        return (int) (Math.random() * RANGE_OF_RANDOM);
    }
}
