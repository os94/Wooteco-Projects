package ladderGame.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class GameDataTest {
    @Test
    void 참여자와_결과의_입력된_갯수가_다를_때() {
        assertThrows(IllegalArgumentException.class, () -> {
            new GameData("pobi,crong,seon", "꽝,3000");
        });
    }

    @Test
    void 객체_생성_테스트() {
        GameData gameData = new GameData("pobi,crong", "꽝,30000");
        assertThat(gameData).isEqualTo(new GameData("pobi,crong", "꽝,30000"));
    }
}
