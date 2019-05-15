package ladderGame.domain;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class MemberGroupTest {
    @Test
    void 멤버그룹객체가_생성되는지_확인() {
        List<Member> members = new ArrayList<>();
        members.add(new Member("pobi"));
        members.add(new Member("crong"));
        members.add(new Member("sean"));
        MemberGroup memberGroup = new MemberGroup(members);

        assertThat(memberGroup).isEqualTo(new MemberGroup(members));
    }
}
