package ladderGame.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class LadderTest {
    @Test
    void 사다리의_세로_생성() {
        Ladder ladder = new Ladder(5);
        assertThat(ladder).isEqualTo(new Ladder(5));
    }
}
