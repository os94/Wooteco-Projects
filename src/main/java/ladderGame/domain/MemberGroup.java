package ladderGame.domain;

import java.util.List;

public class MemberGroup {
    List<Member> members;

    public MemberGroup(List<Member> members) {
        this.members = members;
    }

    public int getCountOfPerson() {
        return members.size();
    }
}
