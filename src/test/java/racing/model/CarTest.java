package racing.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CarTest {
    @Test
    void create_name() {
        assertThat(new Car("pobi").equals(new Car("pobi"))).isEqualTo(true);
    }

    @Test
    void create_name_blank() {
        assertThrows(IllegalArgumentException.class, () -> {
           new Car("");
        });
    }

    @Test
    void create_name_5자_초과() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Car("pobing");
        });
    }

    @Test
    void move() {
        Car car = new Car("pobi");
        car.move(4);
        assertThat(car).isEqualTo(new Car("pobi", 1));
    }

    @Test
    void stop() {
        Car car = new Car("pobi");
        car.move(3);
        assertThat(car.matchPosition(0)).isEqualTo(true);
    }

    @Test
    void current_position() {
        assertThat(new Car("pobi", 3).getCurrentLocation()).isEqualTo("pobi  : ---");
    }
}
