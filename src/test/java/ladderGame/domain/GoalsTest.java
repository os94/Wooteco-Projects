package ladderGame.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class GoalsTest {
    @Test
    void create_blank() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Goals(null, 3);
            new Goals("", 3);
            new Goals("win,,fail", 3);
        });
    }

    @Test
    void mismatch_with_members() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Goals("pobi,crong", 3);
        });
    }
}
