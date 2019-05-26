package ladderGame.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MembersTest {
    @Test
    void create_blank() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Members(null);
            new Members("");
        });
    }

    @Test
    void has_duplicate_name() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Members("pobi,crong,pobi");
        });
    }

    @Test
    void has_member() {
        Members members = new Members("pobi,crong");
        assertThat(members.has("crong")).isTrue();
        assertThat(members.has("sean")).isFalse();
    }
}
