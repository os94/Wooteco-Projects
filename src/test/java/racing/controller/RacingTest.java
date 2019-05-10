/*
 * @class       RacingTest class
 * @version     1.0.0
 * @date        19.05.10
 * @author      OHSANG SEO (tjdhtkd@gmail.com)
 * @brief       test duplicate car name, negative move number
 */

package racing.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import racing.model.Car;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class RacingTest {
    List<Car> cars;

    @BeforeEach
    void setUp() {
        cars = new ArrayList<>();
        cars.add(new Car("pobi"));
        cars.add(new Car("sean"));
    }

    @Test
    void 차량이름_중복() {
        cars.add(new Car("pobi"));

        assertThrows(IllegalArgumentException.class, () -> {
            new Racing(cars, 3);
        });
    }

    @Test
    void 이동횟수_음수() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Racing(cars, -1);
        });
    }
}
