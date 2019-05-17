package ladderGame.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class LadderTest {
    @Test
    void 객체_생성() {
        Ladder ladder = new Ladder(4, 5);
        assertThat(ladder).isEqualTo(new Ladder(4, 5));
    }
}
