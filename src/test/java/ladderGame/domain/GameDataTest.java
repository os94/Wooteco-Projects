package ladderGame.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class GameDataTest {
    @Test
    void 참여자와_결과의_입력된_갯수가_다를_때() {
        List<Member> members = new ArrayList<>();
        members.add(new Member("pobi"));
        members.add(new Member("crong"));
        members.add(new Member("seon"));

        List<Goal> goals = new ArrayList<>();
        goals.add(new Goal("꽝"));
        goals.add(new Goal("3000"));

        assertThrows(IllegalArgumentException.class, () -> {
            new GameData(members, goals);
        });
    }

    @Test
    void 객체_생성_테스트() {
        List<Member> members = new ArrayList<>();
        members.add(new Member("pobi"));
        members.add(new Member("crong"));

        List<Goal> goals = new ArrayList<>();
        goals.add(new Goal("꽝"));
        goals.add(new Goal("3000"));

        GameData gameData = new GameData(members, goals);

        assertThat(gameData).isEqualTo(new GameData(members, goals));

    }
}
