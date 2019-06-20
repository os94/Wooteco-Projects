package model;

import chess.model.Point;
import chess.model.User;
import chess.model.UserFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class UserTest {
    User white;

    @BeforeEach
    void setUp() {
        white = UserFactory.createUsers().get(true);
    }

    @Test
    void 출발지가_내것이_아닌_경우() {
        assertThrows(IllegalArgumentException.class, () -> {
            white.checkValidPoint(Point.of(8, 8), Point.of(5, 5));
        });
    }

    @Test
    void 도착지에_내것이_있는_경우() {
        assertThrows(IllegalArgumentException.class, () -> {
            white.checkValidPoint(Point.of(2, 2), Point.of(1, 1));
        });
    }


}
