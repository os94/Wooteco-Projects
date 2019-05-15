package ladderGame.domain;

import java.util.List;
import java.util.Objects;

public class MemberGroup {
    List<Member> members;

    public MemberGroup(List<Member> members) {
        this.members = members;
    }

    public int getCountOfPerson() {
        return members.size();
    }

    public List<Member> getMembers() {
        return members;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MemberGroup that = (MemberGroup) o;
        return Objects.equals(members, that.members);
    }

    @Override
    public int hashCode() {
        return Objects.hash(members);
    }
}
