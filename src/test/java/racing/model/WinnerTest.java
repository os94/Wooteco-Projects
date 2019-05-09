package racing.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class WinnerTest {
    @Test
    void 우승자_선정() {
        List<Car> cars = new ArrayList<>();
        cars.add(new Car("pobi", 3));
        cars.add(new Car("sean", 2));
        cars.add(new Car("denis", 3));
        cars.add(new Car("ms", 1));
        Winner winner = new Winner(cars);

        assertThat(winner.getWinners()).hasSize(2);
    }
}
